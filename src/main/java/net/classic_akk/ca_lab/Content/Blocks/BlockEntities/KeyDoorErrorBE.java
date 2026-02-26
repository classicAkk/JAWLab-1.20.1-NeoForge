package net.classic_akk.ca_lab.Content.Blocks.BlockEntities;

import net.classic_akk.ca_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classic_akk.ca_lab.Content.Blocks.LabBlockEntities;
import net.classic_akk.ca_lab.Content.Blocks.LabBlocks;
import net.classic_akk.ca_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class KeyDoorErrorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private int clevel;

    public KeyDoorErrorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.KEY_DOOR_ERROR_BE.get(), pos, state);
    }

    public void setData(BlockEntity be, int level){
        if (be instanceof KeyDoorBE block) {
            block.setClevel(level);
            block.setChanged();
        }
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

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide())
            return;

        if (this.ticks++ % 20 == 0) {
            timer++;
            if (timer == 2) {
                timer = 0;
                BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);

                if (blockEntity instanceof KeyDoorErrorBE keydoor_error) {
                    BlockState state = level.getBlockState(this.getBlockPos());
                    level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), LabSounds.KEYDOOR_ERROR.get(), SoundSource.AMBIENT, 0.5f, 0f);
                    level.setBlockAndUpdate(this.getBlockPos(), LabBlocks.KEYDOOR_UP_CLOSED.get().withPropertiesOf(state));
                    for (int i = 0; i < 20; i++) {
                        this.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 0.1, 0.1, 0.1);
                    }

                    BlockEntity newBlockEntity = this.level.getBlockEntity(this.worldPosition);
                    if (newBlockEntity instanceof KeyDoorBE keydoor) {
                        setData(newBlockEntity, keydoor_error.getCLevel());
                    }
                }
            }
        }
    }
}