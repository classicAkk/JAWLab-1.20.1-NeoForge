package net.classic_akk.jaw_lab.Content.Network;

public class NetworkSecurity {

    public static boolean canAddUsers(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.ADD_USER);}
    public static boolean canRemoveUsers(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.REMOVE_USER);}
    public static boolean canRemoveAdmins(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.REMOVE_ADMIN);}
    public static boolean canDeleteNetwork(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.DELETE_NETWORK);}
    public static boolean canChangeRole(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.CHANGE_ROLE);}
    public static boolean canChangeLevel(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.CHANGE_LEVEL);}
    public static boolean canChangeCardLevel(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.CHANGE_CARD_LEVEL);}
    public static boolean canChangeNetwork(NetworkUser actor) {return actor.getRole().hasPermission(NetworkPermission.ADD_NETWORK);}
}