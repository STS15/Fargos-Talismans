package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Blaze_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Blaze_Talisman extends TalismanItem implements Blaze_Talisman_Provider {

    private static final String talismanName = "blaze_talisman";

    public Blaze_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
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
        } catch (NoSuchFieldException | IllegalAccessException e) {}
        return isEnabled;
    }
    
    private static boolean canSee(Player player, Entity target) {
        Vec3 playerEye = player.getEyePosition(1.0F);
        Vec3 targetPos = target.position();
        BlockHitResult result = player.level().clip(new ClipContext(playerEye, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        
        return result.getType() == HitResult.Type.MISS;
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
    	
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (player.hasEffect(EffectsInit.BLAZE_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Blaze_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, "blaze_talisman"))
                        return;

                    int radius = Config.BLAZE_TALISMAN_BURN_RANGE.getAsInt();
                    AABB area = player.getBoundingBox().inflate(radius);
                    List<Monster> mobs = player.level().getEntitiesOfClass(Monster.class, area);
                    for (Monster mob : mobs) {
                        if (canSee(player, mob)) {
                            mob.igniteForSeconds(Config.BLAZE_TALISMAN_BURN_TIME.getAsInt());
                        }
                    }
                }
            }
        }


    }
}
