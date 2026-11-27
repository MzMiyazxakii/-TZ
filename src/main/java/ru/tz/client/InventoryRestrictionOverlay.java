package ru.tz.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

/** Minimal inventory slot markings; all drawing stays inside vanilla slot bounds. */
@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class InventoryRestrictionOverlay {
    private InventoryRestrictionOverlay() {}

    @SubscribeEvent
    public static void onInventoryRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;
        GuiGraphics g = event.getGuiGraphics();
        int left = screen.getGuiLeft();
        int top = screen.getGuiTop();

        // Slots 9-12 remain usable: a one-pixel red outline, without a tinted fill.
        for (int slot = 9; slot <= 12; slot++) {
            int x = left + 8 + (slot - 9) * 18;
            int y = top + 84;
            g.fill(x + 1, y + 1, x + 17, y + 2, 0xFFD05A5A);
            g.fill(x + 1, y + 16, x + 17, y + 17, 0xFFD05A5A);
            g.fill(x + 1, y + 2, x + 2, y + 16, 0xFFD05A5A);
            g.fill(x + 16, y + 2, x + 17, y + 16, 0xFFD05A5A);
        }

        // Slots 13-35: small, muted lock glyphs; no large opaque blocks.
        for (int slot = 13; slot <= 35; slot++) {
            int index = slot - 9;
            int x = left + 8 + (index % 9) * 18;
            int y = top + 84 + (index / 9) * 18;
            int cx = x + 6;
            int cy = y + 5;

            // Shackle.
            g.fill(cx + 2, cy, cx + 5, cy + 1, 0xFF8A744B);
            g.fill(cx + 1, cy + 1, cx + 2, cy + 4, 0xFF8A744B);
            g.fill(cx + 5, cy + 1, cx + 6, cy + 4, 0xFF8A744B);
            // Body and keyhole.
            g.fill(cx, cy + 4, cx + 7, cy + 9, 0xFFB99A5C);
            g.fill(cx + 3, cy + 5, cx + 4, cy + 8, 0xFF51432D);
        }
    }
}
