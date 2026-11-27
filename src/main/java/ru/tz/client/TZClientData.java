package ru.tz.client;

public final class TZClientData {
    private static long balance;
    private TZClientData() {}
    public static long getBalance() { return balance; }
    public static void setBalance(long value) { balance = Math.max(0L, value); }
}
