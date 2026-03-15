package net.classicAkk.jaw_lab.DataGen;

import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class LabLootTables extends BlockLootSubProvider {
    public LabLootTables(PackOutput output) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(LabBlocks.ARMORED_CONCRETE_G.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return LabBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}