package local.shieldtest;

import com.mojang.authlib.GameProfile;
import com.sts15.fargos.items.scavenged.ShieldOfCthulhuItem;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;
import java.util.UUID;

@Mod("shieldregression")
public class ShieldRegression {
    public ShieldRegression() { NeoForge.EVENT_BUS.addListener(this::register); }
    private void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("shieldregression").requires(s -> s.hasPermission(4))
            .executes(context -> {
                String result;
                try { result = run(context.getSource().getLevel()); }
                catch (StackOverflowError e) { result = "REPRODUCED: guardian/shield StackOverflowError"; }
                catch (Throwable e) { e.printStackTrace(); result = "FAILED: " + e; }
                final String message = result;
                System.out.println("SHIELD_REGRESSION " + message);
                context.getSource().sendSuccess(() -> Component.literal(message), false);
                return message.startsWith("PASS") ? 1 : 0;
            }));
    }
    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    private static String run(ServerLevel level) throws ReflectiveOperationException {
        FakePlayer player = new FakePlayer(level,
            new GameProfile(UUID.fromString("77b2ec9d-3a25-47c9-bd01-37f64769c100"), "ShieldRegression")) {
                @Override public boolean isInvulnerableTo(DamageSource source) { return false; }
            };
        // FakePlayer is normally invulnerable, and newly created players also have
        // spawn protection. Disable both so real guardian thorns reaches the event bus.
        var spawnProtection = net.minecraft.server.level.ServerPlayer.class.getDeclaredField("spawnInvulnerableTime");
        spawnProtection.setAccessible(true);
        spawnProtection.setInt(player, 0);
        player.setPos(0, 200, 0);
        player.setHealth(player.getMaxHealth());
        player.setSprinting(true);
        var inventory = CuriosApi.getCuriosInventory(player).orElseThrow();
        inventory.reset();
        var slot = inventory.getCurios().entrySet().stream()
            .filter(e -> e.getValue().getStacks().getSlots() > 0).findFirst().orElseThrow();
        var shield = BuiltInRegistries.ITEM.get(ResourceLocation.parse("fargostalismans:shield_of_cthulhu"));
        inventory.setEquippedCurio(slot.getKey(), 0, new ItemStack(shield));
        require(inventory.isEquipped(shield), "shield was not equipped");
        require(TalismanUtil.isTalismanEnabled(player, "shield_of_cthulhu"), "shield disabled");
        class CountingGuardian extends Guardian {
            int calls;
            CountingGuardian() { super(EntityType.GUARDIAN, level); setPos(2, 200, 0); }
            @Override public boolean hurt(DamageSource source, float amount) {
                calls++;
                return super.hurt(source, amount);
            }
        }
        try {
            CountingGuardian guardian = new CountingGuardian();
            float hp = guardian.getHealth();
            ShieldOfCthulhuItem.Events.onPlayerHit(new LivingIncomingDamageEvent(
                player, new DamageContainer(player.damageSources().mobAttack(guardian), 1.0F)));
            require(guardian.calls == 1, "guardian damage recursively called " + guardian.calls + " times");
            require(guardian.getHealth() < hp, "shield did not damage guardian");
            require(player.getHealth() < player.getMaxHealth(), "guardian thorns did not actually execute");
            require(guardian.getDeltaMovement().lengthSqr() > 0, "shield knockback missing");
            // A separate later hit must still work, proving the guard is not left set.
            player.invulnerableTime = 0;
            player.setHealth(player.getMaxHealth());
            player.setSprinting(true);
            CountingGuardian second = new CountingGuardian();
            ShieldOfCthulhuItem.Events.onPlayerHit(new LivingIncomingDamageEvent(
                player, new DamageContainer(player.damageSources().mobAttack(second), 1.0F)));
            require(second.calls == 1 && second.getHealth() < second.getMaxHealth(), "subsequent hit broken");

            // An exception from a damage callback must also clear the guard.
            Guardian throwing = new Guardian(EntityType.GUARDIAN, level) {
                @Override public boolean hurt(DamageSource source, float amount) {
                    throw new IllegalStateException("intentional regression-test callback failure");
                }
            };
            throwing.setPos(2, 200, 0);
            boolean threw = false;
            try {
                ShieldOfCthulhuItem.Events.onPlayerHit(new LivingIncomingDamageEvent(
                    player, new DamageContainer(player.damageSources().mobAttack(throwing), 1.0F)));
            } catch (IllegalStateException expected) { threw = true; }
            require(threw, "exception scenario did not run");
            Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
            zombie.setPos(2, 200, 0);
            float zombieHp = zombie.getHealth();
            ShieldOfCthulhuItem.Events.onPlayerHit(new LivingIncomingDamageEvent(
                player, new DamageContainer(player.damageSources().mobAttack(zombie), 1.0F)));
            require(zombie.getHealth() < zombieHp, "guard not reset after exception");

            player.setSprinting(false);
            Zombie inactive = new Zombie(EntityType.ZOMBIE, level);
            inactive.setPos(2, 200, 0);
            ShieldOfCthulhuItem.Events.onPlayerHit(new LivingIncomingDamageEvent(
                player, new DamageContainer(player.damageSources().mobAttack(inactive), 1.0F)));
            require(inactive.getHealth() == inactive.getMaxHealth(), "non-sprinting behavior changed");
            return "PASS: guardian retaliation bounded; damage/knockback preserved; subsequent hits and exception cleanup work; non-sprinting unchanged";
        } finally {
            inventory.setEquippedCurio(slot.getKey(), 0, ItemStack.EMPTY);
            player.setSprinting(false);
            player.setHealth(player.getMaxHealth());
        }
    }
}