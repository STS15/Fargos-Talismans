package com.sts15.fargos.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncAirStatusPacket implements CustomPacketPayload {

    private final int air;

    public SyncAirStatusPacket(int air) {
        this.air = air;
    }

    public static final Type<SyncAirStatusPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("fargostalismans", "sync_air_status"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public int getAir() {
        return air;
    }

    public static final StreamCodec<FriendlyByteBuf, SyncAirStatusPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SyncAirStatusPacket::getAir,
            SyncAirStatusPacket::new
    );

    public static void handle(SyncAirStatusPacket packet, IPayloadContext context) {
        if (context.player() != null) {
            Player player = context.player();
            player.setAirSupply(packet.air);
        }
    }
}
