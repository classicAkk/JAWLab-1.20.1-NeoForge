package net.classic_akk.jaw_lab.Content.Blocks;

import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Decorations.DirectionalBlock;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Decorations.GlassRailings;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Decorations.GlassRailingsCorner;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Decorations.Grating;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.ElectricPanel.*;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.ElevatorButtons.InsideElevatorButton;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.ElevatorButtons.OutsideElevatorButton;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Barrier.BarrierGate;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Barrier.BarrierGateConnector;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Barrier.BarrierGateOff;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.KeyCards.*;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.KeyDoors.*;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.KeycardProgrammator;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.LED_Lamp;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Pickable.WireCuttersBlock;
import net.classic_akk.jaw_lab.Lab;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Pickable.CrowbarBlock;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Pickable.DuctTapeBlock;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Pickable.FuseBlock;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Sheets.EmptySheetBlock;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Sheets.SheetBlock;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Stamps.GreenStamp;
import net.classic_akk.jaw_lab.Content.Blocks.Blocks.Stamps.RedStamp;
import net.classic_akk.jaw_lab.Content.Items.LabItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class LabBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Lab.MOD_ID);

    //InteractableBlocks
    public static final RegistryObject<Block> KEYCARD_PROGRAMMATOR = registerBlock("keycard_programmator",
            () -> new KeycardProgrammator(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    //ArmoredConcrete
    public static final RegistryObject<Block> ARMORED_CONCRETE = registerBlock("armored_concrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ARMORED_CONCRETE_PAINTED = registerBlock("armored_concrete_painted",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ARMORED_CONCRETE_G = registerBlock("armored_concrete_g",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ARMORED_CONCRETE_G_PAINTED = registerBlock("armored_concrete_g_painted",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ARMORED_CONCRETE_B = registerBlock("armored_concrete_b",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ARMORED_CONCRETE_B_PAINTED = registerBlock("armored_concrete_b_painted",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    //ZonesBlocks
    public static final RegistryObject<Block> Y_ZONE_BLOCK_UP = registerBlock("y_zone_block_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> Y_ZONE_BLOCK_DOWN = registerBlock("y_zone_block_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));


    public static final RegistryObject<Block> C_ZONE_BLOCK_UP = registerBlock("c_zone_block_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> C_ZONE_BLOCK_DOWN = registerBlock("c_zone_block_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));


    public static final RegistryObject<Block> G_ZONE_BLOCK_UP = registerBlock("g_zone_block_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> G_ZONE_BLOCK_DOWN = registerBlock("g_zone_block_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> REACTOR_ZONE_BLOCK_UP = registerBlock("reactor_zone_block_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> REACTOR_ZONE_BLOCK_DOWN = registerBlock("reactor_zone_block_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> R_ZONE_BLOCK_UP = registerBlock("r_zone_block_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> R_ZONE_BLOCK_DOWN = registerBlock("r_zone_block_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> O_ZONE_BLOCK_UP = registerBlock("o_zone_block_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> O_ZONE_BLOCK_DOWN = registerBlock("o_zone_block_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));




    //KeyDoors
    public static final RegistryObject<Block> KEYDOOR_DOWN_CLOSED = registerBlock("keydoor_down_closed",
            () -> new KeyDoorDownClosed(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> KEYDOOR_DOWN_OPENED = registerBlock("keydoor_down_opened",
            () -> new KeyDoorDownOpened(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    public static final RegistryObject<Block> KEYDOOR_UP_CLOSED = registerBlock("keydoor_up_closed",
            () -> new KeyDoorUpClosed(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> KEYDOOR_UP_OPENED = registerBlock("keydoor_up_opened",
            () -> new KeyDoorUpOpened(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    public static final RegistryObject<Block> KEYDOOR_UP_ERROR = registerBlock("keydoor_up_error",
            () -> new KeyDoorUpError(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    //decorations
    public static final RegistryObject<Block> GLASS_RAILINGS = registerBlock("glass_railings",
            () -> new GlassRailings(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.GLASS)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> GLASS_RAILINGS_CORNER = registerBlock("glass_railings_corner",
            () -> new GlassRailingsCorner(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.GLASS)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> GRATING = registerBlock("grating",
            () -> new Grating(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> CABLE = registerBlock("cable",
            () -> new DirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).sound(SoundType.COPPER),
                    BlockSetType.IRON, 20, true));


    public static final RegistryObject<Block> ELEVATOR_INSIDE = registerBlock("elevator_inside",
            () -> new InsideElevatorButton(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK),
                    BlockSetType.IRON, 200, true));
    public static final RegistryObject<Block> ELEVATOR_OUTSIDE = registerBlock("elevator_outside",
            () -> new OutsideElevatorButton(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK),
                    BlockSetType.IRON, 200, true));


    //electric
    public static final RegistryObject<Block> ELECTRICAL_PANEL_FINE_CLOSED = registerBlock("electrical_panel_fine_closed",
            () -> new Grating(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_FINE_OPENED = registerBlock("electrical_panel_fine_opened",
            () -> new Grating(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_BROKEN_CLOSED = registerBlock("electrical_panel_broken_closed",
            () -> new ElectricPanelBrokenClosed(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_BROKEN_OPENED = registerBlock("electrical_panel_broken_opened",
            () -> new ElectricPanelBrokenOpened(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_BROKEN_FUSE0 = registerBlock("electrical_panel_broken_fuse0",
            () -> new Grating(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_BROKEN_FUSE1 = registerBlock("electrical_panel_broken_fuse1",
            () -> new Grating(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_BROKEN_FUSE2 = registerBlock("electrical_panel_broken_fuse2",
            () -> new Grating(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    public static final RegistryObject<Block> ELECTRICAL_PANEL_FIXED_CABLE = registerBlock("electrical_panel_fixed_cable",
            () -> new ElectricPanelFixedCable(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_FIXED_FUSE1 = registerBlock("electrical_panel_fixed_fuse1",
            () -> new ElectricPanelFixedFuse1(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_FIXED_FUSE2 = registerBlock("electrical_panel_fixed_fuse2",
            () -> new ElectricPanelFixedFuse2(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> ELECTRICAL_PANEL_FIXED_CLOSED = registerBlock("electrical_panel_fixed_fuse_closed",
            () -> new ElectricPanelFixedClosed(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));


    public static final RegistryObject<Block> LED_LAMP = registerBlock("led_lamp",
            () -> new LED_Lamp(BlockBehaviour.Properties.copy(Blocks.TORCH).sound(SoundType.NETHERITE_BLOCK)));



    //Barriers
    public static final RegistryObject<Block> BARRIER_GATE = registerBlock("barrier_gate",
            () -> new BarrierGate(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.GLASS)
                    .noOcclusion()
                    .dynamicShape()
                    .strength(-1)));
    public static final RegistryObject<Block> BARRIER_GATE_OFF = registerBlock("barrier_gate_off",
            () -> new BarrierGateOff(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.GLASS)
                    .noOcclusion()
                    .dynamicShape()
                    .strength(100)));
    public static final RegistryObject<Block> BARRIER_GATE_CONNECTOR = registerBlock("barrier_gate_connector",
            () -> new BarrierGateConnector(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.GLASS)
                    .noOcclusion()
                    .dynamicShape()
                    .strength(-1)));

    //pickle items
    public static final RegistryObject<Block> WIRE_CUTTERS_BLOCK = registerBlock("wire_cutters_block",
            () -> new WireCuttersBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> CROWBAR_BLOCK = registerBlock("crowbar_block",
            () -> new CrowbarBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> DUCT_TAPE_BLOCK = registerBlock("duct_tape_block",
            () -> new DuctTapeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> FUSE_BLOCK = registerBlock("fuse_block",
            () -> new FuseBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> GREEN_STAMP_BLOCK = registerBlock("green_stamp_block",
            () -> new GreenStamp(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> RED_STAMP_BLOCK = registerBlock("red_stamp_block",
            () -> new RedStamp(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));


    public static final RegistryObject<Block> KEYCARD1_BLOCK = registerBlock("keycard1_block",
            () -> new KeyCardBlock1(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> KEYCARD2_BLOCK = registerBlock("keycard2_block",
            () -> new KeyCardBlock2(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> KEYCARD3_BLOCK = registerBlock("keycard3_block",
            () -> new KeyCardBlock3(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> KEYCARD4_BLOCK = registerBlock("keycard4_block",
            () -> new KeyCardBlock4(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> KEYCARD5_BLOCK = registerBlock("keycard5_block",
            () -> new KeyCardBlock5(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    //sheet

    public static final RegistryObject<Block> SHEET = registerBlock("sheet",
            () -> new EmptySheetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> SHEET_RED = registerBlock("sheet_red",
            () -> new SheetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()));
    public static final RegistryObject<Block> SHEET_GREEN = registerBlock("sheet_green",
            () -> new SheetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return LabItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}