package com.sts15.fargos.network.packet;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.network.TalismanType;
import com.sts15.fargos.network.NetworkHandler;
import com.sts15.fargos.config.PlayerDataHandler;
import com.sts15.fargos.screen.TalismanToggleScreen;
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

public class TalismanStatePacket implements CustomPacketPayload {

    private final Map<Integer, Boolean> talismanStates;

    public TalismanStatePacket(Map<Integer, Boolean> talismanStates) {
        this.talismanStates = talismanStates;
        //System.out.println("TalismanStatePacket created with states: " + talismanStates);
    }

    public TalismanStatePacket(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        this.talismanStates = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int index = buf.readVarInt();
            boolean enabled = buf.readBoolean();
            this.talismanStates.put(index, enabled);
        }
    }

    public static final Type<TalismanStatePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "talisman_state"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, TalismanStatePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.VAR_INT, ByteBufCodecs.BOOL), // Codec for Map<Integer, Boolean>
            TalismanStatePacket::getTalismanStates,                                     // Getter for the map
            TalismanStatePacket::new                                                    // Constructor reference for decoding
    );

    public static void handle(TalismanStatePacket packet, IPayloadContext context) {
        //System.out.println("Handling TalismanStatePacket with states: " + packet.talismanStates);

        if (context.player() instanceof ServerPlayer serverPlayer) {
            // Server side: the client has requested the talisman states
            if (packet.talismanStates.isEmpty()) {
                //System.out.println("Received empty TalismanStatePacket on server, preparing response...");

                Map<Integer, Boolean> talismanStates = new HashMap<>();
                for (TalismanType talismanType : TalismanType.values()) {
                    boolean isEnabled = PlayerDataHandler.getTalismanState(serverPlayer, talismanType.name());
                    talismanStates.put(talismanType.getIndex(), isEnabled);
                }
                //System.out.println("Sending talisman states back to client: " + talismanStates);
                // Send the talisman states back to the client
                NetworkHandler.sendTalismanStateToClient(serverPlayer, talismanStates);
            }
        } else if (context.player().level().isClientSide()) {
            // Client side: the server has responded with the talisman states
            //System.out.println("Received TalismanStatePacket on client with states: " + packet.talismanStates);

            Minecraft.getInstance().execute(() -> {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    packet.talismanStates.forEach((index, isEnabled) -> {
                        TalismanType talismanType = TalismanType.byIndex(index);
                        //System.out.println("Updating talisman state on client: " + talismanType.name() + " = " + isEnabled);
                        PlayerDataHandler.setTalismanState(player, talismanType.name(), isEnabled);
                    });

                    // After updating the state, refresh the UI
                    if (Minecraft.getInstance().screen instanceof TalismanToggleScreen screen) {
                        screen.setTalismanStates(packet.getTalismanStates());
                    }
                }
            });
        }

    }

    public Map<Integer, Boolean> getTalismanStates() {
        //System.out.println("Getting talisman states: " + talismanStates);
        return talismanStates;
    }
}