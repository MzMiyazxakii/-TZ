package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;

public class TZLockedDoorBlock extends DoorBlock {
    public TZLockedDoorBlock(Properties properties, BlockSetType type) {
        super(properties, type);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        // TZ doors are opened only by a key (or a future lock-picking item).
        return InteractionResult.FAIL;
    }

    @Override
    public void tick(BlockState state, Level level, BlockPos pos,
                     net.minecraft.server.level.ServerLevel serverLevel) {
        if (!state.hasProperty(OPEN) || !state.getValue(OPEN)) return;
        BlockPos lowerPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
        BlockState lower = level.getBlockState(lowerPos);
        if (!(lower.getBlock() instanceof TZLockedDoorBlock)) return;
        closeDoor(level, lowerPos, lower);
    }

    public static void openForOneMinute(Level level, BlockPos clickedPos, BlockState clickedState) {
        BlockPos lowerPos = clickedState.getValue(HALF) == DoubleBlockHalf.UPPER ? clickedPos.below() : clickedPos;
        BlockState lower = level.getBlockState(lowerPos);
        if (!(lower.getBlock() instanceof TZLockedDoorBlock)) return;
        BlockState upper = level.getBlockState(lowerPos.above());
        level.setBlock(lowerPos, lower.setValue(OPEN, true), 10);
        if (upper.getBlock() == lower.getBlock()) {
            level.setBlock(lowerPos.above(), upper.setValue(OPEN, true), 10);
        }
        level.scheduleTick(lowerPos, lower.getBlock(), 1200);
    }

    private static void closeDoor(Level level, BlockPos lowerPos, BlockState lower) {
        BlockState upper = level.getBlockState(lowerPos.above());
        level.setBlock(lowerPos, lower.setValue(OPEN, false), 10);
        if (upper.getBlock() == lower.getBlock()) {
            level.setBlock(lowerPos.above(), upper.setValue(OPEN, false), 10);
        }
    }
}
