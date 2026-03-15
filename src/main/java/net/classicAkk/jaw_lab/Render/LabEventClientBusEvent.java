package net.classicAkk.jaw_lab.Render;

import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Screen.CodeDoor.CodeDoorScreen;
import net.classicAkk.jaw_lab.Screen.DoorProgrammator.CodeDoor.DoorProgrammatorCodeScreen;
import net.classicAkk.jaw_lab.Screen.DoorProgrammator.KeyDoor.DoorProgrammatorKeyScreen;
import net.classicAkk.jaw_lab.Screen.KCPCopy.KeycardProgrammatorCopyScreen;
import net.classicAkk.jaw_lab.Screen.KCPMain.KeycardProgrammatorMainScreen;
import net.classicAkk.jaw_lab.Screen.KCPNetwork.KeycardProgrammatorNetworkScreen;
import net.classicAkk.jaw_lab.Screen.LabMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("ALL")
@Mod.EventBusSubscriber(modid = Lab.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LabEventClientBusEvent {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(LabBlocks.GRATING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(LabBlocks.GLASS_RAILINGS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(LabBlocks.GLASS_RAILINGS_CORNER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(LabBlocks.BARRIER_GATE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(LabBlocks.BARRIER_GATE_CONNECTOR.get(), RenderType.translucent());

        MenuScreens.register(LabMenuTypes.KCP_NETWORK.get(), KeycardProgrammatorNetworkScreen::new);
        MenuScreens.register(LabMenuTypes.KCP_COPY.get(), KeycardProgrammatorCopyScreen::new);
        MenuScreens.register(LabMenuTypes.KCP_MAIN.get(), KeycardProgrammatorMainScreen::new);
        MenuScreens.register(LabMenuTypes.CODE_DOOR.get(), CodeDoorScreen::new);
        MenuScreens.register(LabMenuTypes.DPR_CODE.get(), DoorProgrammatorCodeScreen::new);
        MenuScreens.register(LabMenuTypes.DPR_KEY.get(), DoorProgrammatorKeyScreen::new);
    }

    public static void doSetup(FMLClientSetupEvent event) {
    }
}