package net.classic_akk.jaw_lab;
import com.mojang.logging.LogUtils;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocksTab;
import net.classic_akk.jaw_lab.Content.Items.LabItems;
import net.classic_akk.jaw_lab.Content.Items.LabItemsTab;
import net.classic_akk.jaw_lab.Content.Sound.LabSounds;
import net.classic_akk.jaw_lab.Screen.LabMenuTypes;
import net.classic_akk.jaw_lab.Util.LabPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Lab.MOD_ID)
public class Lab {
    public static final String MOD_ID = "lab";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Lab() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LabBlockEntities.register(modEventBus);
        LabItemsTab.register(modEventBus);
        LabBlocksTab.register(modEventBus);
        LabBlocks.register(modEventBus);
        LabSounds.register(modEventBus);
        LabItems.register(modEventBus);
        LabMenuTypes.register(modEventBus);
        LabPackets.register();


        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation resourceLocation(String loc){
        return new ResourceLocation(MOD_ID, loc);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}