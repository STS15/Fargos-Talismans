package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.List;

public class Soul_of_Eternity extends TalismanItem implements ICurioItem, Soul_of_Eternity_Provider,
            Earth_Talisman_Provider, Water_Talisman_Provider, Fire_Talisman_Provider, Air_Talisman_Provider, // Force of Nature
            Emerald_Talisman_Provider, Amethyst_Talisman_Provider, Gold_Talisman_Provider, Diamond_Talisman_Provider, Lapis_Talisman_Provider, Redstone_Talisman_Provider, // Force of Overworld
            Dragon_Talisman_Provider, Wither_Talisman_Provider, Zombie_Talisman_Provider, Skeleton_Talisman_Provider, Blaze_Talisman_Provider, Ghast_Talisman_Provider, Creeper_Talisman_Provider, Vindicator_Talisman_Provider, // Force of Rejectors
            Vampiric_Talisman_Provider, Enchanting_Talisman_Provider, Librarian_Talisman_Provider, Witch_Talisman_Provider, Shulker_Talisman_Provider, Undying_Talisman_Provider, // Force of Mystic
            Battle_Talisman_Provider, Iron_Golem_Talisman_Provider, Thorny_Talisman_Provider, Cactus_Talisman_Provider, Void_Talisman_Provider, // Force of Warrior
            Glowstone_Talisman_Provider, Spectral_Talisman_Provider, Arctic_Talisman_Provider, Enderman_Talisman_Provider, Architect_Talisman_Provider, // Force of Explorer
            Day_Talisman_Provider, Snowy_Talisman_Provider, True_Sun_Talisman_Provider, Storm_Talisman_Provider, Rain_Talisman_Provider, Night_Talisman_Provider, Full_Moon_Talisman_Provider, Sun_Talisman_Provider, // Force of Environment
            Fired_Talisman_Provider, Poisoned_Talisman_Provider, Withered_Talisman_Provider, Blinded_Talisman_Provider, Fatigued_Talisman_Provider, Slowed_Talisman_Provider, Nauseated_Talisman_Provider, Weakened_Talisman_Provider,	// Force of Negative
            Soul_of_Colossus_Provider, Soul_of_Flight_Mastery_Provider, Soul_of_Supersonic_Provider, // Soul of Dimensions
            Soul_of_Arch_Wizard_Provider, Soul_of_Berserker_Provider, Soul_of_Conjurist_Provider, Soul_of_Sniper_Provider, Soul_of_Trawler_Provider, Soul_of_World_Shaper_Provider // Soul of the Universe
{

    public static final String talismanName = "soul_of_eternity";

    public Soul_of_Eternity() {
        super(new Properties().rarity(Rarity.EPIC));
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

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

        }

    }
}
