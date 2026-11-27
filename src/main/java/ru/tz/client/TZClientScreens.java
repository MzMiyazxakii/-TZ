package ru.tz.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ru.tz.TZMod;
import ru.tz.registry.TZRegistry;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = TZMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TZClientScreens {
    private TZClientScreens() {}

    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(TZRegistry.SECRET_CHEST_MENU.get(), TZSecretChestScreen::new));
    }

    public static void openTransfer(UUID targetId, String targetName) {
        Minecraft.getInstance().setScreen(new TransferMoneyScreen(targetId, targetName));
    }
}
