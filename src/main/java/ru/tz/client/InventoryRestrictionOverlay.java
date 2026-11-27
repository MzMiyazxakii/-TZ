package ru.tz.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

/** Client-side markings for the four usable secret pockets and locked cells. */
@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class InventoryRestrictionOverlay {
    private InventoryRestrictionOverlay() {}

    @SubscribeEvent
    public static void onInventoryRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;

        GuiGraphics graphics = event.getGuiGraphics();
        int left = screen.getGuiLeft();
        int top = screen.getGuiTop();

        graphics.drawString(screen.getMinecraft().font, "Секретные карманы",
                left + 8, top + 73, 0xFFFF5555, false);

        // Four uppermost cells of the ordinary 3x9 inventory grid: slots 9-12.
        graphics.fill(left + 7, top + 82, left + 81, top + 100, 0x44FF3333);
        graphics.fill(left + 7, top + 82, left + 81, top + 83, 0xFFFF5555);
        graphics.fill(left + 7, top + 99, left + 81, top + 100, 0xFFFF5555);
        graphics.fill(left + 7, top + 82, left + 8, top + 100, 0xFFFF5555);
        graphics.fill(left + 80, top + 82, left + 81, top + 100, 0xFFFF5555);

        // Slots 13-35: draw a small lock glyph without covering the whole cell.
        for (int slot = 13; slot <= 35; slot++) {
            int index = slot - 9;
            int x = left + 8 + (index % 9) * 18;
            int y = top + 84 + (index / 9) * 18;
            int cx = x + 7;
            int cy = y + 7;
            graphics.fill(cx + 2, cy, cx + 7, cy + 2, 0xFFB8B8B8);
            graphics.fill(cx + 1, cy + 2, cx + 8, cy + 8, 0xFF777777);
            graphics.fill(cx + 3, cy + 4, cx + 6, cy + 7, 0xFF303030);
        }
    }
}
