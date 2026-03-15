package net.classicAkk.jaw_lab.Content.Network;

import java.util.Set;

public enum NetworkRole {

    FOUNDER(Set.of(
            NetworkPermission.ADD_USER,
            NetworkPermission.REMOVE_USER,
            NetworkPermission.REMOVE_ADMIN,
            NetworkPermission.CHANGE_LEVEL,
            NetworkPermission.CHANGE_CARD_LEVEL,
            NetworkPermission.CHANGE_ROLE,
            NetworkPermission.DELETE_NETWORK,
            NetworkPermission.ADD_NETWORK,
            NetworkPermission.CHANGE_DOORS
    )),

    ADMIN(Set.of(
            NetworkPermission.ADD_USER,
            NetworkPermission.REMOVE_USER,
            NetworkPermission.CHANGE_LEVEL,
            NetworkPermission.CHANGE_CARD_LEVEL,
            NetworkPermission.CHANGE_ROLE,
            NetworkPermission.ADD_NETWORK,
            NetworkPermission.CHANGE_DOORS
    )),

    MODERATOR(Set.of(
            NetworkPermission.CHANGE_LEVEL,
            NetworkPermission.REMOVE_USER,
            NetworkPermission.ADD_USER
    )),

    MEMBER(Set.of());

    private final Set<NetworkPermission> permissions;

    NetworkRole(Set<NetworkPermission> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(NetworkPermission permission) {
        return permissions.contains(permission);
    }
}