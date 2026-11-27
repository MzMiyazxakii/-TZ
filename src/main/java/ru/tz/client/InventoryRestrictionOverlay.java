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

        // Secret pockets: restrained red tint and border, all pixels stay inside each 16x16 slot.
        for (int slot = 9; slot <= 12; slot++) {
            int x = left + 8 + (slot - 9) * 18;
            int y = top + 84;
            g.fill(x + 1, y + 1, x + 17, y + 17, 0x66A00000);
            g.fill(x + 1, y + 1, x + 17, y + 2, 0xFFE04A4A);
            g.fill(x + 1, y + 16, x + 17, y + 17, 0xFFE04A4A);
            g.fill(x + 1, y + 2, x + 2, y + 16, 0xFFE04A4A);
            g.fill(x + 16, y + 2, x + 17, y + 16, 0xFFE04A4A);
        }

        // Locked inventory cells are simply dark grey; no lock glyphs or text.
        for (int slot = 13; slot <= 35; slot++) {
            int index = slot - 9;
            int x = left + 8 + (index % 9) * 18;
            int y = top + 84 + (index / 9) * 18;
            g.fill(x + 1, y + 1, x + 17, y + 17, 0xD9161616);
        }
    }
}
