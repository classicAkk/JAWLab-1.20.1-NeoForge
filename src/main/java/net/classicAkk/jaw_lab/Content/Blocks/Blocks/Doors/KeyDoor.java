package net.classicAkk.jaw_lab.Content.Blocks.Blocks.Doors;

import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.KeyDoors.KeyDoorBE;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.DoorState;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Util.TickableBE;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlockEntities;
import net.classicAkk.jaw_lab.Content.Blocks.LabBlocks;
import net.classicAkk.jaw_lab.Content.Items.LabItems;
import net.classicAkk.jaw_lab.Content.Sound.LabSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Vector;
import java.util.stream.Stream;

public class KeyDoor extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoorState> STATE = EnumProperty.create("state", DoorState.class);

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

    public static final VoxelShape NORTH_OPENED = Stream.of(
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(15, 0, 6.5, 16, 16, 9.5),
            Block.box(1, 15, 6.5, 15, 16, 9.5),
            Block.box(1, 0, 7.5, 2, 15, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST_OPENED = Stream.of(
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(6.5, 0, 0, 9.5, 16, 1),
            Block.box(6.5, 15, 1, 9.5, 16, 15),
            Block.box(7.5, 0, 14, 8.5, 15, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH_OPENED = Stream.of(
            Block.box(15, 0, 6.5, 16, 16, 9.5),
            Block.box(0, 0, 6.5, 1, 16, 9.5),
            Block.box(1, 15, 6.5, 15, 16, 9.5),
            Block.box(14, 0, 7.5, 15, 15, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST_OPENED = Stream.of(
            Block.box(6.5, 0, 0, 9.5, 16, 1),
            Block.box(6.5, 0, 15, 9.5, 16, 16),
            Block.box(6.5, 15, 1, 9.5, 16, 15),
            Block.box(7.5, 0, 1, 8.5, 15, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public KeyDoor(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(STATE, DoorState.CLOSED));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(STATE) != DoorState.OPENED) {
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(STATE);
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
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (pState.getBlock() == LabBlocks.KEY_DOOR.get()) {
                if (blockEntity instanceof KeyDoorBE keydoor) {
                    Vector<Item> cardList = new Vector<Item>();
                    cardList.add(0, null);
                    cardList.add(1, LabItems.KEYCARD1.get());
                    cardList.add(2, LabItems.KEYCARD2.get());
                    cardList.add(3, LabItems.KEYCARD3.get());
                    cardList.add(4, LabItems.KEYCARD4.get());
                    cardList.add(5, LabItems.KEYCARD5.get());

                    if (pPlayer.getItemInHand(pHand).getItem().equals(LabItems.DOOR_PROGRAMMATOR.get())) {
                        keydoor.setClevel(keydoor.getCLevel() + 1);
                        pPlayer.displayClientMessage(Component.literal(String.valueOf(keydoor.getCLevel())).withStyle(ChatFormatting.DARK_AQUA), true);
                        if (keydoor.getCLevel() >= 5) {
                            keydoor.setClevel(0);
                        }
                    } else {
                        if (pState.getValue(KeyDoor.STATE) != DoorState.CLOSED) return InteractionResult.FAIL;
                        if (keydoor.getCLevel() <= cardList.indexOf(pPlayer.getItemInHand(pHand).getItem())) {
                            pLevel.playSound(null, pPos, LabSounds.KEYDOOR_OPEN.get(), SoundSource.BLOCKS, 0.5f, 1f);
                            pLevel.setBlockAndUpdate(pPos, pState.setValue(STATE, DoorState.OPENED));
                            pLevel.setBlockAndUpdate(pPos.below(), LabBlocks.DOOR_BOTTOM.get().withPropertiesOf(pState.setValue(STATE, DoorState.OPENED)));
                            keydoor.resetTick();
                        } else {
                            pPlayer.displayClientMessage(Component.literal("Access Denied").withStyle(ChatFormatting.RED), true);
                            pLevel.playSound(null, pPos, LabSounds.KEYDOOR_ERROR.get(), SoundSource.BLOCKS, 0.5f, 1f);
                            pLevel.setBlockAndUpdate(pPos, pState.setValue(STATE, DoorState.ERROR));
                            keydoor.resetTick();
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos, LabBlocks.DOOR_BOTTOM.get().withPropertiesOf(state).setValue(STATE, DoorState.CLOSED), 3);
        level.setBlock(pos.above(), LabBlocks.KEY_DOOR.get().withPropertiesOf(state).setValue(STATE, DoorState.CLOSED), 3);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockState(pos.below()).getBlock() == LabBlocks.DOOR_BOTTOM.get()) {
            BlockPos below = pos.below();
            level.destroyBlock(below, false);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return LabBlockEntities.KEY_DOOR_BE.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBE.getTickerHelper(level);
    }
}