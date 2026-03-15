package net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors;

import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.KeyDoor;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Network.Network;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class KeyDoorBE extends BlockEntity implements TickableBE {
    private int ticks;
    private int timer;
    private int clevel;
    private String network;
    private boolean autoClose = true;

    public KeyDoorBE(BlockPos pos, BlockState state) {
        super(LabBlockEntities.KEY_DOOR_BE.get(), pos, state);
    }
    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
        setChanged();
        if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
    public boolean getAutoClose() {
        return autoClose;
    }

    public void resetTick() {
        ticks = 0;
    }

    public void setClevel(int clevel){
        this.clevel = clevel;
        setChanged();
        if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
    public int getCLevel() {
        return clevel;
    }

    public void setNetwork(Network network){
        this.network = network.getName();
        setChanged();
        if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
    public String getNetwork() {
        return network;
    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putInt("cLevel", clevel);
        tag.putBoolean("cAutoClose", autoClose);
        if (network != null) tag.putString("cNetwork", network);
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        clevel = tag.getInt("cLevel");
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
        BlockState state = level.getBlockState(pos);
        int x = worldPosition.getX(); int y = worldPosition.getY(); int z = worldPosition.getZ();

        if (state.getValue(KeyDoor.STATE) == DoorState.OPENED) {
            if (ticks++ % 20 == 0) {
                if (autoClose) {
                    level.playSound(null, x, y, z, LabSounds.KEYDOOR_TICK.get(), SoundSource.BLOCKS, 0.5f, 1f);
                    timer++;
                    if (timer == 4) {
                        timer = 0;
                        level.playSound(null, x, y, z, LabSounds.KEYDOOR_CLOSE.get(), SoundSource.BLOCKS, 0.5f, 1.0f);
                        if (level instanceof ServerLevel server) server.sendParticles(ParticleTypes.SMOKE,
                                x + 0.5, y, z + 0.5, 20, 0.2, 0.4, 0.2, 0.02);

                        level.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.ERROR));
                        level.setBlockAndUpdate(pos.below(), LabBlocks.DOOR_BOTTOM.get().withPropertiesOf(state.setValue(KeyDoor.STATE, DoorState.CLOSED)));
                        resetTick();
                    }
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
                }
            }
        }
    }
}