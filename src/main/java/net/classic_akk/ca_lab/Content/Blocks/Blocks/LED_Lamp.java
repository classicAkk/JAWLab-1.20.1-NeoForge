package net.classic_akk.ca_lab.Content.Blocks.Blocks;

import net.classic_akk.ca_lab.Content.Blocks.LabBlocks;
import net.classic_akk.ca_lab.Content.Sound.LabSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class LED_Lamp extends Block {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(7.2, 0, 7.2, 8.8, 4, 8.8),
            Block.box(7, 11, 7, 9, 11.5, 9),
            Block.box(7, 4, 7, 9, 4.5, 9),
            Block.box(7.3, 3.5, 7.3, 8.7, 11, 8.7)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public LED_Lamp(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            pLevel.playSound(null, pPos, LabSounds.ITEM_PICKUP.get(), SoundSource.BLOCKS, 0.25f, 1f);

            ItemStack itemstack = new ItemStack(LabBlocks.LED_LAMP.get());
            pPlayer.addItem(itemstack);

            pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        }
        return InteractionResult.SUCCESS;
    }
}
