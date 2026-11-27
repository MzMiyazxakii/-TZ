package ru.tz.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

/** Client-side markings for usable and locked inventory cells. */
@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class InventoryRestrictionOverlay {
    private InventoryRestrictionOverlay() {}

    @SubscribeEvent
    public static void onInventoryRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;
        GuiGraphics g = event.getGuiGraphics();
        int left = screen.getGuiLeft();
        int top = screen.getGuiTop();

        // Four usable cells (slots 9-12): keep every pixel inside the 18x18 slot bounds.
        for (int slot = 9; slot <= 12; slot++) {
            int x = left + 8 + (slot - 9) * 18;
            int y = top + 84;
            g.fill(x + 1, y + 1, x + 17, y + 17, 0x35FF3030);
            g.fill(x + 1, y + 1, x + 17, y + 2, 0xFFFF5555);
            g.fill(x + 1, y + 16, x + 17, y + 17, 0xFFFF5555);
            g.fill(x + 1, y + 2, x + 2, y + 16, 0xFFFF5555);
            g.fill(x + 16, y + 2, x + 17, y + 16, 0xFFFF5555);
        }

        // Slots 13-35: compact gold lock, centered and fully contained in each slot.
        for (int slot = 13; slot <= 35; slot++) {
            int index = slot - 9;
            int x = left + 8 + (index % 9) * 18;
            int y = top + 84 + (index / 9) * 18;
            int cx = x + 5;
            int cy = y + 4;
            g.fill(cx + 2, cy, cx + 7, cy + 2, 0xFFE0B34F);
            g.fill(cx + 1, cy + 2, cx + 2, cy + 6, 0xFFE0B34F);
            g.fill(cx + 7, cy + 2, cx + 8, cy + 6, 0xFFE0B34F);
            g.fill(cx, cy + 6, cx + 9, cy + 13, 0xFFE0B34F);
            g.fill(cx + 4, cy + 8, cx + 5, cy + 11, 0xFF59431C);
        }
    }
}
