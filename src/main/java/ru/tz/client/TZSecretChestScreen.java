package ru.tz.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ru.tz.block.TZSecretChestMenu;

public class TZSecretChestScreen extends AbstractContainerScreen<TZSecretChestMenu> {
    public TZSecretChestScreen(TZSecretChestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 132;
        this.inventoryLabelY = 38;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // Compact chest panel: one row for storage, then the player's inventory.
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF171717);
        graphics.fill(leftPos + 1, topPos + 1, leftPos + imageWidth - 1, topPos + 44, 0xFF292321);
        graphics.fill(leftPos + 1, topPos + 45, leftPos + imageWidth - 1, topPos + imageHeight - 1, 0xFF242424);
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + 1, 0xFF77706B);
        graphics.fill(leftPos, topPos + imageHeight - 1, leftPos + imageWidth, topPos + imageHeight, 0xFF77706B);
        graphics.fill(leftPos, topPos, leftPos + 1, topPos + imageHeight, 0xFF77706B);
        graphics.fill(leftPos + imageWidth - 1, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF77706B);

        // Red secret pockets, contained within the four slot frames.
        for (int slot = 0; slot < 4; slot++) {
            int x = leftPos + 62 + slot * 18;
            int y = topPos + 18;
            graphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF8F2424);
            graphics.fill(x, y, x + 16, y + 16, 0x66D93030);
        }
    }
}
