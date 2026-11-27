package ru.tz;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import ru.tz.registry.TZRegistry;
import ru.tz.network.TZNetwork;

@Mod(TZMod.MOD_ID)
public final class TZMod {
    public static final String MOD_ID = "tz";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TZMod() {
        TZRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
        TZNetwork.register();
        LOGGER.info("TZ mod initialized for Minecraft 1.20.1");
    }
}
