package ru.tz.client;

import net.minecraft.client.Minecraft;
import java.util.UUID;

public final class TZClientScreens {
    private TZClientScreens() {}
    public static void openTransfer(UUID targetId, String targetName) {
        Minecraft.getInstance().setScreen(new TransferMoneyScreen(targetId, targetName));
    }
}
