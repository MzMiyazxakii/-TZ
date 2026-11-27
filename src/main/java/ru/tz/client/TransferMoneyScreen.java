package ru.tz.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.tz.network.TZNetwork;
import ru.tz.network.TransferMoneyPacket;

import java.util.UUID;

public class TransferMoneyScreen extends Screen {
    private final UUID targetId;
    private final String targetName;
    private EditBox amountField;

    public TransferMoneyScreen(UUID targetId, String targetName) {
        super(Component.literal("Перевод денег"));
        this.targetId = targetId;
        this.targetName = targetName;
    }

    @Override
    protected void init() {
        int center = width / 2;
        amountField = new EditBox(font, center - 100, height / 2 - 8, 200, 20, Component.literal("Сумма"));
        amountField.setHint(Component.literal("Введите сумму"));
        amountField.setFilter(value -> value.matches("[0-9]{0,13}"));
        addRenderableWidget(amountField);
        addRenderableWidget(Button.builder(Component.literal("Передать"), button -> {
            try {
                long amount = Long.parseLong(amountField.getValue());
                if (amount > 0) {
                    TZNetwork.CHANNEL.sendToServer(new TransferMoneyPacket(targetId, amount));
                    onClose();
                }
            } catch (NumberFormatException ignored) {
                amountField.setFocused(true);
            }
        }).bounds(center - 100, height / 2 + 20, 200, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Отмена"), button -> onClose())
                .bounds(center - 100, height / 2 + 46, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        graphics.drawCenteredString(font, "Передать деньги игроку", width / 2, height / 2 - 55, 0xFFFFFF);
        graphics.drawCenteredString(font, targetName, width / 2, height / 2 - 40, 0xFFFFAA55);
        graphics.drawCenteredString(font, "Ваш баланс: " + TZClientData.getBalance(), width / 2, height / 2 - 25, 0x55FF55);
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
