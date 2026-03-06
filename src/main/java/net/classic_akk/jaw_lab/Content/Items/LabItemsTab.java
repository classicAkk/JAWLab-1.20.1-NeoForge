package net.classic_akk.jaw_lab.Content.Items;
import net.classic_akk.jaw_lab.Lab;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
public class LabItemsTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lab.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LAB_ITEMS_TAB = CREATIVE_MODE_TABS.register("lab_item_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(LabItems.KEYCARD4.get()))
                    .title(Component.translatable("creativetab.lab_items_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(LabItems.KEYCARD1.get());
                        pOutput.accept(LabItems.KEYCARD2.get());
                        pOutput.accept(LabItems.KEYCARD3.get());
                        pOutput.accept(LabItems.KEYCARD4.get());
                        pOutput.accept(LabItems.KEYCARD5.get());


                        pOutput.accept(LabItems.CROWBAR.get());
                        pOutput.accept(LabItems.FUSE.get());
                        pOutput.accept(LabItems.DUCT_TAPE.get());
                        pOutput.accept(LabBlocks.LED_LAMP.get());

                        pOutput.accept(LabItems.GREEN_STAMP.get());
                        pOutput.accept(LabItems.RED_STAMP.get());

                        pOutput.accept(LabItems.S_DELTA.get());
                        pOutput.accept(LabItems.S_IOTA.get());
                        pOutput.accept(LabItems.S_KAPPA.get());
                        pOutput.accept(LabItems.S_PSI.get());
                        pOutput.accept(LabItems.S_TAU.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
