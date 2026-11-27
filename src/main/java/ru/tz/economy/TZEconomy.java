package ru.tz.economy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public final class TZEconomy {
    private static final String ROOT = "tz_economy";
    private static final String BALANCE = "balance";

    private TZEconomy() {}

    public static long getBalance(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return Math.max(0L, data.getCompound(ROOT).getLong(BALANCE));
    }

    public static void setBalance(ServerPlayer player, long amount) {
        player.getPersistentData().getCompound(ROOT).putLong(BALANCE, Math.max(0L, amount));
    }

    public static boolean transfer(ServerPlayer from, ServerPlayer to, long amount) {
        if (from == to || amount <= 0 || getBalance(from) < amount) return false;
        setBalance(from, getBalance(from) - amount);
        setBalance(to, getBalance(to) + amount);
        return true;
    }
}
