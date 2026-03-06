package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BarrierGateOffBE extends BlockEntity implements TickableBE {
    private int clevel;
    private boolean bstate;

    public BarrierGateOffBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.BARRIER_GATE_OFF.get(), pos, state);
    }

    public int checkDirection(){
        Direction dir = this.level.getBlockState(this.worldPosition).getValue(HorizontalDirectionalBlock.FACING);
        Direction dir2;
        BlockEntity oppositeBlockEntity;

        for (int i = 1; i < 7; i++) {
            if (this.level.getBlockState(positioner(dir, i)).getBlock() == LabBlocks.BARRIER_GATE_OFF.get()) {
                oppositeBlockEntity = this.level.getBlockEntity(positioner(dir, i));
                dir2 = this.level.getBlockState(positioner(dir, i)).getValue(HorizontalDirectionalBlock.FACING);
                if (oppositeBlockEntity instanceof BarrierGateOffBE barrierGateBE && dir2.getOpposite() == dir) {
                    return i;
                }
            }
        }
        return 0;
    }

    public BlockPos positioner(Direction dir, int i){
        if(dir == Direction.EAST) {
            return this.worldPosition.east(i);
        }
        if(dir == Direction.WEST) {
            return this.worldPosition.west(i);
        }
        if(dir == Direction.NORTH) {
            return this.worldPosition.north(i);
        }
        if(dir == Direction.SOUTH) {
            return this.worldPosition.south(i);
        }
        return this.worldPosition;
    }

    public void setData(BlockEntity be, boolean state){
        if (be instanceof BarrierGateBE block) {
            block.setBState(state);
            block.setChanged();
        }
    }

    public BlockState stateInverter(BlockState state){
        Direction current = state.getValue(HorizontalDirectionalBlock.FACING);
        Direction opposite = current.getOpposite();

        return state.setValue(HorizontalDirectionalBlock.FACING, opposite);
    }

    /** Access Level  */
    public void setClevel(int clevel){
        this.clevel = clevel;
        setChanged();
    }
    public int getCLevel() {
        return clevel;
    }

    /** Current State (On/Off) */
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
        tag.putInt("cLevel", clevel);
        tag.putBoolean("bState", bstate);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        clevel = tag.getInt("cLevel");
        bstate = tag.getBoolean("bState");
    }

    public void updateBarrier() {
        Direction dir = this.level.getBlockState(this.worldPosition).getValue(HorizontalDirectionalBlock.FACING);
        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);
        BlockEntity blockEntityOpposite = this.level.getBlockEntity(positioner(dir, checkDirection()));
        int current = checkDirection();

        if (blockEntity instanceof BarrierGateOffBE barrierGateoffBE) {
            Direction dir2 = this.level.getBlockState(positioner(dir, checkDirection())).getValue(HorizontalDirectionalBlock.FACING);
            if (blockEntityOpposite instanceof BarrierGateOffBE barrierGateOffOppositeBE && dir2.getOpposite() == dir) {
                if (barrierGateoffBE.bstate && barrierGateOffOppositeBE.bstate) {
                    this.level.setBlockAndUpdate(this.worldPosition, LabBlocks.BARRIER_GATE.get().withPropertiesOf(this.level.getBlockState(this.worldPosition)));
                    this.level.setBlockAndUpdate(positioner(dir, checkDirection()), LabBlocks.BARRIER_GATE.get().withPropertiesOf(this.level.getBlockState(positioner(dir, checkDirection()))));
                    BlockEntity blockEntityOppositeNew = this.level.getBlockEntity(positioner(dir, current));
                    BlockEntity blockEntityNew = this.level.getBlockEntity(this.worldPosition);
                    setData(blockEntityOppositeNew, true);
                    setData(blockEntityNew, true);
                }
                else {
                    setBState(false);
                }
            }
        }
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }
        updateBarrier();
    }
}