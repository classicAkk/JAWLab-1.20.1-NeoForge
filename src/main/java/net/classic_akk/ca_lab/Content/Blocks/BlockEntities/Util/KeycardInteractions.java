package net.classic_akk.ca_lab.Content.Blocks.BlockEntities.Util;

import net.classic_akk.ca_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.classic_akk.ca_lab.Screen.KCPMain.KeycardProgrammatorMainMenu;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class KeycardInteractions {
    public static void increaseLevel(ItemStack itemStack){
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.contains("cLevel")) {
            tag.putInt("cLevel", 0);
        }
        tag.putInt("cLevel", tag.getInt("cLevel")+1);
    }
    public static void decreaseLevel(ItemStack itemStack){
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.contains("cLevel")) {
            tag.putInt("cLevel", 0);
        }
        tag.putInt("cLevel", tag.getInt("cLevel")-1);
    }
    public static void setLevel(ItemStack itemStack, int pLevel) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("cLevel", pLevel);
    }
    public static void resetLevel(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("cLevel", 0);
    }

    public static void setUsername(ItemStack itemStack, Player player){
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putString("cOwner", player.getName().toString());

        System.out.print(tag.getString("cOwner") + "---" + player.getName() + "\n");
    }

    public static void setUUID(KeycardProgrammatorMainMenu menu, ServerPlayer player, int id) {
        ItemStack stack = menu.getSlot(id).getItem();

        if (!stack.isEmpty()) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString("cUUID", player.getStringUUID());
            menu.getSlot(id).setChanged();

            System.out.print("ply:" + player + "\n");
            System.out.print("id: " + menu.getSlot(id) + " --- " + player.getName() + "\n");
            System.out.print("uuid: " + player.getStringUUID() + "\n");
        }
    }

    public static void resetUsername(ItemStack itemStack){
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putString("cOwner", "none");
    }
    public static void resetUUID(ItemStack itemStack){
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putString("cUUID", "none");
    }

    public static void bindUser(ItemStack itemStack, Player player){
        setUsername(itemStack, player);
        //setUUID(itemStack, player);
    }
    public static void unbindUser(ItemStack itemStack){
        resetUsername(itemStack);
        resetUUID(itemStack);
    }

    public static void resetKeycard(ItemStack itemstack){
        unbindUser(itemstack);
        resetLevel(itemstack);
    }
    public static void copyKeycardProperties(ItemStack itemStackSource, ItemStack itemStackTarget) {
        CompoundTag sourceTag = itemStackSource.getOrCreateTag();
        CompoundTag targetTag = itemStackTarget.getOrCreateTag();

        targetTag.putInt("cLevel", sourceTag.getInt("cLevel"));
    }
}
