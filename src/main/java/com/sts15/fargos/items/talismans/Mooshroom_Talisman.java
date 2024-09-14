package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Mooshroom_Talisman extends TalismanItem {

    private static final String talismanName = "mooshroom_talisman";

    public Mooshroom_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;

        @SuppressWarnings({"removal", "deprecation"})
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (player.hasEffect(EffectsInit.MOOSHROOM_TALISMAN_EFFECT) ||
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Mooshroom_Talisman, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;

                BlockPos belowPlayer = player.blockPosition().below();
                BlockState blockBelow = player.level().getBlockState(belowPlayer);

                if (blockBelow.getBlock() instanceof MyceliumBlock) {
                    player.heal(0.02F);

                    // Increment tick counter and trigger every 8 ticks
                    if (tickCounter % 10 == 0) {
                        playHealingSound(player.level(), player);
                    }

                    tickCounter++;
                }
            }
        }

        private static void playHealingSound(Level level, Player player) {
            if (!level.isClientSide()) {
                // Play a quiet healing sound
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FISHING_BOBBER_RETRIEVE,
                        SoundSource.PLAYERS, 0.1F, 0.7F);
            }
        }
    }
}
