package com.sts15.fargos.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

public class FireUIRenderer {

    public static final ResourceLocation FIRE_SPRITE = ResourceLocation.fromNamespaceAndPath("fargostalismans", "textures/gui/hud/fire.png");
    public static final ResourceLocation FIRE_EMPTY_SPRITE = ResourceLocation.fromNamespaceAndPath("fargostalismans", "textures/gui/hud/fire_extinguish.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Post event) {

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || !shouldShowFireUI(player)) return;

        // === Get the GUI positioning context ===
        GuiGraphics gui = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int xStart = screenWidth / 2 + 91;

        // === Fire shield logic ===
        int fireTime = getFireShieldTime(player);
        if (fireTime <= 0) return; // skip if not active (remove for testing)

        int maxFireTime = 200;
        int fullIcons = Mth.ceil((double)(fireTime - 2) * 10.0 / maxFireTime);
        int partialIcons = Mth.ceil((double)fireTime * 10.0 / maxFireTime) - fullIcons;
        int totalIcons = fullIcons + partialIcons;

        // === Match air bubble Y position and stacking behavior ===
        int y = screenHeight - 49; // air bar Y offset (same as vanilla)
        int iconSize = 9;

        RenderSystem.enableBlend();

        for (int i = 0; i < totalIcons; i++) {
            int x = xStart - i * 8 - 9;
            RenderSystem.setShaderTexture(0, i < fullIcons ? FIRE_SPRITE : FIRE_EMPTY_SPRITE);
            gui.blit(
                    i < fullIcons ? FIRE_SPRITE : FIRE_EMPTY_SPRITE, // texture
                    x, y,      // screen position
                    0, 0,      // UV coordinates in the texture (top-left)
                    iconSize, iconSize,  // how much to draw
                    iconSize, iconSize   // full texture size (9x9)
            );
        }

        RenderSystem.disableBlend();
    }

    private static int getFireShieldTime(Player player) {
        return player.getPersistentData().getInt("FireShieldDuration");
    }

    private static boolean shouldShowFireUI(Player player) {
        return getFireShieldTime(player) > 0;
    }
}
