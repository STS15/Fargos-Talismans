package com.sts15.fargos.network.packet;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.config.ServerConfig;
import com.sts15.fargos.config.ServerDataHandler;
import com.sts15.fargos.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerTalismanConfigPacket implements CustomPacketPayload {

    private final String playerUUID;
    private final Map<String, Boolean> talismanStates;

    // Constructor for sending data (from server to client)
    public ServerTalismanConfigPacket(String playerUUID, Map<String, Boolean> talismanStates) {
        this.playerUUID = playerUUID;
        this.talismanStates = talismanStates;
    }

    // Define the packet type
    public static final Type<ServerTalismanConfigPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "server_talisman_config"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Codec for serialization and deserialization using composite
    public static final StreamCodec<FriendlyByteBuf, ServerTalismanConfigPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, // Codec for playerUUID (STRING_UTF8 codec)
            ServerTalismanConfigPacket::getPlayerUUID, // Getter for playerUUID

            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.BOOL), // Codec for Map<String, Boolean>
            ServerTalismanConfigPacket::getTalismanStates, // Getter for talismanStates

            // Constructor to assemble playerUUID and talismanStates into a new instance of ServerTalismanConfigPacket
            ServerTalismanConfigPacket::new
    );

    public String getPlayerUUID() {
        return playerUUID;
    }

    public Map<String, Boolean> getTalismanStates() {
        return talismanStates;
    }

    // Packet handling method
    public static void handle(ServerTalismanConfigPacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer serverPlayer) {
            // Server side: handle the packet here
            Map<String, Boolean> talismanStates = new HashMap<>(ServerConfig.talismanEnabledStates);
            NetworkHandler.sendServerTalismanConfigToClient(serverPlayer, UUID.fromString(packet.playerUUID), talismanStates);
        } else if (context.player().level().isClientSide()) {
            // Client side: receive the talisman states from the server
            Minecraft.getInstance().execute(() -> {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && player.getUUID().toString().equals(packet.playerUUID)) {
                    // Update ServerDataHandler on the client with the received data
                    ServerDataHandler.setServerTalismanStates(packet.getTalismanStates());
                    ServerDataHandler.saveServerTalismanStatesToPlayer(player); // Optionally save the states to NBT
                }
            });
        }
    }
}
