package net.classic_akk.jaw_lab.Content.Blocks;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Barriers.BarrierGateBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Barriers.BarrierGateOffBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors.CodeDoorBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeyDoors.KeyDoorBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeycardProgrammatorBE;
import net.classic_akk.jaw_lab.Lab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LabBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Lab.MOD_ID);

    public static final RegistryObject<BlockEntityType<KeycardProgrammatorBE>> KEYCARD_PROGRAMMATOR =
            BLOCK_ENTITIES.register("keycard_programmator",
                    ()-> BlockEntityType.Builder.of(KeycardProgrammatorBE::new, LabBlocks.KEYCARD_PROGRAMMATOR.get()).build(null));
    //KeyDoors
    public static final RegistryObject<BlockEntityType<KeyDoorBE>> KEY_DOOR_BE =
            BLOCK_ENTITIES.register("key_door_be",
                    ()-> BlockEntityType.Builder.of(KeyDoorBE::new, LabBlocks.KEY_DOOR.get()).build(null));
    //CodeDoors
    public static final RegistryObject<BlockEntityType<CodeDoorBE>> CODE_DOOR_BE =
            BLOCK_ENTITIES.register("code_door_be",
                    ()-> BlockEntityType.Builder.of(CodeDoorBE::new, LabBlocks.CODE_DOOR.get()).build(null));
    //Gates
    public static final RegistryObject<BlockEntityType<BarrierGateBE>> BARRIER_GATE_BE =
            BLOCK_ENTITIES.register("barrier_gate_be",
                    ()-> BlockEntityType.Builder.of(BarrierGateBE::new, LabBlocks.BARRIER_GATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<BarrierGateOffBE>> BARRIER_GATE_OFF =
            BLOCK_ENTITIES.register("barrier_gate_off",
                    ()-> BlockEntityType.Builder.of(BarrierGateOffBE::new, LabBlocks.BARRIER_GATE_OFF.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}