package net.classicAkk.jaw_lab.DataGen;

import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class LabBlockTags extends BlockTagsProvider {
    public LabBlockTags(PackOutput output,
                        CompletableFuture<HolderLookup.Provider> lookupProvider,
                        ExistingFileHelper helper) {
        super(output, lookupProvider, "lab", helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(LabBlocks.ARMORED_CONCRETE_G.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(LabBlocks.ARMORED_CONCRETE_G.get());
    }
}