package ru.tz.economy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public final class TZEconomy {
    private static final String ROOT = "tz_economy";
    private static final String BALANCE = "balance";
    private static final long MAX_BALANCE = 1_000_000_000_000L;

    private TZEconomy() {}

    public static long getBalance(ServerPlayer player) {
        return Math.max(0L, player.getPersistentData().getCompound(ROOT).getLong(BALANCE));
    }

    public static void setBalance(ServerPlayer player, long amount) {
        CompoundTag data = player.getPersistentData();
        CompoundTag economy = data.getCompound(ROOT);
        economy.putLong(BALANCE, Math.max(0L, Math.min(MAX_BALANCE, amount)));
        data.put(ROOT, economy);
    }

    public static boolean transfer(ServerPlayer from, ServerPlayer to, long amount) {
        long senderBalance = getBalance(from);
        long receiverBalance = getBalance(to);
        if (from == to || amount <= 0 || senderBalance < amount || receiverBalance > MAX_BALANCE - amount) return false;
        setBalance(from, senderBalance - amount);
        setBalance(to, receiverBalance + amount);
        return true;
    }
}
