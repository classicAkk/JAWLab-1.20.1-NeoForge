package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Doors.CodeDoor;
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

public class CodeDoorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private String cPasscode = "";

    public CodeDoorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.CODE_DOOR_BE.get(), pos, state);
    }

    public void resetTick() {
        ticks = 0;
    }

    public void setPasscode(String cPasscode){
        this.cPasscode = cPasscode;
        setChanged();
    }

    public String getPasscode() {
        return cPasscode;
    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putString("cPasscode", cPasscode);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cPasscode = tag.getString("cPasscode");
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;
        BlockPos pos = getBlockPos();
        BlockState state = level.getBlockState(getBlockPos());
        int x = worldPosition.getX(); int y = worldPosition.getY(); int z = worldPosition.getZ();

        if (state.getValue(CodeDoor.STATE) == DoorState.OPENED) {
            if (ticks++ % 20 == 0) {
                level.playSound(null, x, y, z, LabSounds.KEYDOOR_TICK.get(), SoundSource.AMBIENT, 0.5f, 1f);
                timer++;
                if (timer == 4) {
                    timer = 0;
                    level.playSound(null, x, y, z, LabSounds.KEYDOOR_CLOSE.get(), SoundSource.AMBIENT, 2f, 1f);
                    for (int i = 0; i < 20; i++) level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, x, y, z, 0.1, 0.1, 0.1);

                    level.setBlockAndUpdate(pos, state.setValue(CodeDoor.STATE, DoorState.ERROR));
                    level.setBlockAndUpdate(pos.below(), LabBlocks.KEYDOOR_DOWN.get().withPropertiesOf(state.setValue(CodeDoor.STATE, DoorState.CLOSED)));
                    resetTick();
                }
            }
        }
        if (state.getValue(CodeDoor.STATE) == DoorState.ERROR) {
            if (ticks++ % 20 == 0) {
                timer++;
                if (timer == 2) {
                    timer = 0;
                    level.playSound(null, x, y, z, LabSounds.KEYDOOR_ERROR.get(), SoundSource.AMBIENT, 0.5f, 0f);
                    level.setBlockAndUpdate(pos, state.setValue(CodeDoor.STATE, DoorState.CLOSED));
                    for (int i = 0; i < 20; i++) level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, x, y, z, 0.1, 0.1, 0.1);
                }
            }
        }
    }
}