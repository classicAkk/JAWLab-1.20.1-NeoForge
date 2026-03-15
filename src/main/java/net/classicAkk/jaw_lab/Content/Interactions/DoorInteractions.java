package net.classicAkk.jaw_lab.Content.Interactions;

import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors.CodeDoorBE;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors.KeyDoorBE;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.KeyDoor;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Network.Network;
import net.classicAkk.jaw_lab.Content.Network.NetworkRole;
import net.classicAkk.jaw_lab.Content.Network.NetworkSecurity;
import net.classicAkk.jaw_lab.Content.Network.NetworkWorldData;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DoorInteractions {
    public static void setDoor(BlockEntity pBlockEntity, Level pLevel, Player pPlayer) {
        BlockPos pos = pBlockEntity.getBlockPos();
        BlockState state = pLevel.getBlockState(pos);
        int x = pos.getX(); int y = pos.getY(); int z = pos.getZ();

        if (pBlockEntity instanceof CodeDoorBE codeDoorBE) {
            pLevel.playSound(null, x, y, z, LabSounds.KEYDOOR_OPEN.get(), SoundSource.AMBIENT, 0.5f, 1f);
            pLevel.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.OPENED));
            pLevel.setBlockAndUpdate(pos.below(), LabBlocks.DOOR_BOTTOM.get().withPropertiesOf(state.setValue(KeyDoor.STATE, DoorState.OPENED)));
            pPlayer.displayClientMessage(Component.literal("Access Granted").withStyle(ChatFormatting.GREEN), true);
            if (pLevel instanceof ServerLevel server) server.sendParticles(ParticleTypes.SMOKE,
                    x+0.5, y, z+0.5, 20, 0.2, 0.4, 0.2,0.02);

            codeDoorBE.resetTick();
        }
    }
    public static void error(BlockEntity pBlockEntity, Level pLevel, Player pPlayer) {
        BlockPos pos = pBlockEntity.getBlockPos();
        BlockState state = pLevel.getBlockState(pos);
        int x = pos.getX(); int y = pos.getY(); int z = pos.getZ();
        pLevel.playSound(null, x, y, z, LabSounds.KEYDOOR_ERROR.get(), SoundSource.AMBIENT, 0.5f, 1f);
        pLevel.setBlockAndUpdate(pos, state.setValue(KeyDoor.STATE, DoorState.ERROR));
        pPlayer.displayClientMessage(Component.literal("Access Denied").withStyle(ChatFormatting.RED), true);

        if (pLevel.getBlockEntity(pos) instanceof CodeDoorBE codeDoorBENew) {
            codeDoorBENew.resetTick();
        }
    }
    public static void setCode(BlockEntity pBlockEntity, Level pLevel, String passcode, Player pPlayer) {
        BlockPos pos = pBlockEntity.getBlockPos();
        if (pLevel.getBlockEntity(pos) instanceof CodeDoorBE codeDoorBE) {
            pPlayer.displayClientMessage(Component.literal("Passcode successfully set").withStyle(ChatFormatting.GREEN), true);
            codeDoorBE.setPasscode(passcode);
        }
    }

    public static void switchAutoClose(BlockEntity pBlockEntity, Level pLevel) {
        BlockPos pos = pBlockEntity.getBlockPos();
        if (pLevel.getBlockEntity(pos) instanceof CodeDoorBE codeDoorBE) {
            codeDoorBE.setAutoClose(!codeDoorBE.getAutoClose());
        }
        if (pLevel.getBlockEntity(pos) instanceof KeyDoorBE keyDoorBE) {
            keyDoorBE.setAutoClose(!keyDoorBE.getAutoClose());
        }
    }
    public static void resetDoor(BlockEntity pBlockEntity, Level pLevel, String networkName, Player pPlayer) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) pLevel);
        if (!NetworkSecurity.canChangeDoorSettings(data.getNetwork(networkName).getUser(pPlayer.getUUID()))) return;
        BlockPos pos = pBlockEntity.getBlockPos();
        if (pLevel.getBlockEntity(pos) instanceof CodeDoorBE codeDoorBE) {
            codeDoorBE.setPasscode("");
            codeDoorBE.setAutoClose(true);
            System.out.print("set0");
        }
        if (pLevel.getBlockEntity(pos) instanceof KeyDoorBE keyDoorBE) {
            keyDoorBE.setClevel(0);
            keyDoorBE.setNetwork(null);
            keyDoorBE.setAutoClose(true);
        }
    }
    public static void setNetwork(BlockEntity pBlockEntity, Level pLevel, String networkName, String currentNetworkName, Player pPlayer) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) pLevel);
        if (data.getNetwork(networkName) == null) return;
        if (!NetworkSecurity.canChangeDoorSettings(data.getNetwork(networkName).getUser(pPlayer.getUUID()))) return;
        if (!currentNetworkName.isEmpty()) {
            NetworkRole role = data.getUserRole(currentNetworkName, pPlayer.getUUID());
            if (role != NetworkRole.FOUNDER && role != NetworkRole.ADMIN) return;
        }
        BlockPos pos = pBlockEntity.getBlockPos();
        if (pLevel.getBlockEntity(pos) instanceof KeyDoorBE keyDoorBE) {
            if (data.isValidNetwork(networkName)) keyDoorBE.setNetwork(data.getNetwork(networkName));
        }
    }
    public static boolean canSetNetwork(BlockEntity pBlockEntity, Level pLevel, String networkName, String currentNetworkName, Player pPlayer) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) pLevel);
        if (data.getNetwork(networkName) == null) return false;
        if (!NetworkSecurity.canChangeDoorSettings(data.getNetwork(networkName).getUser(pPlayer.getUUID()))) return false;
        if (!currentNetworkName.isEmpty()) {
            NetworkRole role = data.getUserRole(currentNetworkName, pPlayer.getUUID());
            if (role != NetworkRole.FOUNDER && role != NetworkRole.ADMIN) return false;
        }
        BlockPos pos = pBlockEntity.getBlockPos();
        if (pLevel.getBlockEntity(pos) instanceof KeyDoorBE keyDoorBE) {
            return data.isValidNetwork(networkName);
        }
        return false;
    }

    public static void incrementLevel(BlockEntity pBlockEntity, Level pLevel, String networkName, Player pPlayer) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) pLevel);
        if (!NetworkSecurity.canChangeDoorSettings(data.getNetwork(networkName).getUser(pPlayer.getUUID()))) return;
        if (pLevel.getBlockEntity(pBlockEntity.getBlockPos()) instanceof KeyDoorBE keyDoorBE) {
            keyDoorBE.setClevel(keyDoorBE.getCLevel()+1);
        }
    }
    public static void decrementLevel(BlockEntity pBlockEntity, Level pLevel, String networkName, Player pPlayer) {
        NetworkWorldData data = NetworkWorldData.get((ServerLevel) pLevel);
        if (!NetworkSecurity.canChangeDoorSettings(data.getNetwork(networkName).getUser(pPlayer.getUUID()))) return;
        if (pLevel.getBlockEntity(pBlockEntity.getBlockPos()) instanceof KeyDoorBE keyDoorBE) {
            if (keyDoorBE.getCLevel() <= 0) return;
            keyDoorBE.setClevel(keyDoorBE.getCLevel()-1);
        }
    }

    public static String getNetwork(BlockEntity pBlockEntity) {
        if (pBlockEntity instanceof CodeDoorBE codeDoorBE) {
            return codeDoorBE.getNetwork();
        }
        if (pBlockEntity instanceof KeyDoorBE keyDoorBE) {
            return keyDoorBE.getNetwork();
        }
        return null;
    }
    public static int getLevel(BlockEntity pBlockEntity) {
        if (pBlockEntity instanceof KeyDoorBE keyDoorBE) {
            return keyDoorBE.getCLevel();
        }
        return 0;
    }
}