package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Emerald_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class Emerald_Talisman extends TalismanItem implements Emerald_Talisman_Provider {

    private static final String talismanName = "emerald_talisman";
	
	private static final long ACTIVE_ABILITY_COOLDOWN = 15000; // 15 seconds
    private long lastActiveAbilityTime = 0;

    public Emerald_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Level level = player.level();
        long currentTime = System.currentTimeMillis();

        if (!level.isClientSide) {
            if (currentTime - lastActiveAbilityTime >= ACTIVE_ABILITY_COOLDOWN) {
                lastActiveAbilityTime = currentTime;
                applyBonemealEffect(player, level);
            }
        }
    }
    
    private void applyBonemealEffect(Player player, Level level) {
        RandomSource randomSource = level.random;

        BlockPos.betweenClosedStream(player.blockPosition().offset(-2, -1, -2), player.blockPosition().offset(2, 1, 2))
                .forEach(pos -> {
                    BlockState blockState = level.getBlockState(pos);
                    if (isBonemealableCrop(blockState)) {
                        BonemealableBlock bonemealable = (BonemealableBlock) blockState.getBlock();
                        if (bonemealable.isValidBonemealTarget(level, pos, blockState)) {
                            if (level instanceof ServerLevel) {
                                if (bonemealable.isBonemealSuccess(level, randomSource, pos, blockState)) {
                                    bonemealable.performBonemeal((ServerLevel) level, randomSource, pos, blockState);
                                }
                            }
                        }
                    }
                });
    }
    
    private boolean isBonemealableCrop(BlockState blockState) {
        Block block = blockState.getBlock();
        return block instanceof BonemealableBlock && block != Blocks.GRASS_BLOCK && block != Blocks.TALL_GRASS && block != Blocks.SHORT_GRASS;
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            Entity source = event.getSource().getDirectEntity();

            if (source instanceof ServerPlayer player) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Emerald_Talisman_Provider, player).isPresent()) {
                    if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                        return;

                    if (!(event.getSource().getEntity() instanceof Player))
                        return;

                    if (event.getEntity() instanceof AbstractIllager) {
                        float extraDamage = 2.0F;
                        float baseDamage = event.getAmount();
                        float totalDamage = baseDamage + extraDamage;
                        event.setAmount(totalDamage);
                    }
                }
            }
        }

    }

}
