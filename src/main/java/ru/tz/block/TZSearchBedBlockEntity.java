package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.tz.registry.TZRegistry;

public class TZSearchBedBlockEntity extends BedBlockEntity {
    public TZSearchBedBlockEntity(BlockPos pos, BlockState state) {
        super(TZRegistry.SEARCH_BED_ENTITY.get(), pos, state, DyeColor.RED);
    }
}
