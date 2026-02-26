package net.classic_akk.ca_lab.Content.Blocks.Blocks.Decorations;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DirectionalBlock extends FaceAttachedHorizontalDirectionalBlock {
    protected static final VoxelShape CEILING_AABB_X;
    protected static final VoxelShape CEILING_AABB_Z;
    protected static final VoxelShape FLOOR_AABB_X;
    protected static final VoxelShape FLOOR_AABB_Z;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape PRESSED_CEILING_AABB_X;
    protected static final VoxelShape PRESSED_CEILING_AABB_Z;
    protected static final VoxelShape PRESSED_FLOOR_AABB_X;
    protected static final VoxelShape PRESSED_FLOOR_AABB_Z;
    protected static final VoxelShape PRESSED_NORTH_AABB;
    protected static final VoxelShape PRESSED_SOUTH_AABB;
    protected static final VoxelShape PRESSED_WEST_AABB;
    protected static final VoxelShape PRESSED_EAST_AABB;
    private final BlockSetType type;

    public DirectionalBlock(Properties p_273290_, BlockSetType p_273462_, int p_273212_, boolean p_272786_) {
        super(p_273290_.sound(p_273462_.soundType()));
        this.type = p_273462_;
    }


    static {
        CEILING_AABB_X = Block.box(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
        CEILING_AABB_Z = Block.box(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
        FLOOR_AABB_X = Block.box(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
        FLOOR_AABB_Z = Block.box(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
        NORTH_AABB = Block.box(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
        SOUTH_AABB = Block.box(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
        WEST_AABB = Block.box(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
        EAST_AABB = Block.box(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
        PRESSED_CEILING_AABB_X = Block.box(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
        PRESSED_CEILING_AABB_Z = Block.box(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
        PRESSED_FLOOR_AABB_X = Block.box(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
        PRESSED_FLOOR_AABB_Z = Block.box(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
        PRESSED_NORTH_AABB = Block.box(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
        PRESSED_SOUTH_AABB = Block.box(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
        PRESSED_WEST_AABB = Block.box(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
        PRESSED_EAST_AABB = Block.box(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
    }
}
