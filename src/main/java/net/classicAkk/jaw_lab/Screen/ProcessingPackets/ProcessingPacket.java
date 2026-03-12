package net.classicAkk.jaw_lab.Screen.ProcessingPackets;

import net.classicAkk.jaw_lab.Content.Interactions.CodeDoorInteractions;
import net.classicAkk.jaw_lab.Content.Interactions.KeycardInteractions;
import net.classicAkk.jaw_lab.Content.Interactions.NetworkInteractions;
import net.classicAkk.jaw_lab.Content.Network.NetworkRole;
import net.classicAkk.jaw_lab.Content.Network.UUIDFetcher;
import net.classicAkk.jaw_lab.Screen.CodeDoor.CodeDoorMenu;
import net.classicAkk.jaw_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.classicAkk.jaw_lab.Screen.KCPMain.KeycardProgrammatorMainMenu;
import net.classicAkk.jaw_lab.Screen.KCPNetwork.KeycardProgrammatorNetworkMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProcessingPacket {

    private static int containerId;
    private static NetworkRole role;
    private static String type;
    private static String parameter;
    private static String networkName;
    private static Level level;
    private static ServerLevel serverLevel;
    private static MinecraftServer server;
    private static Player player;
    private static BlockEntity blockEntity;

    public ProcessingPacket(int containerId, String type) {
        ProcessingPacket.containerId = containerId;
        ProcessingPacket.type = type;
    }
    public ProcessingPacket(int containerId, String type, String parameter) {
        ProcessingPacket.containerId = containerId;
        ProcessingPacket.type = type;
        ProcessingPacket.parameter = parameter;
    }
    public ProcessingPacket(int containerId, String type, Player player, String parameter) {
        ProcessingPacket.containerId = containerId;
        ProcessingPacket.type = type;
        ProcessingPacket.player = player;
        ProcessingPacket.parameter = parameter;
    }
    public ProcessingPacket(Level level, Player player, String networkName, String type) {
        ProcessingPacket.type = type;
        ProcessingPacket.level = level;
        ProcessingPacket.player = player;
        ProcessingPacket.networkName = networkName;
        if (!level.isClientSide()) {
            ProcessingPacket.serverLevel = (ServerLevel) level;
        }
    }
    public ProcessingPacket(Level level, Player player, String networkName, String type, String parameter) {
        ProcessingPacket.type = type;
        ProcessingPacket.level = level;
        ProcessingPacket.player = player;
        ProcessingPacket.networkName = networkName;
        ProcessingPacket.parameter = parameter;
        if (!level.isClientSide()) {
            ProcessingPacket.serverLevel = (ServerLevel) level;
        }
    }
    public ProcessingPacket(Level level, Player player, String networkName, String type, String parameter, NetworkRole role) {
        ProcessingPacket.type = type;
        ProcessingPacket.level = level;
        ProcessingPacket.player = player;
        ProcessingPacket.networkName = networkName;
        ProcessingPacket.parameter = parameter;
        ProcessingPacket.role = role;
        if (!level.isClientSide()) {
            ProcessingPacket.serverLevel = (ServerLevel) level;
        }
    }
    public ProcessingPacket(Level level, BlockEntity blockEntity, String type, String parameter, Player player) {
        ProcessingPacket.level = level;
        ProcessingPacket.blockEntity = blockEntity;
        ProcessingPacket.type = type;
        ProcessingPacket.parameter = parameter;
        ProcessingPacket.player = player;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(containerId);
    }

    public static ProcessingPacket decode(FriendlyByteBuf buf) {
        return new ProcessingPacket(containerId, type);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {return;}

            if (serverPlayer.containerMenu instanceof KeycardProgrammatorMainMenu MainMenu) {
                switch(type) {
                    case("resetKeycard"):{KeycardInteractions.resetKeycard(MainMenu, serverPlayer, 36); break;}
                    case("uuid"): {KeycardInteractions.setUUID(MainMenu, serverPlayer, 36); break;}
                    case("owner"):{KeycardInteractions.setUsername(MainMenu, 36, parameter); break;}
                    case("increaseLevel"): {KeycardInteractions.increaseLevel(MainMenu, 36, serverLevel, networkName, player); break;}
                    case("decreaseLevel"): {KeycardInteractions.decreaseLevel(MainMenu, 36, serverLevel, networkName, player); break;}
                    case("resetLevel"): {KeycardInteractions.resetLevel(MainMenu, 36); break;}
                    case("addNetwork"): {KeycardInteractions.addNetwork(MainMenu, level, 36, parameter, player); break;}
                    case("removeNetwork"): {KeycardInteractions.removeNetwork(MainMenu, 36); break;}
                    default: {System.out.print("Jaw Lab: net/classic_akk/jaw_lab/Screen/ProcessingPackets\n"); break;}
                }
            }
            if (serverPlayer.containerMenu instanceof KeycardProgrammatorNetworkMenu NetworkMenu) {
                switch(type) {
                    case("createNetwork"): {NetworkInteractions.createNetwork(serverLevel, networkName, player); break;}
                    case("deleteNetwork"): {NetworkInteractions.deleteNetwork(serverLevel, networkName, player); break;}
                    case("addUser"): {UUIDFetcher.getPlayerUUIDAsync(ctx.get().getSender().getServer(), parameter).thenAccept(uuid -> {
                        if (uuid != null) {NetworkInteractions.addUser(serverLevel, uuid, parameter, networkName);}
                        else {System.out.println("Cannot get the UUID of "+parameter);}});break;}
                    case("removeUser"): {NetworkInteractions.removeUser(serverLevel, networkName, player, parameter); break;}
                    case("increaseUserLevel"): {NetworkInteractions.increaseUserLevel(serverLevel, networkName, player, parameter); break;}
                    case("decreaseUserLevel"): {NetworkInteractions.decreaseUserLevel(serverLevel, networkName, player, parameter); break;}
                    case("setRole"): {NetworkInteractions.setRole(serverLevel, networkName, player, parameter, role); break;}
                    default: {System.out.print("Jaw Lab: net/classic_akk/jaw_lab/Screen/ProcessingPackets\n"); break;}
                }
            }
            if (serverPlayer.containerMenu instanceof KeycardProgrammatorCopyMenu CopyMenu) {
                switch(type) {
                    case("copyCard"): {KeycardInteractions.copyCard(CopyMenu, 36); break;}
                    default: {System.out.print("Jaw Lab: net/classic_akk/jaw_lab/Screen/ProcessingPackets\n"); break;}
                }
            }
            if (serverPlayer.containerMenu instanceof CodeDoorMenu CodeDoorMenu) {
                switch(type) {
                    case("openDoor"): {CodeDoorInteractions.setDoor(blockEntity, level, player);break;}
                    case("setCode"): {CodeDoorInteractions.setCode(blockEntity, parameter, player);break;}
                    case("error"): {CodeDoorInteractions.error(blockEntity, level, player);break;}
                    default: {System.out.print("Jaw Lab: net/classic_akk/jaw_lab/Screen/ProcessingPackets\n"); break;}
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}