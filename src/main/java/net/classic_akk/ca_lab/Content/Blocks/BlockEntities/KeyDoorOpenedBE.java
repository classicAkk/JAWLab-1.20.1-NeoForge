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

public class KeyDoorOpenedBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private int clevel;

    public KeyDoorOpenedBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.KEY_DOOR_OPENED_BE.get(), pos, state);
    }

    public void setClevel(int clevel){
        this.clevel = clevel;
        setChanged();
    }

    public int getCLevel() {
        return clevel;
    }

    public void setData(BlockEntity be, int level){
        if (be instanceof KeyDoorErrorBE block) {

            block.setClevel(level);
            block.setChanged();
        }
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
        if (this.level == null || this.level.isClientSide()) {
            return;
        }
        BlockState state = level.getBlockState(this.getBlockPos());
        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);

        if (this.ticks++ % 20 == 0) {
            this.level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), LabSounds.KEYDOOR_TICK.get(), SoundSource.AMBIENT, 0.5f, 1f);
            timer++;

            if (timer == 4) {
                timer = 0;
                if (blockEntity instanceof KeyDoorOpenedBE keydoor_opened) {
                    this.level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), LabSounds.KEYDOOR_CLOSE.get(), SoundSource.AMBIENT, 2f, 1f);
                    for (int i = 0; i < 20; i++) {
                        this.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 0.1, 0.1, 0.1);
                    }
                    this.level.setBlockAndUpdate(this.getBlockPos(), LabBlocks.KEYDOOR_UP_ERROR.get().withPropertiesOf(state));
                    this.level.setBlockAndUpdate(this.getBlockPos().below(), LabBlocks.KEYDOOR_DOWN_CLOSED.get().withPropertiesOf(state));

                    BlockEntity newBlockEntity = this.level.getBlockEntity(this.worldPosition);
                    setData(newBlockEntity, keydoor_opened.getCLevel());
                }
            }
        }
    }
}