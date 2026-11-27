package ru.tz.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record MoneySyncPacket(long balance) {
    public static void encode(MoneySyncPacket msg, FriendlyByteBuf buf) { buf.writeLong(msg.balance); }
    public static MoneySyncPacket decode(FriendlyByteBuf buf) { return new MoneySyncPacket(buf.readLong()); }
    public static void handle(MoneySyncPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ru.tz.client.TZClientData.setBalance(msg.balance)));
        context.get().setPacketHandled(true);
    }
}
