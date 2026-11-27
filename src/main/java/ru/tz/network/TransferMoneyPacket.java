package ru.tz.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import ru.tz.economy.TZEconomy;

import java.util.UUID;
import java.util.function.Supplier;

public record TransferMoneyPacket(UUID targetId, long amount) {
    public static void encode(TransferMoneyPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.targetId);
        buf.writeLong(msg.amount);
    }
    public static TransferMoneyPacket decode(FriendlyByteBuf buf) {
        return new TransferMoneyPacket(buf.readUUID(), buf.readLong());
    }
    public static void handle(TransferMoneyPacket msg, Supplier<NetworkEvent.Context> context) {
        ServerPlayer sender = context.get().getSender();
        context.get().enqueueWork(() -> {
            if (sender == null || msg.amount <= 0 || msg.amount > 1_000_000_000_000L) return;
            Player found = sender.serverLevel().getPlayerByUUID(msg.targetId);
            if (!(found instanceof ServerPlayer target)) {
                sender.displayClientMessage(net.minecraft.network.chat.Component.literal("Игрок не найден"), true);
                return;
            }
            if (sender.distanceToSqr(target) > 64.0D) {
                sender.displayClientMessage(net.minecraft.network.chat.Component.literal("Подойдите ближе к игроку"), true);
                return;
            }
            if (!TZEconomy.transfer(sender, target, msg.amount)) {
                sender.displayClientMessage(net.minecraft.network.chat.Component.literal("Недостаточно денег или неверная сумма"), true);
                return;
            }
            TZNetwork.sync(sender, TZEconomy.getBalance(sender));
            TZNetwork.sync(target, TZEconomy.getBalance(target));
            sender.displayClientMessage(net.minecraft.network.chat.Component.literal("Передано " + msg.amount + " монет игроку " + target.getName().getString()), true);
            target.displayClientMessage(net.minecraft.network.chat.Component.literal("Вы получили " + msg.amount + " монет от " + sender.getName().getString()), true);
        });
        context.get().setPacketHandled(true);
    }
}
