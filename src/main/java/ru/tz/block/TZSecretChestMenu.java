package ru.tz.block;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;
import ru.tz.registry.TZRegistry;

public class TZSecretChestMenu extends AbstractContainerMenu {
    private final Container container;

    public TZSecretChestMenu(int id, Inventory playerInventory, Container container) {
        super(TZRegistry.SECRET_CHEST_MENU.get(), id);
        checkContainerSize(container, 4);
        this.container = container;
        container.startOpen(playerInventory.player);

        // Four storage slots, arranged as one compact row.
        for (int slot = 0; slot < 4; slot++) {
            addSlot(new Slot(container, slot, 62 + slot * 18, 18));
        }

        // Standard player inventory and hotbar.
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9,
                        8 + column * 18, 50 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, 8 + column * 18, 108));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            original = stack.copy();
            if (index < 4) {
                if (!moveItemStackTo(stack, 4, 40, true)) return ItemStack.EMPTY;
            } else if (!moveItemStackTo(stack, 0, 4, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
            if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, stack);
        }
        return original;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }
}
