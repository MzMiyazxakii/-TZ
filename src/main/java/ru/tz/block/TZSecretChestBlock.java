package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class TZSecretChestBlock extends ChestBlock {
    public TZSecretChestBlock(Properties properties,
                              Supplier<BlockEntityType<? extends net.minecraft.world.level.block.entity.ChestBlockEntity>> type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TZSecretChestBlockEntity(pos, state);
    }
}
