package net.classic_akk.jaw_lab.Content.Blocks.Blocks.ElevatorButtons;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class InsideElevatorButton extends FaceAttachedHorizontalDirectionalBlock {
    public static final BooleanProperty POWERED;
    private static final int PRESSED_DEPTH = 1;
    private static final int UNPRESSED_DEPTH = 2;
    protected static final int HALF_AABB_HEIGHT = 2;
    protected static final int HALF_AABB_WIDTH = 3;
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
    private final int ticksToStayPressed;
    private final boolean arrowsCanPress;

    public InsideElevatorButton(Properties p_273290_, BlockSetType p_273462_, int p_273212_, boolean p_272786_) {
        super(p_273290_.sound(p_273462_.soundType()));
        this.type = p_273462_;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(POWERED, false)).setValue(FACE, AttachFace.WALL));
        this.ticksToStayPressed = p_273212_;
        this.arrowsCanPress = p_272786_;
    }

    public VoxelShape getShape(BlockState p_51104_, BlockGetter p_51105_, BlockPos p_51106_, CollisionContext p_51107_) {
        Direction $$4 = (Direction)p_51104_.getValue(FACING);
        boolean $$5 = (Boolean)p_51104_.getValue(POWERED);
        switch ((AttachFace)p_51104_.getValue(FACE)) {
            case FLOOR:
                if ($$4.getAxis() == Axis.X) {
                    return $$5 ? PRESSED_FLOOR_AABB_X : FLOOR_AABB_X;
                }

                return $$5 ? PRESSED_FLOOR_AABB_Z : FLOOR_AABB_Z;
            case WALL:
                VoxelShape var10000;
                switch ($$4) {
                    case EAST:
                        var10000 = $$5 ? PRESSED_EAST_AABB : EAST_AABB;
                        break;
                    case WEST:
                        var10000 = $$5 ? PRESSED_WEST_AABB : WEST_AABB;
                        break;
                    case SOUTH:
                        var10000 = $$5 ? PRESSED_SOUTH_AABB : SOUTH_AABB;
                        break;
                    case NORTH:
                    case UP:
                    case DOWN:
                        var10000 = $$5 ? PRESSED_NORTH_AABB : NORTH_AABB;
                        break;
                    default:
                        throw new IncompatibleClassChangeError();
                }

                return var10000;
            case CEILING:
            default:
                if ($$4.getAxis() == Axis.X) {
                    return $$5 ? PRESSED_CEILING_AABB_X : CEILING_AABB_X;
                } else {
                    return $$5 ? PRESSED_CEILING_AABB_Z : CEILING_AABB_Z;
                }
        }
    }

    public InteractionResult use(BlockState p_51088_, Level p_51089_, BlockPos p_51090_, Player p_51091_, InteractionHand p_51092_, BlockHitResult p_51093_) {
        if ((Boolean)p_51088_.getValue(POWERED)) {
            return InteractionResult.CONSUME;
        } else {
            this.press(p_51088_, p_51089_, p_51090_);
            this.playSound(p_51091_, p_51089_, p_51090_, true);
            p_51089_.gameEvent(p_51091_, GameEvent.BLOCK_ACTIVATE, p_51090_);
            return InteractionResult.sidedSuccess(p_51089_.isClientSide);
        }
    }

    public void press(BlockState p_51117_, Level p_51118_, BlockPos p_51119_) {
        p_51118_.setBlock(p_51119_, (BlockState)p_51117_.setValue(POWERED, true), 3);
        this.updateNeighbours(p_51117_, p_51118_, p_51119_);
        p_51118_.scheduleTick(p_51119_, this, this.ticksToStayPressed);
    }

    protected void playSound(@Nullable Player p_51068_, LevelAccessor p_51069_, BlockPos p_51070_, boolean p_51071_) {
        p_51069_.playSound(p_51071_ ? p_51068_ : null, p_51070_, this.getSound(p_51071_), SoundSource.BLOCKS);
    }

    protected SoundEvent getSound(boolean p_51102_) {
        return p_51102_ ? this.type.buttonClickOn() : this.type.buttonClickOff();
    }

    public void onRemove(BlockState p_51095_, Level p_51096_, BlockPos p_51097_, BlockState p_51098_, boolean p_51099_) {
        if (!p_51099_ && !p_51095_.is(p_51098_.getBlock())) {
            if ((Boolean)p_51095_.getValue(POWERED)) {
                this.updateNeighbours(p_51095_, p_51096_, p_51097_);
            }

            super.onRemove(p_51095_, p_51096_, p_51097_, p_51098_, p_51099_);
        }
    }

    public int getSignal(BlockState p_51078_, BlockGetter p_51079_, BlockPos p_51080_, Direction p_51081_) {
        return (Boolean)p_51078_.getValue(POWERED) ? 15 : 0;
    }

    public int getDirectSignal(BlockState p_51109_, BlockGetter p_51110_, BlockPos p_51111_, Direction p_51112_) {
        return (Boolean)p_51109_.getValue(POWERED) && getConnectedDirection(p_51109_) == p_51112_ ? 15 : 0;
    }

    public boolean isSignalSource(BlockState p_51114_) {
        return true;
    }

    public void tick(BlockState p_220903_, ServerLevel p_220904_, BlockPos p_220905_, RandomSource p_220906_) {
        if ((Boolean)p_220903_.getValue(POWERED)) {
            this.checkPressed(p_220903_, p_220904_, p_220905_);
        }
    }

    public void entityInside(BlockState p_51083_, Level p_51084_, BlockPos p_51085_, Entity p_51086_) {
        if (!p_51084_.isClientSide && this.arrowsCanPress && !(Boolean)p_51083_.getValue(POWERED)) {
            this.checkPressed(p_51083_, p_51084_, p_51085_);
        }
    }

    protected void checkPressed(BlockState p_51121_, Level p_51122_, BlockPos p_51123_) {
        AbstractArrow $$3 = this.arrowsCanPress ? (AbstractArrow)p_51122_.getEntitiesOfClass(AbstractArrow.class, p_51121_.getShape(p_51122_, p_51123_).bounds().move(p_51123_)).stream().findFirst().orElse((AbstractArrow) null) : null;
        boolean $$4 = $$3 != null;
        boolean $$5 = (Boolean)p_51121_.getValue(POWERED);
        if ($$4 != $$5) {
            p_51122_.setBlock(p_51123_, (BlockState)p_51121_.setValue(POWERED, $$4), 3);
            this.updateNeighbours(p_51121_, p_51122_, p_51123_);
            this.playSound((Player)null, p_51122_, p_51123_, $$4);
            p_51122_.gameEvent($$3, $$4 ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, p_51123_);
        }

        if ($$4) {
            p_51122_.scheduleTick(new BlockPos(p_51123_), this, this.ticksToStayPressed);
        }

    }

    private void updateNeighbours(BlockState p_51125_, Level p_51126_, BlockPos p_51127_) {
        p_51126_.updateNeighborsAt(p_51127_, this);
        p_51126_.updateNeighborsAt(p_51127_.relative(getConnectedDirection(p_51125_).getOpposite()), this);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51101_) {
        p_51101_.add(new Property[]{FACING, POWERED, FACE});
    }

    static {
        POWERED = BlockStateProperties.POWERED;
        CEILING_AABB_X = Block.box(1, 15, 4, 15, 16, 12);
        CEILING_AABB_Z = Block.box(4, 15, 1, 12, 16, 15);
        FLOOR_AABB_X = Block.box(1, 0, 4, 15, 1, 12);
        FLOOR_AABB_Z = Block.box(4, 0, 1, 12, 1, 15);
        NORTH_AABB = Block.box(4, 1, 15, 12, 15, 16);
        SOUTH_AABB = Block.box(4, 1, 0, 12, 15, 1);
        WEST_AABB = Block.box(15, 1, 4, 16, 15, 12);
        EAST_AABB = Block.box(0, 1, 4, 1, 15, 12);
        PRESSED_CEILING_AABB_X = Block.box(1, 15, 4, 15, 16, 12);
        PRESSED_CEILING_AABB_Z = Block.box(4, 15, 1, 12, 16, 15);
        PRESSED_FLOOR_AABB_X = Block.box(1, 0, 4, 15, 1, 12);
        PRESSED_FLOOR_AABB_Z = Block.box(4, 0, 1, 12, 1, 15);
        PRESSED_NORTH_AABB = Block.box(4, 1, 15, 12, 15, 16);
        PRESSED_SOUTH_AABB = Block.box(4, 1, 0, 12, 15, 1);
        PRESSED_WEST_AABB = Block.box(15, 1, 4, 16, 15, 12);
        PRESSED_EAST_AABB = Block.box(0, 1, 4, 1, 15, 12);
    }
}
