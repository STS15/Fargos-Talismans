package com.sts15.fargos.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.sts15.fargos.Fargos;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SimpleMessagePacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SimpleMessagePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "simple_message"));

    private final String message;

    public SimpleMessagePacket(String message) {
        this.message = message;
    }

    // Define the StreamCodec for SimpleMessagePacket
    public static final StreamCodec<FriendlyByteBuf, SimpleMessagePacket> STREAM_CODEC = StreamCodec.of(
            SimpleMessagePacket::encode,
            SimpleMessagePacket::decode
    );

    // Getter for the message
    public String getMessage() {
        return message;
    }

    public static void encode(FriendlyByteBuf buf, SimpleMessagePacket packet) {
        buf.writeUtf(packet.message);
    }

    public static SimpleMessagePacket decode(FriendlyByteBuf buf) {
        return new SimpleMessagePacket(buf.readUtf(32767));
    }

    public void handle(IPayloadContext ctx) {
        System.out.println("Received message from client: " + message);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
