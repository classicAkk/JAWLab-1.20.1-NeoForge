package net.classicAkk.jaw_lab.Content.Network;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class NetworkUser {
    private final UUID uuid;
    private final String username;
    private NetworkRole role;
    private int accessLevel;

    public NetworkUser(UUID uuid, String username, NetworkRole role, int accessLevel) {
        this.uuid = uuid;
        this.username = username;
        this.role = role;
        this.accessLevel = accessLevel;
    }

    public UUID getUUID() {
        return uuid;
    }
    public String getUsername() {
        return username;
    }
    public NetworkRole getRole() {
        return role;
    }
    public int getAccessLevel() {
        return accessLevel;
    }

    public void setRole(NetworkRole role) {
        this.role = role;
    }

    public void setAccessLevel(int level) {
        this.accessLevel = level;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("uuid", uuid);
        tag.putString("username", username);
        tag.putString("role", role.name());
        tag.putInt("accessLevel", accessLevel);

        return tag;
    }

    public static NetworkUser load(CompoundTag tag) {
        UUID uuid = tag.getUUID("uuid");
        String username = tag.getString("username");
        NetworkRole role = NetworkRole.valueOf(tag.getString("role"));
        int accessLevel = tag.getInt("accessLevel");

        return new NetworkUser(uuid, username, role, accessLevel);
    }
}