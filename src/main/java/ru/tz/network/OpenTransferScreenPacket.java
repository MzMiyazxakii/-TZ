package ru.tz.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record OpenTransferScreenPacket(UUID targetId, String targetName) {
    public static void encode(OpenTransferScreenPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.targetId);
        buf.writeUtf(msg.targetName, 64);
    }
    public static OpenTransferScreenPacket decode(FriendlyByteBuf buf) {
        return new OpenTransferScreenPacket(buf.readUUID(), buf.readUtf(64));
    }
    public static void handle(OpenTransferScreenPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ru.tz.client.TZClientScreens.openTransfer(msg.targetId, msg.targetName)));
        context.get().setPacketHandled(true);
    }
}
