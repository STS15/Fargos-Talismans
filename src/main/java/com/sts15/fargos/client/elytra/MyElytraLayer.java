package com.sts15.fargos.client.elytra;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sts15.fargos.items.crafted.BasicElytraItem;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

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

        // 1) Check Curios for the "back" slot item that is instanceof MyVariantElytraItem
        var optionalElytra = CuriosApi.getCuriosHelper().findEquippedCurio(
                stack -> stack.getItem() instanceof BasicElytraItem,
                player
        );

        if (optionalElytra.isEmpty()) {
            return;
        }

        // 3) Retrieve the item + slot info if needed
        ImmutableTriple<String, Integer, ItemStack> triple = optionalElytra.get();
        ItemStack elytraStack = triple.getRight();

        // 4) We now know the player has our Elytra in the "back" curio slot:
        BasicElytraItem myElytra = (BasicElytraItem) elytraStack.getItem();

        // 5) Copy player model properties to the elytra model
        getParentModel().copyPropertiesTo(elytraModel);
        elytraModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // 6) Build a vertex consumer with your custom texture
        ResourceLocation elytraTexture = myElytra.getElytraTexture();
        var vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(elytraTexture));

        // 7) Render exactly like vanilla's Elytra
        elytraModel.renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );
    }
}
