package com.sts15.fargos.network.packet;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public class TalismanConfigPacket implements CustomPacketPayload {
    // A map storing the talisman states (enabled/disabled) for the client
    private final Map<String, Boolean> talismanStates;

    // Defines a unique type for this packet using a custom ResourceLocation identifier
    public static final CustomPacketPayload.Type<TalismanConfigPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "config_sync"));

    // StreamCodec is used to handle encoding and decoding of the packet's data
    public static final StreamCodec<FriendlyByteBuf, TalismanConfigPacket> STREAM_CODEC = StreamCodec.composite(
            // Defines how to decode the map from the buffer
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.BOOL),
            // Function to extract the talismanStates map from the packet instance
            TalismanConfigPacket::getTalismanStates,
            // Constructor to create a new TalismanConfigPacket from the decoded map
            TalismanConfigPacket::new
    );

    // Constructor for creating the packet with a map of talisman states
    public TalismanConfigPacket(Map<String, Boolean> talismanStates) {
        this.talismanStates = talismanStates;
    }

    // Constructor for decoding the packet from a FriendlyByteBuf
    public TalismanConfigPacket(FriendlyByteBuf buf) {
        // Initialize the map that will store the talisman states
        this.talismanStates = new HashMap<>();
        // Read the number of talismans
        int size = buf.readInt();
        // Loop through and read each talisman and its state
        for (int i = 0; i < size; i++) {
            String talisman = buf.readUtf();
            boolean state = buf.readBoolean();
            talismanStates.put(talisman, state);
        }
    }

    // Static method to decode a packet from the buffer
    public static TalismanConfigPacket decode(FriendlyByteBuf buf) {
        // Use the map codec to decode the map from the buffer
        Map<String, Boolean> talismanStates = ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.BOOL).decode(buf);
        // Return a new TalismanConfigPacket using the decoded map
        return new TalismanConfigPacket(talismanStates);
    }

    // Method to encode the packet data into the buffer
    public void encode(FriendlyByteBuf buf) {
        // Use the map codec to encode the map into the buffer
        ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.BOOL)
                .encode(buf, (HashMap<String, Boolean>) talismanStates);
    }

    public void handle(IPayloadContext ctx) {
        if (ctx.player() instanceof ServerPlayer serverPlayer) {
            // Log when the packet is received
            System.out.println("Received TalismanConfigPacket from client: " + serverPlayer.getName().getString());

            // Log the contents of the packet
            for (Map.Entry<String, Boolean> entry : talismanStates.entrySet()) {
                System.out.println("Talisman: " + entry.getKey() + ", Enabled: " + entry.getValue());
            }

            NetworkHandler.handleTalismanConfigFromClient(serverPlayer, talismanStates);
        }
    }

    // Method to return the type of this packet
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Getter method to access the talismanStates map
    public Map<String, Boolean> getTalismanStates() {
        return talismanStates;
    }
}
