package net.classic_akk.jaw_lab.Screen.CodeDoor;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors.CodeDoorBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeycardProgrammatorBE;
import net.classic_akk.jaw_lab.Screen.LabMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CodeDoorMenu extends AbstractContainerMenu {
    public static CodeDoorBE blockEntity;
    private ContainerLevelAccess access;
    private static Level level;

    public CodeDoorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), ContainerLevelAccess.NULL);
    }

    public CodeDoorMenu(int id, Inventory inv, BlockEntity entity, ContainerLevelAccess access) {
        super(LabMenuTypes.CODE_DOOR.get(), id);
        blockEntity = ((CodeDoorBE) entity);
        this.access = access;
        if (!blockEntity.getLevel().isClientSide()) {
            level = blockEntity.getLevel();
        }
    }

    public static Level getLevel() {
        return level;
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