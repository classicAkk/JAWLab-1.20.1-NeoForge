package net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors;

import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.CodeDoor;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Network.Network;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CodeDoorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private String network;
    private String cPasscode = "";
    private boolean autoClose = true;

    public CodeDoorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.CODE_DOOR_BE.get(), pos, state);
    }

    public void resetTick() {
        ticks = 0;
    }

    public void setNetwork(Network network){
        this.network = network.getName();
        setChanged();
        if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
    public String getNetwork() {
        return network;
    }

    public void setAutoClose(boolean autoClose){
        this.autoClose = autoClose;
        setChanged();
        if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
    public boolean getAutoClose() {
        return autoClose;
    }

    public void setPasscode(String cPasscode){
        this.cPasscode = cPasscode;
        setChanged();
        if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
    public String getPasscode() {
        return cPasscode;
    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putString("cPasscode", cPasscode);
        if (network != null) tag.putString("cNetwork", network);
        tag.putBoolean("cAutoClose", autoClose);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cPasscode = tag.getString("cPasscode");
        network = tag.getString("cNetwork");
        autoClose = tag.getBoolean("cAutoClose");
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;
        BlockPos pos = getBlockPos();
        BlockState state = level.getBlockState(getBlockPos());
        int x = worldPosition.getX(); int y = worldPosition.getY(); int z = worldPosition.getZ();

        if (state.getValue(CodeDoor.STATE) == DoorState.OPENED) {
            if (ticks++ % 20 == 0) {
                if (autoClose) {
                    level.playSound(null, x, y, z, LabSounds.KEYDOOR_TICK.get(), SoundSource.AMBIENT, 0.5f, 1f);
                    timer++;
                    if (timer == 4) {
                        timer = 0;
                        level.playSound(null, x, y, z, LabSounds.KEYDOOR_CLOSE.get(), SoundSource.AMBIENT, 0.5f, 1f);
                        if (level instanceof ServerLevel server) server.sendParticles(ParticleTypes.SMOKE,
                                x + 0.5, y, z + 0.5, 20, 0.2, 0.4, 0.2, 0.02);

                        level.setBlockAndUpdate(pos, state.setValue(CodeDoor.STATE, DoorState.ERROR));
                        level.setBlockAndUpdate(pos.below(), LabBlocks.DOOR_BOTTOM.get().withPropertiesOf(state.setValue(CodeDoor.STATE, DoorState.CLOSED)));
                        resetTick();
                    }
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
                }
            }
        }
    }
}