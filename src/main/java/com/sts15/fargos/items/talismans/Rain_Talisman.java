package com.sts15.fargos.items.talismans;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Rain_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import java.util.List;

public class Rain_Talisman extends TalismanItem implements Rain_Talisman_Provider {

    private static final String talismanName = "rain_talisman";
    private static final ResourceLocation RAIN_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "rain_talisman_speed_modifier");

    private static final double SPEED_BONUS = 0.50;

    public Rain_Talisman() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            Level level = player.level();
            AttributeInstance movementAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementAttribute == null) return;

            boolean isRaining = level.isRaining();
            boolean isInWater = player.isInWater();
            boolean hasTalismanEquipped = player.hasEffect(EffectsInit.RAIN_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Rain_Talisman_Provider, player).isPresent();

            if (isRaining && isInWater && hasTalismanEquipped) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;

                if (!movementAttribute.hasModifier(RAIN_SPEED_MODIFIER_ID)) {
                    //System.out.println("Applying Rain Speed Bonus to player " + player.getName().getString());
                    AttributeModifier speedModifier = new AttributeModifier(RAIN_SPEED_MODIFIER_ID, SPEED_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                    movementAttribute.addTransientModifier(speedModifier);
                }
            } else {
                if (movementAttribute.hasModifier(RAIN_SPEED_MODIFIER_ID)) {
                    //System.out.println("Removing Rain Speed Bonus from player " + player.getName().getString());
                    movementAttribute.removeModifier(RAIN_SPEED_MODIFIER_ID);
                }
            }
        }
    }
}
