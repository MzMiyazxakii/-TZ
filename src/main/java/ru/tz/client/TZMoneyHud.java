package ru.tz.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class TZMoneyHud {
    private static final int RIGHT_MARGIN = 8;
    private static final int TOP_MARGIN = 8;
    private static final int PANEL_HEIGHT = 22;

    private TZMoneyHud() {}

    @SubscribeEvent
    public static void renderMoney(RenderGuiOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        GuiGraphics graphics = event.getGuiGraphics();
        String amount = String.valueOf(TZClientData.getBalance());
        int textWidth = minecraft.font.width(amount);
        int panelWidth = 31 + textWidth + 10;
        int x = event.getWindow().getGuiScaledWidth() - RIGHT_MARGIN - panelWidth;
        int y = TOP_MARGIN;

        // Compact dark panel with a restrained gold edge.
        graphics.fill(x - 1, y + 1, x + panelWidth + 1, y + PANEL_HEIGHT + 1, 0x55000000);
        graphics.fill(x, y, x + panelWidth, y + PANEL_HEIGHT, 0xE8171B24);
        graphics.fill(x, y, x + panelWidth, y + 1, 0xFFFFC857);
        graphics.fill(x, y + PANEL_HEIGHT - 1, x + panelWidth, y + PANEL_HEIGHT, 0xFF66502B);

        drawCoin(graphics, x + 7, y + 6);

        int textX = x + 25;
        int textY = y + (PANEL_HEIGHT - 9) / 2;
        graphics.drawString(minecraft.font, amount, textX, textY, 0xFFFFE2A0, true);
    }

    private static void drawCoin(GuiGraphics graphics, int x, int y) {
        // Small pixel-art coin: dark rim, bright gold face and a stamped center.
        graphics.fill(x + 2, y, x + 8, y + 1, 0xFFFFE18A);
        graphics.fill(x, y + 2, x + 10, y + 8, 0xFFB87920);
        graphics.fill(x + 1, y + 1, x + 9, y + 9, 0xFFFFC247);
        graphics.fill(x + 2, y + 2, x + 8, y + 8, 0xFFFFDF78);
        graphics.fill(x + 3, y + 3, x + 7, y + 7, 0xFFD58B22);
        graphics.fill(x + 4, y + 3, x + 6, y + 7, 0xFFFFF0B0);
        graphics.fill(x + 3, y + 4, x + 7, y + 5, 0xFFFFF0B0);
    }
}
