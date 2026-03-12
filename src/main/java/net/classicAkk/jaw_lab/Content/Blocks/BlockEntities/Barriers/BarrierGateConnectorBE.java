package net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Barriers;

import net.classicAkk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BarrierGateConnectorBE extends BlockEntity {
    private int ticks;
    private int timer;
    private boolean bstate;

    public BarrierGateConnectorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.BARRIER_GATE_BE.get(), pos, state);
    }

    public void setBState(boolean bstate){
        this.bstate = bstate;
        setChanged();
    }

    public boolean getBState() {
        return bstate;
    }


    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putBoolean("bState", bstate);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        bstate = tag.getBoolean("bState");
    }
}