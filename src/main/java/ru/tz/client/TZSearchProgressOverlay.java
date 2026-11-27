package ru.tz.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class TZSearchProgressOverlay {
    private TZSearchProgressOverlay() {}

    @SubscribeEvent
    public static void render(RenderGuiOverlayEvent.Post event) {
        if (!TZSearchClientEvents.isSearching()) return;
        GuiGraphics g = event.getGuiGraphics();
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        int x = width / 2 - 54;
        int y = height - 58;
        float progress = TZSearchClientEvents.getProgress();

        // Compact lock-and-progress indicator, centered above the hotbar.
        g.fill(x, y, x + 108, y + 22, 0xB0101010);
        g.fill(x + 5, y + 5, x + 17, y + 10, 0xFFE0B34F);
        g.fill(x + 7, y + 2, x + 15, y + 5, 0xFFE0B34F);
        g.fill(x + 8, y + 3, x + 14, y + 5, 0xFF101010);
        g.fill(x + 5, y + 10, x + 17, y + 18, 0xFFE0B34F);
        g.fill(x + 10, y + 12, x + 12, y + 16, 0xFF503B18);
        g.fill(x + 23, y + 8, x + 102, y + 15, 0xFF454545);
        g.fill(x + 23, y + 8, x + 23 + Math.round(79 * progress), y + 15, 0xFFE0B34F);
        g.drawCenteredString(Minecraft.getInstance().font, "Поиск", width / 2, y - 10, 0xFFFFFFFF);
    }
}
