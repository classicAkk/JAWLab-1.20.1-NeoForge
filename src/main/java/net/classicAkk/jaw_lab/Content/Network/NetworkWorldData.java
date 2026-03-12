package net.classicAkk.jaw_lab.Content.Network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class NetworkWorldData extends SavedData {
    private static final String DATA_NAME = "jaw_lab_networks";
    /*
     * Vector
     * |---Vector //Network 1 with id = 0
     * |   |---NetworkUser(name) //Name of the network
     * |   |---NetworkUser(uuid, status, accessLevel) //user1
     * |   |---NetworkUser(uuid, status, accessLevel) //user2
     * |---Vector //Network 2 with id = 1
     *     |---NetworkUser(name) //Name of the network
     *     |---NetworkUser(uuid, status, accessLevel) //user1
     *     |---NetworkUser(uuid, status, accessLevel) //user2
     */

    private final Map<String, Network> networks = new HashMap<>();

    public void createNetwork(String name, ServerLevel level, Player owner) {
        if (networks.containsKey(name)) return;
        Network network = new Network(name);
        network.addUser(new NetworkUser(owner.getUUID(), owner.getName().getString(), NetworkRole.FOUNDER, 0));

        System.out.print(owner.getUUID() + " " + owner.getName().getString() + "\n");
        networks.put(name, network);
        setDirty();
    }

    public void deleteNetwork(String networkName) {
        if (networks.remove(networkName) != null) {
            setDirty();
        }
    }
    public void deleteNetworkSafe(String networkName, Player player) {
        Network network = networks.get(networkName);
        NetworkUser actor = network.getUser(player.getUUID());
        if (!NetworkSecurity.canDeleteNetwork(actor)) return;
        if (networks.remove(networkName) != null) {
            setDirty();
        }
    }
    public Network getNetwork(String networkName) {
        return networks.get(networkName);
    }

    public boolean isValidNetwork(String networkName) {
        return networks.containsKey(networkName);
    }

    /** User management */
    public void addUser(String networkName, NetworkUser user) {
        Network network = networks.get(networkName); if (network == null || network.containsUser(user.getUUID())) return;

        network.addUser(user);
        setDirty();
    }
    public void removeUser(String networkName, UUID uuid) {
        Network network = networks.get(networkName); if (network == null) return;

        network.removeUser(uuid);
        setDirty();
    }
    public void removeUserSafe(String networkName, Player player, UUID uuid) {
        Network network = networks.get(networkName); if (network == null) return;
        NetworkUser actor = network.getUser(player.getUUID());
        if (!NetworkSecurity.canRemoveUsers(actor)) return;
        if (network.getUser(uuid).getRole() == NetworkRole.FOUNDER) return;
        if (network.getUser(uuid).getRole() == NetworkRole.ADMIN && !NetworkSecurity.canRemoveAdmins(actor)) return;

        network.removeUser(uuid);
        setDirty();
    }

    public void changeUserLevel(String networkName, Player player, UUID uuid, int cLevel) {
        Network network = networks.get(networkName); if (network == null) return;
        NetworkUser user = network.getUser(uuid);
        NetworkUser actor = network.getUser(player.getUUID());
        NetworkRole role = network.getUser(uuid).getRole();
        if (!NetworkSecurity.canChangeLevel(actor)) return;
        if (role == NetworkRole.FOUNDER || role == NetworkRole.ADMIN) return;

        user.setAccessLevel(cLevel);
        setDirty();
    }
    public void incrementLevel(String networkName, Player player, UUID uuid) {
        Network network = networks.get(networkName); if (network == null) return;
        NetworkUser user = network.getUser(uuid);
        NetworkUser actor = network.getUser(player.getUUID());
        NetworkRole role = network.getUser(uuid).getRole();
        NetworkRole actorRole = actor.getRole();
        if (!NetworkSecurity.canChangeLevel(actor)) return;
        if (role == NetworkRole.FOUNDER || role == NetworkRole.ADMIN) return;
        if (actor.getAccessLevel() > user.getAccessLevel() || actorRole == NetworkRole.FOUNDER || actorRole == NetworkRole.ADMIN) {
            user.setAccessLevel(user.getAccessLevel()+1);
            setDirty();
        }
    }
    public void decrementLevel(String networkName, Player player, UUID uuid) {
        Network network = networks.get(networkName); if (network == null) return;
        NetworkUser user = network.getUser(uuid);
        NetworkUser actor = network.getUser(player.getUUID());
        NetworkRole role = network.getUser(uuid).getRole();
        if (!NetworkSecurity.canChangeLevel(actor)) return;
        if (role == NetworkRole.FOUNDER || role == NetworkRole.ADMIN) return;
        if (user.getAccessLevel() == 0) return;

        user.setAccessLevel(user.getAccessLevel()-1);
        setDirty();
    }

    public boolean canIncrementLevel(String networkName, Player player, UUID uuid) {
        Network network = networks.get(networkName); if (network == null) return false;
        NetworkUser user = network.getUser(uuid);
        NetworkUser actor = network.getUser(player.getUUID());
        NetworkRole role = network.getUser(uuid).getRole();
        NetworkRole actorRole = actor.getRole();
        if (!NetworkSecurity.canChangeLevel(actor)) return false;
        if (role == NetworkRole.FOUNDER || role == NetworkRole.ADMIN) return false;
        if (actor.getAccessLevel() > user.getAccessLevel() || actorRole == NetworkRole.FOUNDER || actorRole == NetworkRole.ADMIN) {
                return true;
        }
        return false;
    }
    public boolean canDecrementLevel(String networkName, Player player, UUID uuid) {
        Network network = networks.get(networkName); if (network == null) return false;
        NetworkUser user = network.getUser(uuid);
        NetworkUser actor = network.getUser(player.getUUID());
        NetworkRole role = network.getUser(uuid).getRole();
        if (!NetworkSecurity.canChangeLevel(actor)) return false;
        if (role == NetworkRole.FOUNDER || role == NetworkRole.ADMIN) return false;
        if (user.getAccessLevel() == 0) return false;

        return true;
    }

    private NetworkUser findUser(String networkName, UUID uuid) {
        Network network = networks.get(networkName);
        if (network == null) return null;

        for (NetworkUser user : network.getUsers()) {
            if (user.getUUID().equals(uuid)) {
                return user;
            }
        }
        return null;
    }
    private NetworkUser findUser(String networkName, String username) {
        Network network = networks.get(networkName);
        if (network == null) return null;

        for (NetworkUser user : network.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void increaseUserLevel(String networkName, UUID uuid) {
        NetworkUser user = findUser(networkName, uuid);if (user == null) return;

        user.setAccessLevel(user.getAccessLevel() + 1);
        setDirty();
    }
    public void decreaseUserLevel(String networkName, UUID uuid) {
        NetworkUser user = findUser(networkName, uuid);if (user == null) return;

        user.setAccessLevel(user.getAccessLevel() - 1);
        setDirty();
    }
    public void setUserLevel(String networkName, UUID uuid, int level) {
        NetworkUser user = findUser(networkName, uuid);if (user == null) return;

        user.setAccessLevel(level);
        setDirty();
    }
    public void setUserRole(String networkName, UUID uuid, NetworkRole role) {
        NetworkUser user = findUser(networkName, uuid);if (user == null) return;
        user.setRole(role);
        setDirty();
    }
    public void setUserRoleSafe(String networkName, Player player, UUID uuid, NetworkRole role) {
        NetworkUser user = findUser(networkName, uuid);if (user == null) return;
        NetworkUser actor = networks.get(networkName).getUser(player.getUUID());
        if (!NetworkSecurity.canChangeRole(actor)) return;
        user.setRole(role);
        setDirty();
    }

    public int getUserLevel(String networkName, UUID uuid) {
        NetworkUser user = findUser(networkName, uuid);
        return user != null ? user.getAccessLevel() : -1;
    }
    public NetworkRole getUserRole(String networkName, UUID uuid) {
        NetworkUser user = findUser(networkName, uuid);
        return user != null ? user.getRole() : null;
    }
    public UUID getUserUUID(String networkName, String username) {
        NetworkUser user = findUser(networkName, username);
        return user != null ? user.getUUID() : null;
    }
    public NetworkUser getUser(String networkName, String username) {
        NetworkUser user = findUser(networkName, getUserUUID(networkName, username));
        return user != null ? user : null;
    }
    public void clear() {
        networks.clear();
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag networkList = new ListTag();
        for (Network network : networks.values()) {
            CompoundTag networkTag = new CompoundTag();
            networkTag.putString("name", network.getName());
            ListTag userList = new ListTag();
            for (NetworkUser user : network.getUsers()) {
                userList.add(user.save());
            }
            networkTag.put("users", userList);
            networkList.add(networkTag);
        }
        tag.put("networks", networkList);
        return tag;
    }

    public static NetworkWorldData load(CompoundTag tag) {
        NetworkWorldData data = new NetworkWorldData();
        ListTag networkList = tag.getList("networks", Tag.TAG_COMPOUND);
        for (int i = 0; i < networkList.size(); i++) {
            CompoundTag networkTag = networkList.getCompound(i);
            String name = networkTag.getString("name");
            Network network = new Network(name);
            ListTag userList = networkTag.getList("users", Tag.TAG_COMPOUND);
            for (int j = 0; j < userList.size(); j++) {
                CompoundTag userTag = userList.getCompound(j);
                NetworkUser user = NetworkUser.load(userTag);
                network.addUser(user);
            }

            data.networks.put(name, network);
        }

        return data;
    }

    public static NetworkWorldData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                NetworkWorldData::load,
                NetworkWorldData::create,
                DATA_NAME
        );
    }

    public static NetworkWorldData create() {
        return new NetworkWorldData();
    }
}