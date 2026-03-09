package net.classic_akk.jaw_lab.Content.Interactions;

import net.classic_akk.jaw_lab.Content.Network.NetworkSecurity;
import net.classic_akk.jaw_lab.Content.Network.NetworkWorldData;
import net.classic_akk.jaw_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.classic_akk.jaw_lab.Screen.KCPMain.KeycardProgrammatorMainMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class KeycardInteractions {
    public static void increaseLevel(KeycardProgrammatorMainMenu menu, int id, ServerLevel serverLevel, String networkName, Player player) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!NetworkSecurity.canChangeCardLevel(NetworkWorldData.get((ServerLevel) serverLevel).getNetwork(networkName).getUser(player.getUUID()))) return;
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            int level = tag.getInt("cLevel");
            tag.putInt("cLevel", level + 1);
            menu.getSlot(id).setChanged();
        }
    }
    public static void decreaseLevel(KeycardProgrammatorMainMenu menu, int id, ServerLevel serverLevel, String networkName, Player player) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!NetworkSecurity.canChangeCardLevel(NetworkWorldData.get((ServerLevel) serverLevel).getNetwork(networkName).getUser(player.getUUID()))) return;
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains("cLevel") || tag.getInt("cLevel") < -1) {tag.putInt("cLevel", 0);menu.getSlot(id).setChanged();return;}
            tag.putInt("cLevel", tag.getInt("cLevel")-1);
            menu.getSlot(id).setChanged();
        }
    }

    public static void resetLevel(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains("cLevel")) {tag.putInt("cLevel", 0);}
            tag.putInt("cLevel", 0);
            menu.getSlot(id).setChanged();
        }
    }

    public static void copyCard(KeycardProgrammatorCopyMenu menu, int id) {
        ItemStack stackS = menu.getSlot(id).getItem();
        ItemStack stackP = menu.getSlot(id+1).getItem();
        if (!stackS.isEmpty() && !stackP.isEmpty()) {
            CompoundTag tagS = stackS.getOrCreateTag();
            CompoundTag tagP = stackP.getOrCreateTag();

            tagP.putInt("cLevel", tagS.getInt("cLevel"));
            menu.getSlot(id+1).setChanged();
        }
    }

    public static void setUUID(KeycardProgrammatorMainMenu menu, Player player, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString("cUUID", player.getStringUUID());
            menu.getSlot(id).setChanged();
        }
    }
    public static void setUsername(KeycardProgrammatorMainMenu menu, int id, String owner) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString("cOwner", owner);
            menu.getSlot(id).setChanged();
        }
    }

    public static void resetUsername(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString("cOwner", "none");
            menu.getSlot(id).setChanged();
        }
    }
    public static void resetUUID(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString("cUUID", "none");
            menu.getSlot(id).setChanged();
        }
    }
    public static void resetNetwork(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString("cNetwork", "none");
            menu.getSlot(id).setChanged();
        }
    }

    public static void bindUser(KeycardProgrammatorMainMenu menu, ServerPlayer player, int id, String owner){
        setUsername(menu, id, owner);
        setUUID(menu, player, id);
    }
    public static void unbindUser(KeycardProgrammatorMainMenu menu, int id){
        resetUsername(menu, id);
        resetUUID(menu, id);
    }

    public static void resetKeycard(KeycardProgrammatorMainMenu menu, ServerPlayer player, int id){
        unbindUser(menu, id);
        resetLevel(menu, id);
        removeNetwork(menu, id);
    }
    public static void copyKeycardProperties(ItemStack itemStackSource, ItemStack itemStackTarget) {
        CompoundTag sourceTag = itemStackSource.getOrCreateTag();
        CompoundTag targetTag = itemStackTarget.getOrCreateTag();

        targetTag.putInt("cLevel", sourceTag.getInt("cLevel"));
    }

    public static void addNetwork(KeycardProgrammatorMainMenu menu, Level level, int id, String networkName, Player player) {
        ItemStack stack = menu.getSlot(id).getItem();
        CompoundTag tag = stack.getOrCreateTag();
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) level);
        if (!NetworkSecurity.canChangeCardLevel(data.getNetwork(networkName).getUser(player.getUUID()))) return;
        if (data.isValidNetwork(networkName)) {
            tag.putString("cNetwork", networkName);
        }
    }
    public static void removeNetwork(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        CompoundTag tag = stack.getOrCreateTag();

        tag.putString("cNetwork", "none");
    }

    public static String getCardOwner(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        CompoundTag tag = stack.getOrCreateTag();
        if (!stack.isEmpty() && tag.contains("cOwner")) {
            return tag.getString("cOwner");
        }
        return "none";
    }
    public static int getColor(String string) {
        if (string.equals("none") || string.equals("false")) {
            return 0xFF2400;
        }
        if (string.equals("true")) {
            return 0x008000;
        }
        return 0xFFA500;
    }
    public static int getColorNumbers(int number) {
        if (number != 0) {
            return 0x42AAFF;
        }
        return 0xFF2400;
    }
    public static String getCardUUID(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("cUUID") && !tag.getString("cUUID").equals("none")){
                return "true";
            }
        }
        return "false";
    }
    public static String getCardNetwork(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("cNetwork")) {
                return tag.getString("cNetwork");
            }
        }
        return "none";
    }
    public static int getCardLevel(KeycardProgrammatorMainMenu menu, int id) {
        ItemStack stack = menu.getSlot(id).getItem();
        if (!stack.isEmpty()) {
            return  stack.getOrCreateTag().getInt("cLevel");
        }
        return 0;
    }
}