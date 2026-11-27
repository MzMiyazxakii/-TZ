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
    private TZMoneyHud() {}

    @SubscribeEvent
    public static void renderMoney(RenderGuiOverlayEvent.Post event) {
        if (Minecraft.getInstance().player == null) return;
        GuiGraphics graphics = event.getGuiGraphics();
        String text = "¤ " + TZClientData.getBalance();
        int x = 8;
        int y = 8;
        graphics.fill(x - 3, y - 3, x + Minecraft.getInstance().font.width(text) + 5, y + 13, 0x88000000);
        graphics.drawString(Minecraft.getInstance().font, text, x, y, 0xFFFFD54A, true);
    }
}
