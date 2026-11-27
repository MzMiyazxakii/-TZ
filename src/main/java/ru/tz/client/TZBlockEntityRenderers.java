package ru.tz.client;

import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.tz.TZMod;
import ru.tz.registry.TZRegistry;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TZBlockEntityRenderers {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TZRegistry.SEARCH_CHEST_ENTITY.get(), ChestRenderer::new);
        event.registerBlockEntityRenderer(TZRegistry.SECRET_CHEST_ENTITY.get(), ChestRenderer::new);
        event.registerBlockEntityRenderer(TZRegistry.SEARCH_BED_ENTITY.get(), BedRenderer::new);
    }

    private TZBlockEntityRenderers() {}
}
