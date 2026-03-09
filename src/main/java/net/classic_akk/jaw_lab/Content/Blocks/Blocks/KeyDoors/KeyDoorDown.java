package net.classic_akk.jaw_lab.Content.Blocks.Blocks.KeyDoors;

import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class KeyDoorDown extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public static final VoxelShape NORTH = Stream.of(
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(1, 0, 7.5, 15, 16, 8.5),
            Block.box(15, 0, 6.5, 16, 16, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(7.5, 0, 1, 8.5, 16, 15),
            Block.box(6.5, 0, 0, 9.5, 16, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(1, 0, 7.5, 15, 16, 8.5),
            Block.box(15, 0, 6.5, 16, 16, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(7.5, 0, 1, 8.5, 16, 15),
            Block.box(6.5, 0, 0, 9.5, 16, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape NORTH_OPENED = Stream.of(
            Block.box(1, 0, 7.5, 2, 16, 8.5),
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(15, 0, 6.5, 16, 16, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH_OPENED = Stream.of(
            Block.box(14, 0, 7.5, 15, 16, 8.5),
            Block.box(15, 0, 6.5, 16, 16, 9.5),
            Block.box(0, 0, 6.5, 1, 16, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST_OPENED = Stream.of(
            Block.box(7.5, 0, 1, 8.5, 16, 2),
            Block.box(6.5, 0, 0, 9.5, 16, 1),
            Block.box(6.5, 0, 15, 9.5, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST_OPENED = Stream.of(
            Block.box(7.5, 0, 14, 8.5, 16, 15),
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(6.5, 0, 0, 9.5, 16, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public KeyDoorDown(Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (!state.getValue(OPEN)) {
            return switch (state.getValue(FACING)) {
                case EAST -> EAST;
                case SOUTH -> SOUTH;
                case WEST -> WEST;
                default -> NORTH;
            };
        }
        else {
            return switch (state.getValue(FACING)) {
                case EAST -> EAST_OPENED;
                case SOUTH -> SOUTH_OPENED;
                case WEST -> WEST_OPENED;
                default -> NORTH_OPENED;
            };
        }
    }

    public static boolean isOpen(BlockState state) {
        return state.getValue(BlockStateProperties.OPEN);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(OPEN);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), LabBlocks.KEYDOOR_UP.get().withPropertiesOf(state).setValue(OPEN, Boolean.FALSE), 3);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockState(pos.above()).getBlock() == LabBlocks.KEYDOOR_UP.get()
                || level.getBlockState(pos.above()).getBlock() == LabBlocks.KEYDOOR_UP_ERROR.get()
                || level.getBlockState(pos.above()).getBlock() == LabBlocks.CODE_DOOR_UP.get()
                || level.getBlockState(pos.above()).getBlock() == LabBlocks.CODE_DOOR_UP_ERROR.get()) {
            BlockPos above = pos.above();
            level.destroyBlock(above, false);
        }

        super.playerWillDestroy(level, pos, state, player);
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