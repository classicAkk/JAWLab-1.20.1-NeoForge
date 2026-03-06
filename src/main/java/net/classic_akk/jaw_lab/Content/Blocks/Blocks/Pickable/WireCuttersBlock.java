package net.classic_akk.jaw_lab.Content.Blocks.Blocks.Pickable;

import net.classic_akk.jaw_lab.Content.Items.LabItems;
import net.classic_akk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class WireCuttersBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NORTH = Stream.of(
            Block.box(5.75, 0, 2, 6.75, 1, 4),
            Block.box(5.5, 0, 0, 6.5, 1, 2),
            Block.box(9.25, 0, 0, 10.25, 1, 2),
            Block.box(9, 0, 2, 10, 1, 4),
            Block.box(6.5, 0, 4, 7.5, 1, 5),
            Block.box(8.5, 0, 4, 9.5, 1, 5),
            Block.box(7.25, 0, 5.75, 8.75, 1, 6.75),
            Block.box(7.5, 0, 4.5, 8.5, 1, 5.75)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(2, 0, 9.25, 4, 1, 10.25),
            Block.box(0, 0, 9.5, 2, 1, 10.5),
            Block.box(0, 0, 5.75, 2, 1, 6.75),
            Block.box(2, 0, 6, 4, 1, 7),
            Block.box(4, 0, 8.5, 5, 1, 9.5),
            Block.box(4, 0, 6.5, 5, 1, 7.5),
            Block.box(5.75, 0, 7.25, 6.75, 1, 8.75),
            Block.box(4.5, 0, 7.5, 5.75, 1, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(9.25, 0, 12, 10.25, 1, 14),
            Block.box(9.5, 0, 14, 10.5, 1, 16),
            Block.box(5.75, 0, 14, 6.75, 1, 16),
            Block.box(6, 0, 12, 7, 1, 14),
            Block.box(8.5, 0, 11, 9.5, 1, 12),
            Block.box(6.5, 0, 11, 7.5, 1, 12),
            Block.box(7.25, 0, 9.25, 8.75, 1, 10.25),
            Block.box(7.5, 0, 10.25, 8.5, 1, 11.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(12, 0, 5.75, 14, 1, 6.75),
            Block.box(14, 0, 5.5, 16, 1, 6.5),
            Block.box(14, 0, 9.25, 16, 1, 10.25),
            Block.box(12, 0, 9, 14, 1, 10),
            Block.box(11, 0, 6.5, 12, 1, 7.5),
            Block.box(11, 0, 8.5, 12, 1, 9.5),
            Block.box(9.25, 0, 7.25, 10.25, 1, 8.75),
            Block.box(10.25, 0, 7.5, 11.5, 1, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public WireCuttersBlock(Properties pProperties) {
        super(pProperties);

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

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
                pLevel.playSound(null, pPos, LabSounds.ITEM_PICKUP.get(), SoundSource.BLOCKS, 0.25f, 1f);

                ItemStack itemstack = new ItemStack(LabItems.WIRE_CUTTERS.get());
                pPlayer.addItem(itemstack);

                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
            }
            return InteractionResult.SUCCESS;
        }
}