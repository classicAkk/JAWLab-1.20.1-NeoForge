package net.classicAkk.jaw_lab.Content.Blocks.Blocks.Stamps;

import net.classicAkk.jaw_lab.Content.Items.LabItems;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
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

public class GreenStamp extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NORTH = Stream.of(
            Block.box(6, 0, 9.5, 10, 1, 10.5),
            Block.box(5.25, 8, 5.75, 10.75, 9, 10.25),
            Block.box(5, 5, 5.5, 11, 8, 10.5),
            Block.box(6, 0, 5.5, 10, 1, 6.5),
            Block.box(5, 1, 5.5, 6, 5, 6.5),
            Block.box(10, 1, 5.5, 11, 5, 6.5),
            Block.box(10, 1, 9.5, 11, 5, 10.5),
            Block.box(5, 1, 9.5, 6, 5, 10.5),
            Block.box(5, 0, 5.5, 6, 1, 10.5),
            Block.box(10, 0, 5.5, 11, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(9.5, 0, 6, 10.5, 1, 10),
            Block.box(5.75, 8, 5.25, 10.25, 9, 10.75),
            Block.box(5.5, 5, 5, 10.5, 8, 11),
            Block.box(5.5, 0, 6, 6.5, 1, 10),
            Block.box(5.5, 1, 10, 6.5, 5, 11),
            Block.box(5.5, 1, 5, 6.5, 5, 6),
            Block.box(9.5, 1, 5, 10.5, 5, 6),
            Block.box(9.5, 1, 10, 10.5, 5, 11),
            Block.box(5.5, 0, 10, 10.5, 1, 11),
            Block.box(5.5, 0, 5, 10.5, 1, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(6, 0, 9.5, 10, 1, 10.5),
            Block.box(5.25, 8, 5.75, 10.75, 9, 10.25),
            Block.box(5, 5, 5.5, 11, 8, 10.5),
            Block.box(6, 0, 5.5, 10, 1, 6.5),
            Block.box(5, 1, 5.5, 6, 5, 6.5),
            Block.box(10, 1, 5.5, 11, 5, 6.5),
            Block.box(10, 1, 9.5, 11, 5, 10.5),
            Block.box(5, 1, 9.5, 6, 5, 10.5),
            Block.box(5, 0, 5.5, 6, 1, 10.5),
            Block.box(10, 0, 5.5, 11, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(9.5, 0, 6, 10.5, 1, 10),
            Block.box(5.75, 8, 5.25, 10.25, 9, 10.75),
            Block.box(5.5, 5, 5, 10.5, 8, 11),
            Block.box(5.5, 0, 6, 6.5, 1, 10),
            Block.box(5.5, 1, 10, 6.5, 5, 11),
            Block.box(5.5, 1, 5, 6.5, 5, 6),
            Block.box(9.5, 1, 5, 10.5, 5, 6),
            Block.box(9.5, 1, 10, 10.5, 5, 11),
            Block.box(5.5, 0, 10, 10.5, 1, 11),
            Block.box(5.5, 0, 5, 10.5, 1, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public GreenStamp(Properties pProperties) {
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
    protected void createBlockStateDefinition (StateDefinition.Builder < Block, BlockState > builder){
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement (BlockPlaceContext context){
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror (BlockState state, Mirror mirror){
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate (BlockState state, LevelAccessor world, BlockPos pos, Rotation direction){
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
                pLevel.playSound(null, pPos, LabSounds.ITEM_PICKUP.get(), SoundSource.BLOCKS, 0.25f, 1f);

                ItemStack itemstack = new ItemStack(LabItems.GREEN_STAMP.get());
                pPlayer.addItem(itemstack);

                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
            }
            return InteractionResult.SUCCESS;
        }
}
