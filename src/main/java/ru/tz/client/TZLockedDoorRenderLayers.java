package ru.tz.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import ru.tz.TZMod;
import ru.tz.block.TZLockedDoorBlock;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TZLockedDoorRenderLayers {
    private TZLockedDoorRenderLayers() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block instanceof TZLockedDoorBlock)
                .forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout())));
    }
}
