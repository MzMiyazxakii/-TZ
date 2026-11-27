package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.nbt.CompoundTag;

/** Searchable prop. A search takes 140 server ticks (about seven seconds). */
public class TZSearchBlock extends Block {
    public TZSearchBlock(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.SUCCESS;

        CompoundTag data = serverPlayer.getPersistentData();
        CompoundTag search = new CompoundTag();
        search.putInt("x", pos.getX());
        search.putInt("y", pos.getY());
        search.putInt("z", pos.getZ());
        search.putInt("ticks", 140);
        search.putString("block", getDescriptionId());
        data.put("tz_active_search", search);
        serverPlayer.displayClientMessage(Component.literal("Вы начали поиск... (7 секунд)"), true);
        return InteractionResult.CONSUME;
    }
}
