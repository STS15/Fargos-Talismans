package com.sts15.fargos.items.scavenged;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Brain_Scrambler_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.lang.reflect.Field;
import java.util.List;

public class BrainScramblerItem extends TalismanItem implements ICurioItem, Brain_Scrambler_Provider {

    private static final Logger LOGGER = LogManager.getLogger(Fargos.MODID);
    public static final String charmName = "brain_scrambler";

    public BrainScramblerItem() {
        super(new Properties().rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + charmName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        try {
            String fieldName = charmName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            return ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (Exception e) {
            LOGGER.error("Error checking config status for " + charmName, e);
            return true;
        }
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if (!player.isSprinting()) return;

            boolean equipped = CuriosApi.getCuriosHelper().findEquippedCurio(
                    stack -> stack.getItem() instanceof Brain_Scrambler_Provider, player).isPresent();

            boolean enabled = TalismanUtil.isTalismanEnabled(player, charmName);
            if (!equipped || !enabled) return;

            CompoundTag data = player.getPersistentData();
            int cooldown = data.getInt("BrainScramblerCooldown");
            if (cooldown > 0) {
                data.putInt("BrainScramblerCooldown", cooldown - 1);
                return;
            }

            AABB box = player.getBoundingBox().inflate(6.0);
            List<Mob> mobs = player.level().getEntitiesOfClass(Mob.class, box,
                    mob -> mob.getTarget() == player);

            for (Mob mob : mobs) {
                mob.setTarget(null);
                mob.getNavigation().stop();
                mob.hurtMarked = true;
            }

            data.putInt("BrainScramblerCooldown", 60);
        }
    }
}
