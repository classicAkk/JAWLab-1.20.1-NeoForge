package net.classicAkk.jaw_lab.Screen.DoorProgrammator.KeyDoor;

import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors.KeyDoorBE;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.KeycardProgrammatorBE;
import net.classicAkk.jaw_lab.Content.Interactions.DoorInteractions;
import net.classicAkk.jaw_lab.Screen.LabMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DoorProgrammatorKeyMenu extends AbstractContainerMenu {
    public static KeyDoorBE blockEntity;
    private static Player player;
    private static Level level;
    private static Level serverLevel;

    public DoorProgrammatorKeyMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), ContainerLevelAccess.NULL);
    }

    public DoorProgrammatorKeyMenu(int id, Inventory inv, BlockEntity entity, ContainerLevelAccess access) {
        super(LabMenuTypes.DPR_KEY.get(), id);

        blockEntity = ((KeyDoorBE) entity);
        player = inv.player;
        level = inv.player.level();
        if (!blockEntity.getLevel().isClientSide()) {
            serverLevel = blockEntity.getLevel();
            blockEntity = ((KeyDoorBE) entity);
        }
    }

    public static Player getPlayer() {
        return player;
    }
    public static Level getLevel() {
        if (!serverLevel.isClientSide()) {
            return serverLevel;
        }
        return null;
    }
    public static BlockEntity getBE() {
        return blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}