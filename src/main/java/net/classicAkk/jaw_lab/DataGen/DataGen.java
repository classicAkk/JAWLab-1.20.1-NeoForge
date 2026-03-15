package net.classicAkk.jaw_lab.DataGen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DataGen {
    @Mod.EventBusSubscriber(modid = "lab", bus = Mod.EventBusSubscriber.Bus.MOD)
    public class LabDataGenerators {

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            if (event.includeServer()) {
                //generator.addProvider(true, new LabLootTables(output));
                generator.addProvider(true, new LabBlockTags(output, event.getLookupProvider(), existingFileHelper));
            }

            if (event.includeClient()) {
                generator.addProvider(true, new LabBlockStateProvider(output, existingFileHelper));
                generator.addProvider(true, new LabItemModelProvider(output, existingFileHelper));
            }
        }
    }
}
