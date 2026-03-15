package net.classicAkk.jaw_lab.Screen.ProcessingPackets;

import net.classicAkk.jaw_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenCopyMenuPacket {

    private final BlockPos pos;

    public OpenCopyMenuPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(OpenCopyMenuPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static OpenCopyMenuPacket decode(FriendlyByteBuf buf) {
        return new OpenCopyMenuPacket(buf.readBlockPos());
    }

    public static void handle(OpenCopyMenuPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            BlockEntity entity = player.level().getBlockEntity(msg.pos);
            if (entity == null) return;

            ContainerData data = new SimpleContainerData(2);

            NetworkHooks.openScreen(player, new MenuProvider() {

                @Override
                public Component getDisplayName() {
                    return Component.translatable("block.lab.ui.kcp_copy");
                }

                @Override
                public AbstractContainerMenu createMenu(int pContainerId, Inventory inv, Player p) {
                    return new KeycardProgrammatorCopyMenu(pContainerId, inv, entity, data);
                }
            }, msg.pos);

        });
        ctx.get().setPacketHandled(true);
    }
}