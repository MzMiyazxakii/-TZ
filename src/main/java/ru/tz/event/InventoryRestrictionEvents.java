package ru.tz.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;

/**
 * Keeps the vanilla player inventory aligned with the restricted layout:
 * hotbar (0-8) and the four upper-left inventory cells (9-12) remain usable.
 */
@Mod.EventBusSubscriber(modid = TZMod.MOD_ID)
public final class InventoryRestrictionEvents {
    private InventoryRestrictionEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;

        // Slots 13-35 are visually locked. Move any pre-existing contents to
        // the hotbar rather than deleting them.
        for (int slot = 13; slot <= 35; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty()) continue;

            ItemStack remaining = stack.copy();
            for (int hotbar = 0; hotbar < 9 && !remaining.isEmpty(); hotbar++) {
                ItemStack target = player.getInventory().getItem(hotbar);
                if (target.isEmpty()) {
                    player.getInventory().setItem(hotbar, remaining.copy());
                    remaining.setCount(0);
                } else if (ItemStack.isSameItemSameTags(target, remaining)
                        && target.getCount() < target.getMaxStackSize()) {
                    int moved = Math.min(remaining.getCount(), target.getMaxStackSize() - target.getCount());
                    target.grow(moved);
                    remaining.shrink(moved);
                }
            }

            player.getInventory().setItem(slot, ItemStack.EMPTY);
            if (!remaining.isEmpty()) player.drop(remaining, false);
        }
    }
}
