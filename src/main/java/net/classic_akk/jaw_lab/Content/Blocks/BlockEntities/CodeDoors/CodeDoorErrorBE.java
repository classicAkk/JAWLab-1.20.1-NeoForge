package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
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

public class CodeDoorErrorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private String cPasscode;

    public CodeDoorErrorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.CODE_DOOR_ERROR_BE.get(), pos, state);
    }

    public void setData(BlockEntity be, String cPasscode){
        if (be instanceof CodeDoorBE block) {
            block.setPasscode(cPasscode);
            block.setChanged();
        }
    }

    public void setPasscode(String cPasscode){
        this.cPasscode = cPasscode;
        setChanged();
    }

    public String getCLevel() {
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
        if (this.level == null || this.level.isClientSide())
            return;

        if (this.ticks++ % 20 == 0) {
            timer++;
            if (timer == 2) {
                timer = 0;
                BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);

                if (blockEntity instanceof CodeDoorErrorBE keydoor_error) {
                    BlockState state = level.getBlockState(this.getBlockPos());
                    level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), LabSounds.KEYDOOR_ERROR.get(), SoundSource.AMBIENT, 0.5f, 0f);
                    level.setBlockAndUpdate(this.getBlockPos(), LabBlocks.CODE_DOOR_UP.get().withPropertiesOf(state).setValue(BlockStateProperties.OPEN, false));
                    for (int i = 0; i < 20; i++) {
                        this.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 0.1, 0.1, 0.1);
                    }

                    BlockEntity newBlockEntity = this.level.getBlockEntity(this.worldPosition);
                    if (newBlockEntity instanceof CodeDoorBE keydoor) {
                        setData(newBlockEntity, keydoor_error.getCLevel());
                    }
                }
            }
        }
    }
}