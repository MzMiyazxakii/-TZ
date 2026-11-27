package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.tz.registry.TZRegistry;

public class TZSearchBedBlockEntity extends BedBlockEntity {
    public TZSearchBedBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, DyeColor.RED);
    }

    @Override
    public BlockEntityType<?> getType() {
        return TZRegistry.SEARCH_BED_ENTITY.get();
    }
}
