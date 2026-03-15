package net.classicAkk.jaw_lab.Content.Blocks.Blocks.Sheets;

import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Items.LabItems;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EmptySheetBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NORTH = Block.box(4, 0, 1, 12, 0.1, 15);
    public static final VoxelShape EAST = Block.box(1, 0, 4, 15, 0.1, 12);
    public static final VoxelShape SOUTH = Block.box(4, 0, 1, 12, 0.1, 15);
    public static final VoxelShape WEST = Block.box(1, 0, 4, 15, 0.1, 12);

    public EmptySheetBlock(Properties pProperties) {
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


    /*
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).getItem().equals(LabItems.GREEN_STAMP.get())){
                pLevel.playSound(null, pPos, LabSounds.STAMP.get(), SoundSource.BLOCKS, 0.25f, 1f);
                pLevel.setBlockAndUpdate(pPos, LabBlocks.SHEET_GREEN.get().withPropertiesOf(pState));
            }
            if (pPlayer.getItemInHand(pHand).getItem().equals(LabItems.RED_STAMP.get())){
                pLevel.playSound(null, pPos, LabSounds.STAMP.get(), SoundSource.BLOCKS, 0.25f, 1f);
                pLevel.setBlockAndUpdate(pPos, LabBlocks.SHEET_RED.get().withPropertiesOf(pState));
            }
        }
        return InteractionResult.SUCCESS;
    }
     */
}