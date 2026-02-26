package net.classic_akk.ca_lab.Util;

import net.classic_akk.ca_lab.Lab;
import net.classic_akk.ca_lab.Screen.OpenScreen.OpenCopyMenuPacket;
import net.classic_akk.ca_lab.Screen.OpenScreen.OpenMainMenuPacket;
import net.classic_akk.ca_lab.Screen.OpenScreen.OpenNetworkMenuPacket;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class LabPackets {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(Lab.resourceLocation("main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    private static int packetId = 0;

    private static int nextID() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.registerMessage(
                nextID(),
                OpenCopyMenuPacket.class,
                OpenCopyMenuPacket::encode,
                OpenCopyMenuPacket::decode,
                OpenCopyMenuPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER) // клиент → сервер
        );
        INSTANCE.registerMessage(
                nextID(),
                OpenMainMenuPacket.class,
                OpenMainMenuPacket::encode,
                OpenMainMenuPacket::decode,
                OpenMainMenuPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER) // клиент → сервер
        );
        INSTANCE.registerMessage(
                nextID(),
                OpenNetworkMenuPacket.class,
                OpenNetworkMenuPacket::encode,
                OpenNetworkMenuPacket::decode,
                OpenNetworkMenuPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER) // клиент → сервер
        );
    }


    /**
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, net.minecraft.server.level.ServerPlayer player) {
        INSTANCE.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToAll(MSG message, net.minecraft.server.level.ServerLevel level) {
        level.players().forEach(p -> sendToPlayer(message, p));
    }
     */
}