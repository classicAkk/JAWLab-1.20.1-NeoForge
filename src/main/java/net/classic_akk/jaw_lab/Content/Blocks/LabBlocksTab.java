package net.classic_akk.jaw_lab.Content.Blocks;
import net.classic_akk.jaw_lab.Lab;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
public class LabBlocksTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lab.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LAB_BLOCKS_TAB = CREATIVE_MODE_TABS.register("lab_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(LabBlocks.C_ZONE_BLOCK_UP.get()))
                    .title(Component.translatable("creativetab.lab_blocks_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_PAINTED.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_G.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_G_PAINTED.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_B.get());
                        pOutput.accept(LabBlocks.ARMORED_CONCRETE_B_PAINTED.get());

                        pOutput.accept(LabBlocks.Y_ZONE_BLOCK_UP.get());
                        pOutput.accept(LabBlocks.Y_ZONE_BLOCK_DOWN.get());

                        pOutput.accept(LabBlocks.C_ZONE_BLOCK_UP.get());
                        pOutput.accept(LabBlocks.C_ZONE_BLOCK_DOWN.get());

                        pOutput.accept(LabBlocks.G_ZONE_BLOCK_UP.get());
                        pOutput.accept(LabBlocks.G_ZONE_BLOCK_DOWN.get());

                        pOutput.accept(LabBlocks.R_ZONE_BLOCK_UP.get());
                        pOutput.accept(LabBlocks.R_ZONE_BLOCK_DOWN.get());

                        pOutput.accept(LabBlocks.REACTOR_ZONE_BLOCK_UP.get());
                        pOutput.accept(LabBlocks.REACTOR_ZONE_BLOCK_DOWN.get());

                        pOutput.accept(LabBlocks.O_ZONE_BLOCK_UP.get());
                        pOutput.accept(LabBlocks.O_ZONE_BLOCK_DOWN.get());

                        pOutput.accept(LabBlocks.GRATING.get());
                        pOutput.accept(LabBlocks.GLASS_RAILINGS.get());
                        pOutput.accept(LabBlocks.GLASS_RAILINGS_CORNER.get());

                        pOutput.accept(LabBlocks.ELEVATOR_INSIDE.get());
                        pOutput.accept(LabBlocks.ELEVATOR_OUTSIDE.get());

                        pOutput.accept(LabBlocks.SHEET.get());
                        pOutput.accept(LabBlocks.SHEET_GREEN.get());
                        pOutput.accept(LabBlocks.SHEET_RED.get());

                        pOutput.accept(LabBlocks.CROWBAR_BLOCK.get());
                        pOutput.accept(LabBlocks.FUSE_BLOCK.get());
                        pOutput.accept(LabBlocks.DUCT_TAPE_BLOCK.get());
                        pOutput.accept(LabBlocks.GREEN_STAMP_BLOCK.get());
                        pOutput.accept(LabBlocks.RED_STAMP_BLOCK.get());
                        pOutput.accept(LabBlocks.KEYCARD1_BLOCK.get());
                        pOutput.accept(LabBlocks.KEYCARD2_BLOCK.get());
                        pOutput.accept(LabBlocks.KEYCARD3_BLOCK.get());
                        pOutput.accept(LabBlocks.KEYCARD4_BLOCK.get());
                        pOutput.accept(LabBlocks.KEYCARD5_BLOCK.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
