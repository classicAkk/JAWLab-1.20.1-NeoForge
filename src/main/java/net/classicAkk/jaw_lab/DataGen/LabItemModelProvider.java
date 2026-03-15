package net.classicAkk.jaw_lab.DataGen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LabItemModelProvider extends ItemModelProvider {

    public LabItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, "lab", helper);
    }

    @Override
    protected void registerModels() {

        withExistingParent("armored_concrete_g",
                modLoc("block/armored_concrete_g"));

    }
}