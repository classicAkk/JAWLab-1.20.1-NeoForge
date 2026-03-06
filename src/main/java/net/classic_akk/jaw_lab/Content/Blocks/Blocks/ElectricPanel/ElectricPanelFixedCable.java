package net.classic_akk.jaw_lab.Content.Blocks.Blocks.ElectricPanel;

import net.classic_akk.jaw_lab.Content.Blocks.LabBlocks;
import net.classic_akk.jaw_lab.Content.Items.LabItems;
import net.classic_akk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class ElectricPanelFixedCable extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(12, 1, 1, 13, 17, 3),
            Block.box(3, 1, 0, 13, 17, 1),
            Block.box(3, 0, 0, 13, 1, 4),
            Block.box(3, 17, 0, 13, 18, 4),
            Block.box(3, 1, 1, 4, 17, 3),
            Block.box(4, 11, 1, 12, 16, 2),
            Block.box(4, 5, 1, 12, 10, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(13, 1, 12, 15, 17, 13),
            Block.box(15, 1, 3, 16, 17, 13),
            Block.box(12, 0, 3, 16, 1, 13),
            Block.box(12, 17, 3, 16, 18, 13),
            Block.box(13, 1, 3, 15, 17, 4),
            Block.box(14, 11, 4, 15, 16, 12),
            Block.box(14, 5, 4, 15, 10, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape NORTH = Stream.of(
            Block.box(3, 1, 13, 4, 17, 15),
            Block.box(3, 1, 15, 13, 17, 16),
            Block.box(3, 0, 12, 13, 1, 16),
            Block.box(3, 17, 12, 13, 18, 16),
            Block.box(12, 1, 13, 13, 17, 15),
            Block.box(4, 11, 14, 12, 16, 15),
            Block.box(4, 5, 14, 12, 10, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(1, 1, 3, 3, 17, 4),
            Block.box(0, 1, 3, 1, 17, 13),
            Block.box(0, 0, 3, 4, 1, 13),
            Block.box(0, 17, 3, 4, 18, 13),
            Block.box(1, 1, 12, 3, 17, 13),
            Block.box(1, 11, 4, 2, 16, 12),
            Block.box(1, 5, 4, 2, 10, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ElectricPanelFixedCable(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).getItem().equals(LabItems.FUSE.get())) {
                pLevel.playSound(null, pPos, LabSounds.ELECTRIC_FUSE1.get(), SoundSource.BLOCKS, 0.5f, 1f);
                pLevel.setBlockAndUpdate(pPos, LabBlocks.ELECTRICAL_PANEL_FIXED_FUSE1.get().withPropertiesOf(pState));
            }
            else{
                pPlayer.displayClientMessage(Component.nullToEmpty(new String("Needs a Fuse")), true);
            }
        }
        return InteractionResult.SUCCESS;
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