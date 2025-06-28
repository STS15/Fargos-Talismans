package com.sts15.fargos.network.packet;

import com.sts15.fargos.Fargos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncFireShieldPacket implements CustomPacketPayload {
    private final int duration;

    public SyncFireShieldPacket(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static final Type<SyncFireShieldPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "sync_fire_shield"));

    public static final StreamCodec<FriendlyByteBuf, SyncFireShieldPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    SyncFireShieldPacket::getDuration,
                    SyncFireShieldPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncFireShieldPacket packet, IPayloadContext context) {
        Player player = context.player();
        if (player != null) {
            player.getPersistentData().putInt("FireShieldDuration", packet.duration);
        }
    }
}
