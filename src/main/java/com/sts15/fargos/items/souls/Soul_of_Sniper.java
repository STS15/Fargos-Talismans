package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Soul_of_Sniper_Provider;
import com.sts15.fargos.mixins.AbstractArrowAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.List;

@EventBusSubscriber(modid = Fargos.MODID)
public class Soul_of_Sniper extends TalismanItem implements ICurioItem, Soul_of_Sniper_Provider {

    public static final String talismanName = "soul_of_sniper";

    public Soul_of_Sniper() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("When worn as talisman:").withStyle(ChatFormatting.GOLD));
        double damageMult = Config.SOUL_OF_SNIPER_ARROW_DAMAGE_MULTIPLIER.get();
        int damagePercent = (int) ((damageMult - 1.0) * 100);
        tooltipComponents.add(Component.literal("+" + damagePercent + "% Arrow Damage").withStyle(ChatFormatting.BLUE).append(Component.literal(String.format(" [%.2fx]", damageMult)).withStyle(ChatFormatting.GRAY)));
        double speedMult = Config.SOUL_OF_SNIPER_PROJECTILE_SPEED_MULTIPLIER.get();
        int speedPercent = (int) ((speedMult - 1.0) * 100);
        tooltipComponents.add(Component.literal("+" + speedPercent + "% Projectile Speed").withStyle(ChatFormatting.BLUE).append(Component.literal(String.format(" [+%d%%]", speedPercent)).withStyle(ChatFormatting.GRAY)));
        int pierce = Config.SOUL_OF_SNIPER_PIERCE_LEVEL.get();
        tooltipComponents.add(Component.literal("+" + pierce + " Arrow Pierce").withStyle(ChatFormatting.BLUE).append(Component.literal(" [" + pierce + "]").withStyle(ChatFormatting.GRAY)));
        boolean noGravity = Config.SOUL_OF_SNIPER_NO_GRAVITY.get();
        if (noGravity) {
            tooltipComponents.add(Component.literal("+1 Arrows Ignore Gravity").withStyle(ChatFormatting.BLUE).append(Component.literal(" [1]").withStyle(ChatFormatting.GRAY)));
        }
        int slowAmp = Config.SOUL_OF_SNIPER_SLOWNESS_LEVEL.get();
        tooltipComponents.add(Component.literal("Applies Slowness on Hit").withStyle(ChatFormatting.BLUE).append(Component.literal(" [Slowness " + (slowAmp + 1) + "]").withStyle(ChatFormatting.GRAY)));
        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        boolean isEnabled = true;
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
        return isEnabled;
    }

    private static boolean hasSniperSoul(ServerPlayer player) {
        return CuriosApi.getCuriosHelper()
                .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Sniper_Provider, player)
                .isPresent();
    }

    @SubscribeEvent
    public static void onArrowSpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.getOwner() instanceof ServerPlayer player)) return;
        if (!checkConfigEnabledStatus() || !hasSniperSoul(player)) return;
        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(Config.SOUL_OF_SNIPER_PROJECTILE_SPEED_MULTIPLIER.get()));
        ((AbstractArrowAccessor) arrow).callSetPierceLevel(Config.SOUL_OF_SNIPER_PIERCE_LEVEL.get().byteValue());
        if (Config.SOUL_OF_SNIPER_NO_GRAVITY.get()) { arrow.setNoGravity(true); }

    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(arrow.getOwner() instanceof ServerPlayer player)) return;
        if (!checkConfigEnabledStatus() || !hasSniperSoul(player)) return;
        if (event.getRayTraceResult() instanceof EntityHitResult hit && hit.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    Config.SOUL_OF_SNIPER_SLOWNESS_DURATION.get(),
                    Config.SOUL_OF_SNIPER_SLOWNESS_LEVEL.get()
            ));
        }
    }

    @SubscribeEvent
    public static void onArrowDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.getOwner() instanceof ServerPlayer player)) return;
        if (!checkConfigEnabledStatus() || !hasSniperSoul(player)) return;
        event.setNewDamage((float) (event.getOriginalDamage() * Config.SOUL_OF_SNIPER_ARROW_DAMAGE_MULTIPLIER.get()));
    }

}
