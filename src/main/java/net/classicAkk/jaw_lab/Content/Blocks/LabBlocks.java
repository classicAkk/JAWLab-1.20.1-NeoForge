package net.classicAkk.jaw_lab.Content.Blocks;

import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.CodeDoor;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Decorations.GlassRailings;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Decorations.GlassRailingsCorner;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Decorations.Grating;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.DoorBottom;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors.KeyDoor;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.ElevatorButtons.InsideElevatorButton;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.ElevatorButtons.OutsideElevatorButton;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Barrier.BarrierGate;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Barrier.BarrierGateConnector;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.Barrier.BarrierGateOff;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.KeycardProgrammator;
import net.classicAkk.jaw_lab.Content.Blocks.Blocks.LEDLamp;
import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Content.Items.LabItems;
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
    public static final RegistryObject<Block> YELLOW_UP = registerBlock("yellow_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> YELLOW_DOWN = registerBlock("yellow_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));


    public static final RegistryObject<Block> CYAN_UP = registerBlock("cyan_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> CYAN_DOWN = registerBlock("cyan_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));


    public static final RegistryObject<Block> GREEN_UP = registerBlock("green_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GREEN_DOWN = registerBlock("green_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> RED_BLACK_UP = registerBlock("red_black_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_BLACK_DOWN = registerBlock("red_black_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> RED_UP = registerBlock("red_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_DOWN = registerBlock("red_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ORANGE_UP = registerBlock("orange_up",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ORANGE_DOWN = registerBlock("orange_down",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));


    //KeyDoors
    public static final RegistryObject<Block> DOOR_BOTTOM = registerBlock("door_bottom",
            () -> new DoorBottom(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    public static final RegistryObject<Block> KEY_DOOR = registerBlock("key_door",
            () -> new KeyDoor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    public static final RegistryObject<Block> CODE_DOOR = registerBlock("code_door",
            () -> new CodeDoor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()));

    //Decorations
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

    //Buttons
    public static final RegistryObject<Block> ELEVATOR_INSIDE = registerBlock("elevator_inside",
            () -> new InsideElevatorButton(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK),
                    BlockSetType.IRON, 20, true));
    public static final RegistryObject<Block> ELEVATOR_OUTSIDE = registerBlock("elevator_outside",
            () -> new OutsideElevatorButton(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK),
                    BlockSetType.IRON, 20, true));

    //Useful
    public static final RegistryObject<Block> LED_LAMP = registerBlock("led_lamp",
            () -> new LEDLamp(BlockBehaviour.Properties.copy(Blocks.TORCH).sound(SoundType.NETHERITE_BLOCK)));

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