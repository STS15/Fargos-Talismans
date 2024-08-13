package com.sts15.fargos.network;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.network.packet.*;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

public class NetworkHandler {

    public static void registerPackets(@NotNull RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Fargos.MODID)
                .versioned("1.0").optional();

        registrar.playToServer(ToggleTalismanStatePacket.TYPE, ToggleTalismanStatePacket.STREAM_CODEC, ToggleTalismanStatePacket::handle);
        registrar.playToClient(SyncAirStatusPacket.TYPE, SyncAirStatusPacket.STREAM_CODEC, SyncAirStatusPacket::handle);
    }

    public static void sendToggleTalismanStateToServer(ServerPlayer player, int talismanIndex, boolean isEnabled) {
        ToggleTalismanStatePacket packet = new ToggleTalismanStatePacket(talismanIndex, isEnabled);
        PacketDistributor.sendToServer(packet);
    }

    public static void sendSyncAirStatusToClient(ServerPlayer player, int air) {
        SyncAirStatusPacket packet = new SyncAirStatusPacket(air);
        PacketDistributor.sendToPlayer(player, packet);
    }
}
