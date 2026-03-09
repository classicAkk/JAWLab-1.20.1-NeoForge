package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.KeyDoors.KeyDoorUp;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.classic_akk.jaw_lab.Content.Sound.LabSounds;
import net.classic_akk.jaw_lab.Screen.KCPMain.KeycardProgrammatorMainMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class CodeDoorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private String cPasscode;

    public CodeDoorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.CODE_DOOR_BE.get(), pos, state);
    }

    public void setPasscode(String cPasscode){
        this.cPasscode = cPasscode;
        setChanged();
    }

    public String getPasscode() {
        return cPasscode;
    }

    public void setData(BlockEntity be, String cPasscode){
        if (be instanceof CodeDoorErrorBE block) {
            block.setPasscode(cPasscode);
            block.setChanged();
        }
    }
 /** Cannot invoke "String.isEmpty()" because "pData" is null */
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
        if (this.level == null || this.level.isClientSide()) {
            return;
        }
        BlockState state = level.getBlockState(this.getBlockPos());
        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition);

        if (!KeyDoorUp.isOpen(state)) return;
        if (this.ticks++ % 20 == 0) {
            this.level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), LabSounds.KEYDOOR_TICK.get(), SoundSource.AMBIENT, 0.5f, 1f);
            timer++;

            if (timer == 4) {
                timer = 0;
                if (blockEntity instanceof CodeDoorBE keydoor) {
                    level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), LabSounds.KEYDOOR_CLOSE.get(), SoundSource.AMBIENT, 2f, 1f);
                    for (int i = 0; i < 20; i++) {
                        this.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 0.1, 0.1, 0.1);
                    }
                    this.level.setBlockAndUpdate(this.getBlockPos(), LabBlocks.CODE_DOOR_UP_ERROR.get().withPropertiesOf(state));
                    this.level.setBlockAndUpdate(this.getBlockPos().below(), LabBlocks.KEYDOOR_DOWN.get().withPropertiesOf(state).setValue(BlockStateProperties.OPEN, false));

                    BlockEntity newBlockEntity = this.level.getBlockEntity(this.worldPosition);
                    setData(newBlockEntity, keydoor.getPasscode());
                }
            }
        }
    }
}