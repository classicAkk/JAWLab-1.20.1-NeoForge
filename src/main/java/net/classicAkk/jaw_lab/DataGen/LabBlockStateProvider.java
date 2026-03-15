package net.classicAkk.jaw_lab.DataGen;

import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LabBlockStateProvider extends BlockStateProvider {

    public LabBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, "lab", exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlock(LabBlocks.ARMORED_CONCRETE_G.get(),
                cubeAll(LabBlocks.ARMORED_CONCRETE_G.get()));

    }
}