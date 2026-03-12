package net.classicAkk.jaw_lab.Content.Interactions;

import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors.CodeDoorBE;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.KeyDoor;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CodeDoorInteractions {
    public static void setDoor(BlockEntity pBlockEntity, Level pLevel, Player pPlayer) {
        BlockPos pos = pBlockEntity.getBlockPos();
        BlockState state = pLevel.getBlockState(pos);
        int x = pos.getX(); int y = pos.getY(); int z = pos.getZ();

        if (pBlockEntity instanceof CodeDoorBE codeDoorBE) {
            pLevel.playSound(null, x, y, z, LabSounds.KEYDOOR_OPEN.get(), SoundSource.AMBIENT, 2f, 1f);
            pLevel.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.OPENED));
            pLevel.setBlockAndUpdate(pos.below(), LabBlocks.DOOR_BOTTOM.get().withPropertiesOf(state.setValue(KeyDoor.STATE, DoorState.OPENED)));
            pPlayer.displayClientMessage(Component.literal("Access Granted").withStyle(ChatFormatting.GREEN), true);
            for (int i = 0; i < 20; i++) {
                pLevel.addAlwaysVisibleParticle(ParticleTypes.SMOKE, x, y, z, 0.1, 0.1, 0.1);
            }
            codeDoorBE.resetTick();
        }
    }

    public static void error(BlockEntity pBlockEntity, Level pLevel, Player pPlayer) {
        BlockPos pos = pBlockEntity.getBlockPos();
        BlockState state = pLevel.getBlockState(pos);
        int x = pos.getX(); int y = pos.getY(); int z = pos.getZ();

        if (pBlockEntity instanceof CodeDoorBE codeDoorBE) {
            pLevel.playSound(null, x, y, z, LabSounds.KEYDOOR_ERROR.get(), SoundSource.AMBIENT, 2f, 1f);
            pLevel.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.ERROR));
            pPlayer.displayClientMessage(Component.literal("Access Denied").withStyle(ChatFormatting.RED), true);

            codeDoorBE.resetTick();
        }
    }

    public static void setCode(BlockEntity pBlockEntity, String passcode, Player pPlayer) {
        if (pBlockEntity instanceof CodeDoorBE codeDoorBE) {
            pPlayer.displayClientMessage(Component.literal("Passcode successfully set").withStyle(ChatFormatting.GREEN), true);
            codeDoorBE.setPasscode(passcode);
        }
    }
}