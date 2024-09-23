package com.sts15.fargos.events;

//Saereth asked to not give book on world creation, but rather toggle in config or quest book

//import com.sts15.fargos.Fargos;
//import com.sts15.fargos.items.ItemInit;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.entity.player.PlayerEvent;
//
//@EventBusSubscriber(modid = Fargos.MODID)
//public class GuideBookHandler {
//
//    private static final String FARGOS_PLAYER_GOT_GUIDE_KEY = "fargosPlayerGotGuide";
//
//    @SubscribeEvent
//    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
//        Player player = event.getEntity();
//        CompoundTag playerData = player.getPersistentData();
//        if (!playerData.getBoolean(FARGOS_PLAYER_GOT_GUIDE_KEY)) {
//            ItemStack guideBook = new ItemStack(ItemInit.GUIDE_BOOK);
//            if (player.addItem(guideBook)) {
//                playerData.putBoolean(FARGOS_PLAYER_GOT_GUIDE_KEY, true);
//            }
//        }
//    }
//}