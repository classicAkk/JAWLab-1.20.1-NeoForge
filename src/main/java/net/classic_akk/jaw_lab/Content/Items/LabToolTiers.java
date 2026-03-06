package net.classic_akk.jaw_lab.Content.Items;

import net.classic_akk.jaw_lab.Lab;
import net.classic_akk.jaw_lab.Util.LabTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class LabToolTiers {
    public static final Tier METAL = TierSortingRegistry.registerTier(
            new ForgeTier(1, 1500, 0.1f, 2f, 25,
                    LabTags.Blocks.NEEDS_CROWBAR_TOOL, () -> Ingredient.of(Items.IRON_INGOT)),
            new ResourceLocation(Lab.MOD_ID, "metal"), List.of(Tiers.NETHERITE), List.of());

}