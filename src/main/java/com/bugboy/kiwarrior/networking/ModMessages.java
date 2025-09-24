package com.bugboy.kiwarrior.networking;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.networking.packets.C2SBlastSpawnEntityPacket;
import com.bugboy.kiwarrior.networking.packets.KiS2CSyncPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraft.server.level.ServerPlayer;

public class ModMessages {
    public static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(KiWarrior.MODID))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(KiS2CSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(KiS2CSyncPacket::new)
                .encoder(KiS2CSyncPacket::toBytes)
                .consumerMainThread(KiS2CSyncPacket::handle)
                .add();

        net.messageBuilder(C2SBlastSpawnEntityPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SBlastSpawnEntityPacket::new)
                .encoder(C2SBlastSpawnEntityPacket::encode)
                .consumerMainThread(C2SBlastSpawnEntityPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
