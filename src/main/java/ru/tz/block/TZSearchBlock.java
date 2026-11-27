package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TZSearchBlock extends Block {
    public TZSearchBlock(Properties properties) { super(properties); }

    public static InteractionResult beginSearch(BlockPos pos, Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.SUCCESS;
        CompoundTag search = new CompoundTag();
        search.putInt("x", pos.getX());
        search.putInt("y", pos.getY());
        search.putInt("z", pos.getZ());
        search.putInt("ticks", 140);
        serverPlayer.getPersistentData().put("tz_active_search", search);
        serverPlayer.displayClientMessage(Component.literal("Вы начали поиск... (7 секунд)"), true);
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        return beginSearch(pos, player, hand);
    }
}
