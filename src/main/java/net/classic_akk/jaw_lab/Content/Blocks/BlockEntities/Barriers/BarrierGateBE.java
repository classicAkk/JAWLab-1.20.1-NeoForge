package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Barriers;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BarrierGateBE extends BlockEntity implements TickableBE {
    private BlockPos connectedPos;
    private int clevel;
    private boolean bstate;

    public BarrierGateBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.BARRIER_GATE_BE.get(), pos, state);
    }

    public int checkDirection(){
        assert this.level != null;
        Direction dir = this.level.getBlockState(this.worldPosition).getValue(HorizontalDirectionalBlock.FACING);
        Direction dir2;

        for (int i = 1; i < 7; i++) {
            if (this.level.getBlockState(positioner(dir, i)).getBlock() == LabBlocks.BARRIER_GATE.get()){
                BlockEntity oppositeBlockEntity = this.level.getBlockEntity(positioner(dir, i));
                dir2 = this.level.getBlockState(positioner(dir, i)).getValue(HorizontalDirectionalBlock.FACING);
                if (oppositeBlockEntity instanceof BarrierGateBE && dir2.getOpposite() == dir) {
                    return i;
                }
            }
        }
        return 0;
    }
    public int checkDirectionAbove(){
        assert this.level != null;
        if (this.level.getBlockState(this.worldPosition.above()).getBlock() == LabBlocks.BARRIER_GATE_OFF.get()
                || this.level.getBlockState(this.worldPosition.above()).getBlock() == LabBlocks.BARRIER_GATE.get()) {
            Direction dir = this.level.getBlockState(this.worldPosition.above()).getValue(HorizontalDirectionalBlock.FACING);
            Direction dir2;

            for (int i = 1; i < 7; i++) {
                if (this.level.getBlockState(positioner(dir, i)).getBlock() == LabBlocks.BARRIER_GATE.get()) {
                    BlockEntity oppositeBlockEntity = this.level.getBlockEntity(positioner(dir, i));
                    dir2 = this.level.getBlockState(positioner(dir, i)).getValue(HorizontalDirectionalBlock.FACING);
                    if (oppositeBlockEntity instanceof BarrierGateBE && dir2.getOpposite() == dir) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    public BlockPos positioner(Direction dir, int i){
        return worldPosition.relative(dir, i);
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


    /** Connected Block (opposite gate)*/
    public void setConnect(BlockPos connectedPos){
        this.connectedPos = connectedPos;
        setChanged();
    }

    public BlockPos getConnect() {
        return connectedPos;
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

    public void setData(BlockEntity be, boolean state){
        if (be instanceof BarrierGateBE block) {
            block.setBState(state);
            block.setChanged();
        }
    }
    public void setDataOff(BlockEntity be, boolean state){
        if (be instanceof BarrierGateOffBE block) {
            block.setBState(state);
            block.setChanged();
        }
    }

    /** !WARNING! There is no BlockEntity data check, possibly to crash if will be used without check */
    public void setData(BarrierGateBE block, BlockPos pos) {
        block.setConnect(pos);
        block.setChanged();
    }

    public void updateBarrier() {
        int current = checkDirection();
        int currentUp = checkDirectionAbove();
        Direction dir = this.level.getBlockState(this.worldPosition).getValue(HorizontalDirectionalBlock.FACING);
        Direction dirOpposite = this.level.getBlockState(positioner(dir, current)).getValue(HorizontalDirectionalBlock.FACING);
        Direction dirUp = null;
        Direction dirUpOpposite = null;

        if ((this.level.getBlockState(this.worldPosition.above()).getBlock() == LabBlocks.BARRIER_GATE_OFF.get()
                && this.level.getBlockState(positioner(dir, current).above()).getBlock() == LabBlocks.BARRIER_GATE_OFF.get())) {
            dirUp = this.level.getBlockState(this.worldPosition.above()).getValue(HorizontalDirectionalBlock.FACING);
            dirUpOpposite = this.level.getBlockState(positioner(dir, currentUp)).getValue(HorizontalDirectionalBlock.FACING);
        }

        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);
        BlockEntity blockEntityOpposite = this.level.getBlockEntity(positioner(dir, current));

        if ((blockEntity instanceof BarrierGateBE barrierGateBE && blockEntityOpposite instanceof BarrierGateBE barrierGateOppositeBE) && dirOpposite.getOpposite() == dir) {
            if (barrierGateBE.bstate && barrierGateOppositeBE.bstate) {
                if ((this.level.getBlockState(this.worldPosition.above()).getBlock() == LabBlocks.BARRIER_GATE_OFF.get()
                        && this.level.getBlockState(positioner(dir, current).above()).getBlock() == LabBlocks.BARRIER_GATE_OFF.get())
                        && dirUpOpposite.getOpposite() == dirUp) {
                    BlockEntity blockEntityOppositeUp = this.level.getBlockEntity(positioner(dirUp, current).above());
                    BlockEntity blockEntityUp = this.level.getBlockEntity(this.worldPosition.above());
                    setDataOff(blockEntityOppositeUp, true);
                    setDataOff(blockEntityUp, true);
                }
                for (int i = 1; i < current; i++) {
                    this.level.setBlockAndUpdate(positioner(this.level.getBlockState(this.worldPosition).getValue(HorizontalDirectionalBlock.FACING), i), LabBlocks.BARRIER_GATE_CONNECTOR.get().withPropertiesOf(level.getBlockState(this.getBlockPos())));
                }
            }
            else {
                for (int i = 1; i < current; i++) {
                    if (this.level.getBlockState(positioner(dir, i)).getBlock() == LabBlocks.BARRIER_GATE_CONNECTOR.get()) {
                        this.level.setBlockAndUpdate(positioner(dir, i), Blocks.AIR.defaultBlockState());
                    }
                }
                this.level.setBlockAndUpdate(this.worldPosition, LabBlocks.BARRIER_GATE_OFF.get().withPropertiesOf(this.level.getBlockState(this.worldPosition)));
                this.level.setBlockAndUpdate(positioner(dir, current), LabBlocks.BARRIER_GATE_OFF.get().withPropertiesOf(this.level.getBlockState(positioner(dir, current))));
            }
        }
        else {
            this.level.setBlockAndUpdate(this.worldPosition, LabBlocks.BARRIER_GATE_OFF.get().withPropertiesOf(this.level.getBlockState(this.worldPosition)));
        }
    }

    @Override
    public void tick() {
        int current = checkDirection();
        if (this.level == null || this.level.isClientSide()) {
            return;
        }
        Direction dir = this.level.getBlockState(this.worldPosition).getValue(HorizontalDirectionalBlock.FACING);
        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);
        BlockEntity blockEntityOpposite = this.level.getBlockEntity(positioner(dir, current));

        if (blockEntity instanceof BarrierGateBE) {
            if (current != 0) {
                if (blockEntityOpposite instanceof BarrierGateBE barrierGateOppositeBE) {
                    if (barrierGateOppositeBE.getConnect() != this.worldPosition) {
                        setData(barrierGateOppositeBE, positioner(dir, current));
                    }
                }
            }
            updateBarrier();
        }
    }
}