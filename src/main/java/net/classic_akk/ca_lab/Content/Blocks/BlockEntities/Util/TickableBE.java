package net.classic_akk.ca_lab.Content.Blocks.BlockEntities.Util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBE {
    void tick();

    public static <T extends BlockEntity>BlockEntityTicker<T> getTickerHelper(Level level) {
        return level.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((TickableBE)blockEntity).tick();
    }
}
