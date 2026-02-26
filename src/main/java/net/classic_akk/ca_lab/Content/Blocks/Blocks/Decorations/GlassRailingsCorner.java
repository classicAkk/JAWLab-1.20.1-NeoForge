package net.classic_akk.ca_lab.Content.Blocks.Blocks.Decorations;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class GlassRailingsCorner extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NORTH = Stream.of(
            Block.box(8.25, 2, 7.75, 14, 14, 8.25),
            Block.box(7.75, 2, 2, 8.25, 14, 7.75),
            Block.box(7.25, 0, 0, 8.75, 16, 1.5),
            Block.box(14.5, 0, 7.25, 16, 16, 8.75),
            Block.box(7.5, 11.25, 1.5, 8.5, 13.25, 3),
            Block.box(7.5, 2.75, 1.5, 8.5, 4.75, 3),
            Block.box(13, 11.25, 7.5, 14.5, 13.25, 8.5),
            Block.box(13, 2.75, 7.5, 14.5, 4.75, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(7.75, 2, 8.25, 8.25, 14, 14),
            Block.box(8.25, 2, 7.75, 14, 14, 8.25),
            Block.box(14.5, 0, 7.25, 16, 16, 8.75),
            Block.box(7.25, 0, 14.5, 8.75, 16, 16),
            Block.box(13, 11.25, 7.5, 14.5, 13.25, 8.5),
            Block.box(13, 2.75, 7.5, 14.5, 4.75, 8.5),
            Block.box(7.5, 11.25, 13, 8.5, 13.25, 14.5),
            Block.box(7.5, 2.75, 13, 8.5, 4.75, 14.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(2, 2, 7.75, 7.75, 14, 8.25),
            Block.box(7.75, 2, 8.25, 8.25, 14, 14),
            Block.box(7.25, 0, 14.5, 8.75, 16, 16),
            Block.box(0, 0, 7.25, 1.5, 16, 8.75),
            Block.box(7.5, 11.25, 13, 8.5, 13.25, 14.5),
            Block.box(7.5, 2.75, 13, 8.5, 4.75, 14.5),
            Block.box(1.5, 11.25, 7.5, 3, 13.25, 8.5),
            Block.box(1.5, 2.75, 7.5, 3, 4.75, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(7.75, 2, 2, 8.25, 14, 7.75),
            Block.box(2, 2, 7.75, 7.75, 14, 8.25),
            Block.box(0, 0, 7.25, 1.5, 16, 8.75),
            Block.box(7.25, 0, 0, 8.75, 16, 1.5),
            Block.box(1.5, 11.25, 7.5, 3, 13.25, 8.5),
            Block.box(1.5, 2.75, 7.5, 3, 4.75, 8.5),
            Block.box(7.5, 11.25, 1.5, 8.5, 13.25, 3),
            Block.box(7.5, 2.75, 1.5, 8.5, 4.75, 3)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public GlassRailingsCorner(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return EAST;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            default:
                return NORTH;
        }
    }


            @Override
            protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                builder.add(FACING);
                super.createBlockStateDefinition(builder);
            }

            @Override
            public BlockState getStateForPlacement(BlockPlaceContext context) {
                return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
            }

            @SuppressWarnings("deprecation")
            @Override
            public BlockState mirror(BlockState state, Mirror mirror) {
                return state.rotate(mirror.getRotation(state.getValue(FACING)));
            }

            @Override
            public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
                return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
            }
}