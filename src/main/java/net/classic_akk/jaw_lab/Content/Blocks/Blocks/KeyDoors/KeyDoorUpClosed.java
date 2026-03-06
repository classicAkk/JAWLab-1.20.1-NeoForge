package net.classic_akk.jaw_lab.Content.Blocks.Blocks.KeyDoors;

import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeyDoorErrorBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeyDoorOpenedBE;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.KeyDoorBE;
import net.classic_akk.jaw_lab.Content.Blocks.LabBlockEntities;
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
import net.minecraft.world.item.Item;
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
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.stream.Stream;

public class KeyDoorUpClosed extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NORTH = Stream.of(
            Block.box(6.2, 6, 6.9, 9.8, 7, 9.1),
            Block.box(15, 0, 6.5, 16, 16, 9.5),
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(1, 15, 6.5, 15, 16, 9.5),
            Block.box(1, 0, 7.5, 15, 15, 8.5),
            Block.box(6, 2, 7, 10, 7.4, 9),
            Block.box(6.5, 2.6, 6.95, 9.5, 5.6, 9.05)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST = Stream.of(
            Block.box(6.9, 6, 6.2, 9.1, 7, 9.8),
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(6.5, 0, 0, 9.5, 16, 1),
            Block.box(6.5, 15, 1, 9.5, 16, 15),
            Block.box(7.5, 0, 1, 8.5, 15, 15),
            Block.box(7, 2, 6, 9, 7.4, 10),
            Block.box(6.95, 2.6, 6.5, 9.05, 5.6, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH = Stream.of(
            Block.box(6.2, 6, 6.9, 9.8, 7, 9.1),
            Block.box(15, 0, 6.5, 16, 16, 9.5),
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(1, 15, 6.5, 15, 16, 9.5),
            Block.box(1, 0, 7.5, 15, 15, 8.5),
            Block.box(6, 2, 7, 10, 7.4, 9),
            Block.box(6.5, 2.6, 6.95, 9.5, 5.6, 9.05)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST = Stream.of(
            Block.box(6.9, 6, 6.2, 9.1, 7, 9.8),
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(6.5, 0, 0, 9.5, 16, 1),
            Block.box(6.5, 15, 1, 9.5, 16, 15),
            Block.box(7.5, 0, 1, 8.5, 15, 15),
            Block.box(7, 2, 6, 9, 7.4, 10),
            Block.box(6.95, 2.6, 6.5, 9.05, 5.6, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public KeyDoorUpClosed(Properties properties) {
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

    public void setData(BlockEntity be, int level){
        if (be instanceof KeyDoorOpenedBE block) {

            block.setClevel(level);
            block.setChanged();
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof KeyDoorBE keydoor) {
                Vector<Item> cardlist = new Vector<Item>();
                cardlist.add(0, null);
                cardlist.add(1, LabItems.KEYCARD1.get());
                cardlist.add(2, LabItems.KEYCARD2.get());
                cardlist.add(3, LabItems.KEYCARD3.get());
                cardlist.add(4, LabItems.KEYCARD4.get());
                cardlist.add(5, LabItems.KEYCARD5.get());

                if (pPlayer.getItemInHand(pHand).getItem().equals(LabItems.KEYDOOR_PROGRAMMER.get())) {
                    keydoor.setClevel(keydoor.getCLevel() + 1);
                    pPlayer.displayClientMessage(Component.nullToEmpty(new String(String.valueOf(keydoor.getCLevel()))), true);
                    if (keydoor.getCLevel() >= 5) {
                        keydoor.setClevel(0);
                    }
                }
                else {
                    if (keydoor.getCLevel() <= cardlist.indexOf(pPlayer.getItemInHand(pHand).getItem())) {
                        //KeyDoorOpened_BE keydoor_opened = (KeyDoorOpened_BE) blockEntity;

                        pLevel.playSound(null, pPos, LabSounds.KEYDOOR_OPEN.get(), SoundSource.BLOCKS, 0.5f, 1f);
                        pLevel.setBlockAndUpdate(pPos, LabBlocks.KEYDOOR_UP_OPENED.get().withPropertiesOf(pState));
                        pLevel.setBlockAndUpdate(pPos.below(), LabBlocks.KEYDOOR_DOWN_OPENED.get().withPropertiesOf(pState));
                        BlockEntity newBlockEntity = pLevel.getBlockEntity(pPos);
                        setData(newBlockEntity, keydoor.getCLevel());

                    } else {
                        pPlayer.displayClientMessage(Component.nullToEmpty(new String("Access Denied")), true);
                        pLevel.playSound(null, pPos, LabSounds.KEYDOOR_ERROR.get(), SoundSource.BLOCKS, 0.5f, 1f);
                        pLevel.setBlockAndUpdate(pPos, LabBlocks.KEYDOOR_UP_ERROR.get().withPropertiesOf(pState));

                        BlockEntity newBlockEntity = pLevel.getBlockEntity(pPos);
                        if (newBlockEntity instanceof KeyDoorErrorBE block) {
                            block.setClevel(keydoor.getCLevel());
                            block.setChanged();
                        }
                    }
                }
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return LabBlockEntities.KEY_DOOR_BE.get().create(pos,state);
    }
}