package net.classic_akk.jaw_lab.Content.Blocks.Blocks;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeycardProgrammatorBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class KeycardProgrammator extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape NORTH = Stream.of(
            Block.box(0, 0, 0.5, 16, 3, 16.5),
            Block.box(0, 3, 0.5, 16, 16, 4.5),
            Block.box(1, 3, 4.5, 15, 13, 15.5),
            Block.box(0, 3, 11.5, 5, 16, 16.5),
            Block.box(0, 3, 5.5, 5, 14, 10.5),
            Block.box(11, 3, 5.5, 16, 14, 10.5),
            Block.box(11, 3, 11.5, 16, 16, 16.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(0.25, 0, 0.25, 16.25, 3, 16.25),
            Block.box(0.25, 3, 0.25, 4.25, 16, 16.25),
            Block.box(4.25, 3, 1.25, 15.25, 13, 15.25),
            Block.box(11.25, 3, 11.25, 16.25, 16, 16.25),
            Block.box(5.25, 3, 11.25, 10.25, 14, 16.25),
            Block.box(5.25, 3, 0.25, 10.25, 14, 5.25),
            Block.box(11.25, 3, 0.25, 16.25, 16, 5.25)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, 0, 16, 3, 16),
            Block.box(0, 3, 12, 16, 16, 16),
            Block.box(1, 3, 1, 15, 13, 12),
            Block.box(11, 3, 0, 16, 16, 5),
            Block.box(11, 3, 6, 16, 14, 11),
            Block.box(0, 3, 6, 5, 14, 11),
            Block.box(0, 3, 0, 5, 16, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(0.25, 0, 0.25, 16.25, 3, 16.25),
            Block.box(0.25, 3, 0.25, 4.25, 16, 16.25),
            Block.box(4.25, 3, 1.25, 15.25, 13, 15.25),
            Block.box(11.25, 3, 11.25, 16.25, 16, 16.25),
            Block.box(5.25, 3, 11.25, 10.25, 14, 16.25),
            Block.box(5.25, 3, 0.25, 10.25, 14, 5.25),
            Block.box(11.25, 3, 0.25, 16.25, 16, 5.25)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public KeycardProgrammator(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof KeycardProgrammatorBE) {
                ((KeycardProgrammatorBE) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof KeycardProgrammatorBE) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (KeycardProgrammatorBE)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new KeycardProgrammatorBE(pPos, pState);
    }
}
