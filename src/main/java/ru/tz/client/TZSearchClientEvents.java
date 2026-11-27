package ru.tz.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;
import ru.tz.block.TZSearchable;
import ru.tz.network.SearchHoldPacket;
import ru.tz.network.TZNetwork;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, value = Dist.CLIENT)
public final class TZSearchClientEvents {
    private static BlockPos activePos;
    private static int heldTicks;
    private static int packetCooldown;

    private TZSearchClientEvents() {}

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null || minecraft.screen != null
                || !minecraft.options.keyUse.isDown()) {
            stopIfNeeded();
            return;
        }
        if (!(minecraft.hitResult instanceof BlockHitResult hit) || hit.getType() != HitResult.Type.BLOCK
                || !(minecraft.level.getBlockState(hit.getBlockPos()).getBlock() instanceof TZSearchable)) {
            stopIfNeeded();
            return;
        }
        BlockPos target = hit.getBlockPos().immutable();
        if (!target.equals(activePos)) {
            stopIfNeeded();
            activePos = target;
            heldTicks = 0;
            packetCooldown = 0;
            send(true, target);
        }
        heldTicks++;
        if (++packetCooldown >= 5) {
            packetCooldown = 0;
            send(true, activePos);
        }
    }

    private static void stopIfNeeded() {
        if (activePos == null) return;
        send(false, activePos);
        activePos = null;
        heldTicks = 0;
        packetCooldown = 0;
    }

    private static void send(boolean held, BlockPos pos) {
        TZNetwork.CHANNEL.sendToServer(new SearchHoldPacket(pos, held));
    }

    public static boolean isSearching() { return activePos != null; }
    public static float getProgress() { return Math.min(1.0F, heldTicks / 140.0F); }
}
