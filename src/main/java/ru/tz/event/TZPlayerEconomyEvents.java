package ru.tz.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;
import ru.tz.economy.TZEconomy;
import ru.tz.network.OpenTransferScreenPacket;
import ru.tz.network.TZNetwork;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID)
public final class TZPlayerEconomyEvents {
    private TZPlayerEconomyEvents() {}

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            net.minecraft.nbt.CompoundTag oldData = event.getOriginal().getPersistentData();
            net.minecraft.nbt.CompoundTag newData = event.getEntity().getPersistentData();
            if (oldData.contains("tz_economy")) {
                newData.put("tz_economy", oldData.getCompound("tz_economy").copy());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        if (player.tickCount % 20 == 0) TZNetwork.sync(player, TZEconomy.getBalance(player));
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel().isClientSide || !(event.getEntity() instanceof ServerPlayer sender)
                || !(event.getTarget() instanceof ServerPlayer target)) return;
        if (sender.distanceToSqr(target) > 64.0D) return;
        event.setCanceled(true);
        TZNetwork.CHANNEL.send(net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> sender),
                new OpenTransferScreenPacket(target.getUUID(), target.getName().getString()));
    }
}
