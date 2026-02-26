package net.classic_akk.ca_lab.Util;

import net.classic_akk.ca_lab.Lab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class LabTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_CROWBAR_TOOL = tag("needs_crowbar_tool");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Lab.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Lab.MOD_ID, name));
        }
    }
}