package net.classic_akk.jaw_lab.Content.Network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class NetworkInteractions {
    public static void createNetwork(ServerLevel level, String networkName, Player player) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        data.createNetwork(networkName, level, player);
    }
    public static void deleteNetwork(ServerLevel level, String networkName, Player player) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            data.deleteNetworkSafe(networkName, player);
        }
    }

    public static void addUser(ServerLevel level, UUID uuid, String nickname, String networkName) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            data.addUser(networkName, new NetworkUser(uuid, nickname, NetworkRole.MEMBER, 0));
        }
    }
    public static void removeUser(ServerLevel level, String networkName, Player player, String targetName) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (!NetworkSecurity.canRemoveUsers(data.getNetwork(networkName).getUser(player.getUUID()))) return;
        if (data.isValidNetwork(networkName)) {
            data.removeUserSafe(networkName, player, data.getUserUUID(networkName, targetName));
        }
    }

    public static void increaseUserLevel(ServerLevel level, String networkName, Player player, String targetName) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            data.incrementLevel(networkName, player, data.getUserUUID(networkName, targetName));
        }
    }
    public static void decreaseUserLevel(ServerLevel level, String networkName, Player player, String targetName) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            data.decrementLevel(networkName, player, data.getUserUUID(networkName, targetName));
        }
    }

    public static void setRole(ServerLevel level, String networkName, Player player, String targetName, NetworkRole role) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (!NetworkSecurity.canChangeRole(data.getNetwork(networkName).getUser(player.getUUID()))) return;
        if (data.getUserRole(networkName, data.getUserUUID(networkName, targetName)) == NetworkRole.FOUNDER) return;
        if (role == NetworkRole.FOUNDER && data.getUserRole(networkName, player.getUUID()) == NetworkRole.FOUNDER) {
            data.setUserRoleSafe(networkName, player, data.getUserUUID(networkName, targetName), role);
            data.setUserRole(networkName, player.getUUID(), NetworkRole.ADMIN);
        }
        data.setUserRoleSafe(networkName, player, data.getUserUUID(networkName, targetName), role);
    }

    public static int getUserAccessLevel(ServerLevel level, String networkName, Player player) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            return data.getUserLevel(networkName, player.getUUID());
        }
        return -1;
    }
    public static int getUserAccessLevel(ServerLevel level, String networkName, String targetName) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            return data.getUserLevel(networkName, data.getUserUUID(networkName, targetName));
        }
        return -1;
    }
    public static NetworkRole getUserRole(ServerLevel level, String networkName, Player player) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            return data.getUserRole(networkName, player.getUUID());
        }
        return null;
    }
    public static NetworkRole getUserStatus(ServerLevel level, String networkName, String targetName) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (data.isValidNetwork(networkName)) {
            return data.getUserRole(networkName, data.getUserUUID(networkName, targetName));
        }
        return null;
    }
}