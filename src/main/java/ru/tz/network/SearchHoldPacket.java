package ru.tz.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import ru.tz.block.TZSearchable;

import java.util.function.Supplier;

public record SearchHoldPacket(BlockPos pos, boolean held) {
    public static void encode(SearchHoldPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeBoolean(msg.held);
    }

    public static SearchHoldPacket decode(FriendlyByteBuf buf) {
        return new SearchHoldPacket(buf.readBlockPos(), buf.readBoolean());
    }

    public static void handle(SearchHoldPacket msg, Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        context.get().enqueueWork(() -> {
            if (player == null) return;
            var data = player.getPersistentData();
            final String key = "tz_active_search";
            if (!msg.held) {
                if (data.contains(key)) {
                    var active = data.getCompound(key);
                    BlockPos activePos = new BlockPos(active.getInt("x"), active.getInt("y"), active.getInt("z"));
                    if (activePos.equals(msg.pos)) data.remove(key);
                }
                return;
            }
            if (player.distanceToSqr(msg.pos.getX() + 0.5, msg.pos.getY() + 0.5, msg.pos.getZ() + 0.5) > 36.0D) return;
            BlockState state = player.level().getBlockState(msg.pos);
            if (!(state.getBlock() instanceof TZSearchable)) return;
            var active = data.getCompound(key);
            boolean sameTarget = data.contains(key)
                    && active.getInt("x") == msg.pos.getX()
                    && active.getInt("y") == msg.pos.getY()
                    && active.getInt("z") == msg.pos.getZ();
            if (!sameTarget) {
                active = new net.minecraft.nbt.CompoundTag();
                active.putInt("x", msg.pos.getX());
                active.putInt("y", msg.pos.getY());
                active.putInt("z", msg.pos.getZ());
                active.putInt("ticks", 0);
            }
            active.putInt("heartbeat", 0);
            data.put(key, active);
        });
        context.get().setPacketHandled(true);
    }
}
