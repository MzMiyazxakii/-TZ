package ru.tz.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.SimpleContainer;
import ru.tz.TZMod;
import ru.tz.block.TZSearchBlock;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID)
public final class TZSearchEvents {
    private static final String SEARCH_TAG = "tz_active_search";
    private TZSearchEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        var data = player.getPersistentData();
        if (!data.contains(SEARCH_TAG)) return;

        var search = data.getCompound(SEARCH_TAG);
        int ticks = search.getInt("ticks") - 1;
        if (ticks > 0) {
            search.putInt("ticks", ticks);
            data.put(SEARCH_TAG, search);
            return;
        }

        BlockPos pos = new BlockPos(search.getInt("x"), search.getInt("y"), search.getInt("z"));
        data.remove(SEARCH_TAG);
        if (player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > 36.0D) {
            player.displayClientMessage(Component.literal("Поиск прерван: вы отошли слишком далеко"), true);
            return;
        }
        BlockState state = player.level().getBlockState(pos);
        if (!(state.getBlock() instanceof TZSearchBlock)) return;

        // Placeholder inventory: the loot table will populate this container in a later update.
        SimpleContainer loot = new SimpleContainer(27);
        player.openMenu(new SimpleMenuProvider(
                (id, inventory, owner) -> ChestMenu.threeRows(id, inventory, loot),
                Component.literal("Найденные предметы")));
    }
}
