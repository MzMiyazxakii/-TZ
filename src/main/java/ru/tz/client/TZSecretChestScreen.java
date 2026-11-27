package ru.tz.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import ru.tz.block.TZSecretChestMenu;

public class TZSecretChestScreen extends AbstractContainerScreen<TZSecretChestMenu> {
    private static final ResourceLocation BACKGROUND =
            new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    public TZSecretChestScreen(TZSecretChestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 132;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Red pocket tint, kept strictly inside the four storage slot frames.
        for (int slot = 0; slot < 4; slot++) {
            int x = leftPos + 62 + slot * 18;
            int y = topPos + 18;
            graphics.fill(x - 1, y - 1, x + 17, y + 17, 0x88C92323);
            graphics.fill(x, y, x + 16, y + 16, 0x44FF3030);
        }
    }
}
