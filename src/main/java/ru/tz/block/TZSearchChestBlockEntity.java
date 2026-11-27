package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.tz.registry.TZRegistry;

public class TZSearchChestBlockEntity extends ChestBlockEntity {
    public TZSearchChestBlockEntity(BlockPos pos, BlockState state) {
        super(TZRegistry.SEARCH_CHEST_ENTITY.get(), pos, state);
    }
}
