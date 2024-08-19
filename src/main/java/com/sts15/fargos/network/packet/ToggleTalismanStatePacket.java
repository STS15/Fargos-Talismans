package com.sts15.fargos.network.packet;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.network.TalismanType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ToggleTalismanStatePacket implements CustomPacketPayload {

    private final int talismanIndex;
    private final boolean isEnabled;

    public ToggleTalismanStatePacket(int talismanIndex, boolean isEnabled) {
        this.talismanIndex = talismanIndex;
        this.isEnabled = isEnabled;
    }

    public static final Type<ToggleTalismanStatePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "toggle_talisman_state"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, ToggleTalismanStatePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,            // Codec for an integer representing the talisman index
            ToggleTalismanStatePacket::getTalismanIndex,   // Getter for the talisman index
            ByteBufCodecs.BOOL,               // Codec for a boolean representing the enabled state
            ToggleTalismanStatePacket::isEnabled,          // Getter for the boolean state
            ToggleTalismanStatePacket::new                 // Constructor reference for decoding
    );

    private Integer getTalismanIndex() {
        return talismanIndex;
    }

    private Boolean isEnabled() {
        return isEnabled;
    }

    public static void handle(ToggleTalismanStatePacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            int talismanIndex = packet.talismanIndex;
            boolean isEnabled = packet.isEnabled;

            TalismanType talismanType = TalismanType.byIndex(talismanIndex);
            PlayerDataHandler.setTalismanState(player, talismanType.name(), isEnabled);
            //player.sendSystemMessage(Component.literal("Talisman " + talismanType.name() + " is now " + (isEnabled ? "enabled" : "disabled")));
        }
    }
}
