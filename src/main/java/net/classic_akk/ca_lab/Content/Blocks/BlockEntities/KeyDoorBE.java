package net.classic_akk.ca_lab.Content.Blocks.BlockEntities;

import net.classic_akk.ca_lab.Content.Blocks.LabBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class KeyDoorBE extends BlockEntity {
    private int ticks;
    private int timer;
    private int clevel;

    public KeyDoorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.KEY_DOOR_BE.get(), pos, state);
    }

    public void setClevel(int clevel){
        this.clevel = clevel;
        setChanged();
    }

    public int getCLevel() {
        return clevel;
    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putInt("cLevel", clevel);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        clevel = tag.getInt("cLevel");
    }
}