package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeyDoors;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Doors.KeyDoor;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.classic_akk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class KeyDoorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private int clevel;

    public KeyDoorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.KEY_DOOR_BE.get(), pos, state);
    }

    public void resetTick() {
        ticks = 0;
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
        if (level == null || level.isClientSide()) return;
        BlockPos pos = getBlockPos();
        BlockState state = level.getBlockState(pos);
        int x = worldPosition.getX(); int y = worldPosition.getY(); int z = worldPosition.getZ();

        if (state.getValue(KeyDoor.STATE) == DoorState.OPENED) {
            if (ticks++ % 20 == 0) {
                level.playSound(null, x, y, z, LabSounds.KEYDOOR_TICK.get(), SoundSource.BLOCKS, 0.5f, 1f);
                timer++;
                if (timer == 4) {
                    timer = 0;
                    level.playSound(null, x, y, z, LabSounds.KEYDOOR_CLOSE.get(), SoundSource.BLOCKS, 2f, 1f);
                    for (int i = 0; i < 20; i++) level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, x, y, z, 0.1, 0.1, 0.1);

                    level.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.ERROR));
                    level.setBlockAndUpdate(pos.below(), LabBlocks.KEYDOOR_DOWN.get().withPropertiesOf(state.setValue(KeyDoor.STATE, DoorState.CLOSED)));
                    resetTick();
                }
            }
        }
        if (state.getValue(KeyDoor.STATE) == DoorState.ERROR) {
            if (ticks++ % 20 == 0) {
                timer++;
                if (timer == 2) {
                    timer = 0;
                    level.playSound(null, x, y, z, LabSounds.KEYDOOR_ERROR.get(), SoundSource.BLOCKS, 0.5f, 0f);
                    level.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.CLOSED));
                    for (int i = 0; i < 20; i++) {
                        level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, x, y, z, 0.1, 0.1, 0.1);
                    }
                }
            }
        }
    }
}