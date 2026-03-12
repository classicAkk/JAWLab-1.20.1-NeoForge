package net.classicAkk.jaw_lab.Content.Network;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Network {

    private final String name;
    private final Map<UUID, NetworkUser> users = new HashMap<>();

    public Network(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<NetworkUser> getUsers() {
        return users.values();
    }

    public NetworkUser getUser(UUID uuid) {
        return users.get(uuid);
    }

    public NetworkUser getUser(String username) {
        for (NetworkUser user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(NetworkUser user) {
        users.put(user.getUUID(), user);
    }

    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return users.containsKey(uuid);
    }

    public Map<UUID, NetworkUser> getUserMap() {
        return users;
    }
}