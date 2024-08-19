package com.sts15.fargos.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sts15.fargos.network.NetworkHandler;
import com.sts15.fargos.network.TalismanType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TalismanToggleScreen extends Screen {

    public static final int WINDOW_WIDTH = 252;
    public static final int WINDOW_HEIGHT = 200;
    private static final int NODE_HEIGHT = 20;
    private static final int SCROLL_BAR_WIDTH = 10;

    private int leftPos, topPos;
    private int scrollOffset = 0;
    private boolean isScrolling = false;

    private List<TalismanNode> nodes;
    private int totalNodeHeight;

    private final Map<Integer, Boolean> talismanStates = new HashMap<>();

    private final Component headerText = Component.translatable("screen.fargostalismans.header").withStyle(ChatFormatting.BOLD);

    public TalismanToggleScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - WINDOW_WIDTH) / 2;
        this.topPos = (this.height - WINDOW_HEIGHT) / 2;
        nodes = setupTalismanNodes();
        totalNodeHeight = nodes.size() * NODE_HEIGHT;
    }

    public void setTalismanStates(Map<Integer, Boolean> states) {
        //System.out.println("Setting talisman states from packet: " + states);
        talismanStates.clear();
        talismanStates.putAll(states);
        updateTalismanStates();
    }

    private List<TalismanNode> setupTalismanNodes() {
        List<TalismanNode> talismanNodes = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : talismanStates.entrySet()) {
            TalismanType talismanType = TalismanType.byIndex(entry.getKey());
            String talismanName = talismanType.name().toLowerCase();
            boolean isEnabled = entry.getValue();
            talismanNodes.add(new TalismanNode(talismanName, isEnabled));
        }
        return talismanNodes;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        renderCustomBackground(guiGraphics);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        drawHeader(guiGraphics);

        drawNodes(guiGraphics, mouseX, mouseY);
        drawScrollBar(guiGraphics);
    }

    private void renderCustomBackground(GuiGraphics guiGraphics) {
        int startColor = FastColor.ARGB32.color(200, 34, 45, 50); // Subtle modern background
        int endColor = FastColor.ARGB32.color(200, 24, 35, 40);

        guiGraphics.fillGradient(leftPos, topPos, leftPos + WINDOW_WIDTH, topPos + WINDOW_HEIGHT, startColor, endColor);

        // Add subtle transparency and depth to the background
        int overlayColor = FastColor.ARGB32.color(120, 20, 20, 20);
        guiGraphics.fill(leftPos + 10, topPos + 10, leftPos + WINDOW_WIDTH - 10, topPos + WINDOW_HEIGHT - 10, overlayColor);

        // Modern border with rounded corners and slight glow
        int borderColor = FastColor.ARGB32.color(255, 80, 150, 200);
        guiGraphics.renderOutline(leftPos, topPos, WINDOW_WIDTH, WINDOW_HEIGHT, borderColor);
    }

    private void drawNode(GuiGraphics guiGraphics, TalismanNode node, int x, int y, int mouseX, int mouseY) {
        int startColor = FastColor.ARGB32.color(220, 60, 60, 80); // Softer gradient
        int endColor = FastColor.ARGB32.color(220, 40, 40, 60);
        int borderColor = FastColor.ARGB32.color(255, 140, 140, 180); // Lighter border
        int hoverColor = FastColor.ARGB32.color(255, 190, 190, 230);  // Subtle hover effect

        guiGraphics.fillGradient(x, y, x + 200, y + NODE_HEIGHT - 2, startColor, endColor);

        int currentBorderColor = isHoveringNode(x, y, mouseX, mouseY) ? hoverColor : borderColor;
        guiGraphics.renderOutline(x, y, 200, NODE_HEIGHT - 2, currentBorderColor);

        Component nameComponent = Component.translatable("item.fargostalismans." + node.talismanName())
                .withStyle(ChatFormatting.UNDERLINE); // Use underline for emphasis

        guiGraphics.drawString(Minecraft.getInstance().font, nameComponent, x + 25, y + 5, FastColor.ARGB32.color(255, 240, 240, 255), true);

        drawCheckbox(guiGraphics, x + 170, y + 5, node.enabled());
        drawIcon(guiGraphics, x + 5, (y + (NODE_HEIGHT - 12) / 2) - 1, node);

        if (isHoveringNode(x, y, mouseX, mouseY)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("item.fargostalismans.tooltip." + node.talismanName()), mouseX, mouseY);
        }
    }

    private void drawCheckbox(GuiGraphics guiGraphics, int x, int y, boolean checked) {
        int outlineColor = FastColor.ARGB32.color(255, 220, 220, 255);
        int checkmarkColor = FastColor.ARGB32.color(255, 50, 220, 80);

        guiGraphics.fill(x, y, x + 10, y + 10, outlineColor);

        if (checked) {
            // Apply a smooth fade-in animation for the checkmark
            guiGraphics.fill(x + 2, y + 2, x + 8, y + 8, checkmarkColor);
        }
    }

    private void drawHeader(GuiGraphics guiGraphics) {
        // Draw the header text centered at the top of the screen
        int headerX = leftPos + (WINDOW_WIDTH / 2) - (Minecraft.getInstance().font.width(headerText) / 2);
        int headerY = topPos + 5;
        guiGraphics.drawString(Minecraft.getInstance().font, headerText, headerX, headerY, FastColor.ARGB32.color(255, 255, 255, 255), true);
    }

    private void drawNodes(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int yStart = topPos + 20 - scrollOffset;

        for (int i = 0; i < nodes.size(); i++) {
            TalismanNode node = nodes.get(i);
            int yPos = yStart + i * NODE_HEIGHT;
            if (yPos >= topPos + 20 && yPos <= topPos + WINDOW_HEIGHT - NODE_HEIGHT) {
                drawNode(guiGraphics, node, leftPos + 20, yPos, mouseX, mouseY);
            }
        }
    }

    private void drawIcon(GuiGraphics guiGraphics, int x, int y, TalismanNode node) {
        ResourceLocation iconLocation = ResourceLocation.fromNamespaceAndPath("fargostalismans", "textures/item/" + node.talismanName() + ".png");
        guiGraphics.blit(iconLocation, x, y, 0, 0, 12, 12, 12, 12);
    }

    public void updateTalismanStates() {
        //System.out.println("Updating Talisman states on the screen");
        nodes = setupTalismanNodes();

        if (nodes.isEmpty()) {
            //System.out.println("No talisman nodes to display.");
        }
        totalNodeHeight = nodes.size() * NODE_HEIGHT;
        this.init();
    }

    private boolean isHoveringNode(int x, int y, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + 200 && mouseY < y + NODE_HEIGHT;
    }

    private void drawScrollBar(GuiGraphics guiGraphics) {
        int visibleHeight = WINDOW_HEIGHT - 40;

        if (totalNodeHeight == 0) {
            //System.out.println("Total node height is zero, skipping scrollbar rendering.");
            return; // Skip rendering the scrollbar if there are no nodes
        }

        int maxScrollOffset = Math.max(0, totalNodeHeight - visibleHeight);
        int scrollBarHeight = Math.max(10, visibleHeight * visibleHeight / totalNodeHeight);

        if (maxScrollOffset > 0) {
            int scrollBarY = topPos + 20 + (int) ((float) scrollOffset / maxScrollOffset * (visibleHeight - scrollBarHeight));
            guiGraphics.fill(leftPos + WINDOW_WIDTH - SCROLL_BAR_WIDTH, scrollBarY, leftPos + WINDOW_WIDTH, scrollBarY + scrollBarHeight, FastColor.ARGB32.color(255, 128, 128, 128));
        } else {
            guiGraphics.fill(leftPos + WINDOW_WIDTH - SCROLL_BAR_WIDTH, topPos + 20, leftPos + WINDOW_WIDTH, topPos + 20 + scrollBarHeight, FastColor.ARGB32.color(255, 128, 128, 128));
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isScrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isScrolling) {
            int visibleHeight = WINDOW_HEIGHT - 40;
            int scrollBarHeight = Math.max(10, visibleHeight * visibleHeight / totalNodeHeight);
            int maxScrollOffset = Math.max(0, totalNodeHeight - visibleHeight);

            double scrollBarY = Mth.clamp(mouseY - topPos - 20 - scrollBarHeight / 2.0, 0, visibleHeight - scrollBarHeight);
            scrollOffset = (int) Mth.clamp((scrollBarY / (visibleHeight - scrollBarHeight)) * maxScrollOffset, 0, maxScrollOffset);

            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovering(leftPos + WINDOW_WIDTH - SCROLL_BAR_WIDTH, topPos + 20, SCROLL_BAR_WIDTH, WINDOW_HEIGHT - 20, (int) mouseX, (int) mouseY)) {
            isScrolling = true;
        } else {
            for (int i = 0; i < nodes.size(); i++) {
                int yStart = topPos + 20 - scrollOffset;
                int yPos = yStart + i * NODE_HEIGHT;
                TalismanNode node = nodes.get(i);

                int checkboxX = leftPos + 180;
                int checkboxY = yPos + 5;
                if (isHovering(checkboxX, checkboxY, 10, 10, (int) mouseX, (int) mouseY)) {
                    toggleTalisman(node);
                    return true;
                }

                if (isHoveringNode(leftPos + 20, yPos, (int) mouseX, (int) mouseY)) {
                    toggleTalisman(node);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void toggleTalisman(TalismanNode node) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            TalismanType talismanType = TalismanType.valueOf(node.talismanName().toUpperCase());
            boolean isEnabled = !talismanStates.get(talismanType.getIndex());
            talismanStates.put(talismanType.getIndex(), isEnabled);
            nodes.set(nodes.indexOf(node), new TalismanNode(node.talismanName(), isEnabled));
            NetworkHandler.sendToggleTalismanStateToLocal(talismanType.getIndex(), isEnabled);
            this.updateTalismanStates();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private boolean isHovering(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static void open() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.execute(() -> {
                //System.out.println("Opening TalismanToggleScreen and requesting talisman states from server.");
                NetworkHandler.sendTalismanStateRequestToServer();
                minecraft.setScreen(new TalismanToggleScreen(Component.literal("Talisman Toggle")));
            });
        }
    }

    record TalismanNode(String talismanName, boolean enabled) {
    }

}
