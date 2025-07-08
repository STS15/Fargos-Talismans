package com.sts15.fargos.client.elytra;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sts15.fargos.items.crafted.BasicElytraItem;
import com.sts15.fargos.items.providers.Soul_of_Flight_Mastery_Provider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class MyElytraLayer
        extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final ElytraModel<AbstractClientPlayer> elytraModel;

    public MyElytraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
        this.elytraModel = new ElytraModel<>(
                Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.ELYTRA)
        );
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       AbstractClientPlayer player,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTicks,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        ResourceLocation textureToRender = null;

        // Case 1: Elytra equipped in Curios
        var elytraCurio = CuriosApi.getCuriosHelper().findEquippedCurio(
                stack -> stack.getItem() instanceof BasicElytraItem,
                player
        );

        var slotResultOptional = CuriosApi.getCuriosInventory(player)
                .flatMap(inv -> inv.findFirstCurio(stack -> stack.getItem() instanceof BasicElytraItem));

        if (slotResultOptional.isEmpty()) return;
        var slotResult = slotResultOptional.get();
        if (!slotResult.slotContext().visible()) return;

        if (elytraCurio.isPresent()) {
            ItemStack stack = elytraCurio.get().getRight();
            BasicElytraItem elytraItem = (BasicElytraItem) stack.getItem();
            textureToRender = elytraItem.getElytraTexture();
        } else {
            // Case 2: Soul of Flight Mastery is equipped and player is flying
            boolean hasSoul = CuriosApi.getCuriosHelper().findEquippedCurio(
                    stack -> stack.getItem() instanceof Soul_of_Flight_Mastery_Provider,
                    player
            ).isPresent();

            if (hasSoul && (player.isFallFlying() || player.getAbilities().flying)) {
                // Use a default or custom Soul of Flight wing texture
                textureToRender = ResourceLocation.fromNamespaceAndPath("fargos", "textures/entity/elytra/soul_of_flight_mastery.png");
            }
        }

        // If nothing to render, exit
        if (textureToRender == null) return;

        // Proceed with rendering
        getParentModel().copyPropertiesTo(elytraModel);
        elytraModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        var vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(textureToRender));
        elytraModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

}
