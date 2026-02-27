package net.classic_akk.ca_lab.Screen.ProcessingPackets;

import net.classic_akk.ca_lab.Content.Blocks.BlockEntities.Util.KeycardInteractions;
import net.classic_akk.ca_lab.Screen.KCPMain.KeycardProgrammatorMainMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProcessingPacket {

    private static int containerId;

    public ProcessingPacket(int containerId) {
        ProcessingPacket.containerId = containerId;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(containerId);
    }

    public static ProcessingPacket decode(FriendlyByteBuf buf) {
        return new ProcessingPacket(containerId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) {
                return;
            }

            if (player.containerMenu instanceof KeycardProgrammatorMainMenu menu) {
                KeycardInteractions.setUUID(menu, player, 36);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}