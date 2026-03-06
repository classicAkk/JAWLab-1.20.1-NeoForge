package net.classic_akk.jaw_lab.Content;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private final List<List<NetworkUser>> dataList = new ArrayList<>();

    public List<List<NetworkUser>> getData() {
        return dataList;
    }

    public int getNetworkId(String username) {
        System.out.print("Get network id method" + "\n");
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).get(0).getUsername().equals(username)) {
                System.out.print("Got network id: " + i + "\n");
                return i;
            }
        }
        return -1;
    }

    public boolean isValidNetwork(String name) {
        System.out.print("Is valid network Method");
        for (List<NetworkUser> networkUsers : dataList) {
            if (!networkUsers.isEmpty() && name.equals(networkUsers.get(0).getUsername())) {
                System.out.print(": Valid" + "\n");
                return true;
            }
        }
        System.out.print(": Invalid" + "\n");
        return false;
    }

    public void createNetwork(String networkName, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        System.out.print("Create network Method" + "\n");
        if (!isValidNetwork(networkName)) {
            List<NetworkUser> row = new ArrayList<>();
            row.add(new NetworkUser(networkName));

            dataList.add(row);

            data.setDirty();
            System.out.print("All data saved" + "\n---\n");
        }
    }
    public void createNetwork(String networkName, ServerLevel level, Player player) {
        NetworkWorldData data = NetworkWorldData.get(level);
        System.out.print("Create network Method" + "\n");
        if (!isValidNetwork(networkName)) {
            List<NetworkUser> row = new ArrayList<>();
            row.add(new NetworkUser(networkName));
            row.add(new NetworkUser(player.getUUID(), player.getName().toString(), "owner", 0));

            dataList.add(row);

            data.setDirty();
            System.out.print("All data saved" + "\n---\n");
        }
    }
    public void deleteNetwork(int id, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        dataList.remove(id);
        data.setDirty();
    }
    public void deleteNetworkSafe(Player player, int networkId, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        System.out.print("Delete network method" + "\n");
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getStatus().equals("owner") && dataList.get(networkId).get(i).getUsername().equals(player.getName().toString())) {
                System.out.print("Deleted network" + "\n---\n");
                dataList.remove(networkId);
                data.setDirty();
                break;
            }
        }
    }

    public void addUser(int networkId, NetworkUser user, ServerLevel level) {
        System.out.print("Add new user method" + "\n");
        if (!isValidUser(user.getUsername(), networkId)) {
            NetworkWorldData data = NetworkWorldData.get(level);
            dataList.get(networkId).add(user);
            System.out.print("Added new user" + "\n---\n");
            data.setDirty();
        }
    }
    public void removeUser(int networkId, NetworkUser user, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        System.out.print("Delete user method" + "\n");
        dataList.get(networkId).remove(user);
        System.out.print("Deleted user" + "\n---\n");
        data.setDirty();
    }
    public void removeUserSafe(int networkId, NetworkUser user, int id, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        System.out.print("Deleted user method" + "\n");

        if ((dataList.get(networkId).get(id).getAccessLevel() > user.getAccessLevel() && !dataList.get(networkId).get(id).getStatus().equals("owner"))
                || (dataList.get(networkId).get(id).getStatus().equals("owner")) && dataList.get(networkId).get(id).getUUID() == user.getUUID()) {
            dataList.get(networkId).remove(id);
            System.out.print("Deleted user" + "\n---\n");
            data.setDirty();
        }
    }
    public void removeUserSafe(int networkId, String username, int id, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        System.out.print("Deleted user method" + "\n");

        if ((dataList.get(networkId).get(id).getAccessLevel() > getUserLevel(networkId, username) && !dataList.get(networkId).get(id).getStatus().equals("owner"))
                || (dataList.get(networkId).get(id).getStatus().equals("owner")) && dataList.get(networkId).get(id).getUUID() == getUserUUId(networkId, username)) {
            dataList.get(networkId).remove(id);
            System.out.print("Deleted user" + "\n---\n");
            data.setDirty();
        }
    }
    public boolean isValidUser(String name) {
        for(int i = 0; i < dataList.size(); i++) {
            for(int j = 0; j < dataList.size(); j++) {
                if (dataList.get(i).get(j).getUsername().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isValidUser(String name, int networkId) {
        System.out.print("Is valid user Method");
        for(int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(name)) {
                System.out.print(": Valid" + "\n");
                return true;
            }
        }
        System.out.print(": Invalid" + "\n");
        return false;
    }

    public void increaseUserLevel(int id, NetworkUser networkUser, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        for (int i = 0; i < dataList.get(id).size(); i++) {
            if (dataList.get(id).get(i).equals(networkUser)) {
                networkUser.setAccessLevel(networkUser.getAccessLevel()+1);
                data.setDirty();
                break;
            }
        }
    }
    public void increaseUserLevelSafe(int networkId, Player player, String targetName, ServerLevel level) {
        System.out.print("=====================================Increase user level Method====================================================\n");
        NetworkWorldData data = NetworkWorldData.get(level);
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            System.out.print("Iteration: "+i+"\n");
            if (dataList.get(networkId).get(i).getUsername().equals(player.getName().toString())
                    && (dataList.get(networkId).get(getUserId(networkId, targetName)).getAccessLevel() > dataList.get(networkId).get(i).getAccessLevel()-1
                    || dataList.get(networkId).get(getUserId(networkId, player.getName().toString())).getStatus().equals("owner"))) {
                System.out.print("B:"+getUserLevel(networkId, player)+"\n");
                dataList.get(networkId).get(i).setAccessLevel(getUserLevel(networkId, player)+1);
                System.out.print("A:"+getUserLevel(networkId, player)+"\n");
                data.setDirty();
                break;
            }
        }
    }
    public void decreaseUserLevel(int networkId, NetworkUser networkUser, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).equals(networkUser)) {
                networkUser.setAccessLevel(networkUser.getAccessLevel()-1);
                data.setDirty();
                break;
            }
        }
    }
    public void decreaseUserLevelSafe(int networkId, Player player, String targetName, ServerLevel level) {
        System.out.print("Increase user level Method\n");
        NetworkWorldData data = NetworkWorldData.get(level);
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            System.out.print("Iteration: "+i+"\n");
            if (dataList.get(networkId).get(i).getUsername().equals(player.getName().toString())
                    && (dataList.get(networkId).get(getUserId(networkId, targetName)).getAccessLevel() > dataList.get(networkId).get(i).getAccessLevel()
                    || dataList.get(networkId).get(getUserId(networkId, player.getName().toString())).getStatus().equals("owner"))) {
                System.out.print("B:"+getUserLevel(networkId, player)+"\n");
                dataList.get(networkId).get(i).setAccessLevel(getUserLevel(networkId, player)-1);
                System.out.print("A:"+getUserLevel(networkId, player)+"\n");
                data.setDirty();
                break;
            }
        }
    }
    public void setUserLevel(int networkId, NetworkUser networkUser, int cLevel, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(networkUser.getUsername())) {
                networkUser.setAccessLevel(cLevel);
                data.setDirty();
                break;
            }
        }
    }
    public void setUserLevel(int networkId, String username, int cLevel, ServerLevel level) {
        NetworkWorldData data = NetworkWorldData.get(level);
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(username)) {
                dataList.get(networkId).get(i).setAccessLevel(cLevel);
                data.setDirty();
                break;
            }
        }
    }

    public int getUserLevel(int networkId, Player player) {
        System.out.print("Get user level method" + "\n");
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            System.out.print("Data:"+dataList.get(networkId).get(i).getUUID() + " " + player.getUUID() + "\n");
            if (dataList.get(networkId).get(i).getUsername().equals(player.getName().toString())) {
                System.out.print("Got user level" + dataList.get(networkId).get(i).getAccessLevel() + "\n");
                return dataList.get(networkId).get(i).getAccessLevel();
            }
        }
        return -1;
    }
    public int getUserLevel(int networkId, String username) {
        System.out.print("Get user level method" + "\n");
        int id = getUserId(networkId, username);
        if (id == -1) {return -1;}
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            //System.out.print("Data:"+dataList.get(networkId).get(i).getUUID() + " " + dataList.get(networkId).get(getUserId(networkId, username)).getUUID() + "\n");
            if (dataList.get(networkId).get(i).getUsername().equals(dataList.get(networkId).get(id).getUsername())) {
                System.out.print("Got user level: " + dataList.get(networkId).get(i).getAccessLevel() + "\n");
                return dataList.get(networkId).get(i).getAccessLevel();
            }
        }
        return -1;
    }
    public int getUserId(int networkId, String name) {
        System.out.print("Get user id method" + "\n");
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(name)) {
                System.out.print("Got user id" + i +"\n");
                return i;
            }
        }
        return -1;
    }
    public UUID getUserUUId(int networkId, String name) {
        System.out.print("Get user UUID method" + "\n");
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(name)) {
                System.out.print("Got user UUID" + dataList.get(networkId).get(i).getUUID() + "\n");
                return dataList.get(networkId).get(i).getUUID();
            }
        }
        return null;
    }
    public String getUserStatus(int networkId, Player player) {
        System.out.print("Get user status method" + "\n");
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(player.getName().toString())) {
                System.out.print("Got user status" + dataList.get(networkId).get(i).getStatus() + "\n");
                return dataList.get(networkId).get(i).getStatus();
            }
        }
        return null;
    }
    public String getUserStatus(int networkId, String username) {
        System.out.print("Get user status method" + "\n");
        for (int i = 0; i < dataList.get(networkId).size(); i++) {
            if (dataList.get(networkId).get(i).getUsername().equals(username)) {
                System.out.print("Got user status" + dataList.get(networkId).get(i).getStatus() + "\n");
                return dataList.get(networkId).get(i).getStatus();
            }
        }
        return null;
    }

    public void clear() {
        dataList.clear();
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag outerList = new ListTag();
        for (List<NetworkUser> innerVector : dataList) {
            ListTag innerList = new ListTag();
            for (NetworkUser user : innerVector) {
                innerList.add(user.save());
            }

            outerList.add(innerList);
        }

        tag.put("jaw_lab_networks", outerList);
        return tag;
    }

    public static NetworkWorldData load(CompoundTag tag) {
        NetworkWorldData data = new NetworkWorldData();
        ListTag outerList = tag.getList("jaw_lab_networks", Tag.TAG_LIST);
        for (Tag outerTag : outerList) {
            ListTag innerList = (ListTag) outerTag;
            List<NetworkUser> innerVector = new ArrayList<>();
            for (Tag innerTag : innerList) {
                CompoundTag userTag = (CompoundTag) innerTag;
                innerVector.add(NetworkUser.load(userTag));
            }

            data.dataList.add(innerVector);
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