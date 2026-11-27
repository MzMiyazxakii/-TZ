package ru.tz.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class InventoryRestrictionOverlay {
    private InventoryRestrictionOverlay() {}

    @SubscribeEvent
    public static void onInventoryRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;
        GuiGraphics g = event.getGuiGraphics();
        int left = screen.getGuiLeft();
        int top = screen.getGuiTop();

        // Restriction marker stays strictly inside the four available pocket slots.
        for (int slot = 9; slot <= 12; slot++) {
            int x = left + 8 + (slot - 9) * 18;
            int y = top + 84;
            int red = 0xFFC65B5B;
            g.fill(x + 1, y + 1, x + 17, y + 2, red);
            g.fill(x + 1, y + 15, x + 17, y + 16, red);
            g.fill(x + 1, y + 2, x + 2, y + 15, red);
            g.fill(x + 16, y + 2, x + 17, y + 15, red);
        }

        // Quiet, compact lock marks; no opaque panels or text over the inventory.
        for (int slot = 13; slot <= 35; slot++) {
            int index = slot - 9;
            int x = left + 8 + (index % 9) * 18;
            int y = top + 84 + (index / 9) * 18;
            int cx = x + 6;
            int cy = y + 5;
            int metal = 0xFF887F70;
            int body = 0xFF9B917F;
            g.fill(cx + 2, cy, cx + 4, cy + 1, metal);
            g.fill(cx + 1, cy + 1, cx + 2, cy + 3, metal);
            g.fill(cx + 4, cy + 1, cx + 5, cy + 3, metal);
            g.fill(cx + 1, cy + 3, cx + 5, cy + 7, body);
            g.fill(cx + 3, cy + 4, cx + 4, cy + 6, 0xFF514B43);
        }
    }
}
