package net.classicAkk.jaw_lab;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Items.LabItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
public class LabCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lab.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LAB_BLOCKS_TAB = CREATIVE_MODE_TABS.register("lab_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(LabItems.KEYCARD4.get()))
                    .title(Component.translatable("creativetab.lab_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(LabBlocks.YELLOW_UP.get());
                        pOutput.accept(LabBlocks.CYAN_UP.get());
                        pOutput.accept(LabBlocks.GREEN_UP.get());
                        pOutput.accept(LabBlocks.RED_UP.get());
                        pOutput.accept(LabBlocks.RED_BLACK_UP.get());
                        pOutput.accept(LabBlocks.ORANGE_UP.get());

                        pOutput.accept(LabBlocks.ARMORED_CONCRETE.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_G.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_B.get());

                        pOutput.accept(LabBlocks.YELLOW_DOWN.get());
                        pOutput.accept(LabBlocks.CYAN_DOWN.get());
                        pOutput.accept(LabBlocks.GREEN_DOWN.get());
                        pOutput.accept(LabBlocks.RED_DOWN.get());
                        pOutput.accept(LabBlocks.RED_BLACK_DOWN.get());
                        pOutput.accept(LabBlocks.ORANGE_DOWN.get());

                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_PAINTED.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_G_PAINTED.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_B_PAINTED.get());

                        pOutput.accept(LabBlocks.KEYCARD_PROGRAMMATOR.get());
                        pOutput.accept(LabBlocks.KEY_DOOR.get());
                        pOutput.accept(LabBlocks.CODE_DOOR.get());
                        pOutput.accept(LabItems.DOOR_PROGRAMMATOR.get());
                        pOutput.accept(LabItems.KEYCARD1.get());
                        pOutput.accept(LabItems.KEYCARD2.get());
                        pOutput.accept(LabItems.KEYCARD3.get());
                        pOutput.accept(LabItems.KEYCARD4.get());
                        pOutput.accept(LabItems.KEYCARD5.get());

                        pOutput.accept(LabItems.CROWBAR.get());
                        pOutput.accept(LabItems.FUSE.get());
                        pOutput.accept(LabItems.DUCT_TAPE.get());
                        pOutput.accept(LabBlocks.LED_LAMP.get());

                        pOutput.accept(LabBlocks.GRATING.get());
                        pOutput.accept(LabBlocks.GLASS_RAILINGS.get());
                        pOutput.accept(LabBlocks.GLASS_RAILINGS_CORNER.get());

                        pOutput.accept(LabBlocks.ELEVATOR_INSIDE.get());
                        pOutput.accept(LabBlocks.ELEVATOR_OUTSIDE.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
