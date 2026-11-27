package ru.tz.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import ru.tz.TZMod;

public final class TZNetwork {
    private static final String VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(TZMod.MOD_ID, "main"))
            .networkProtocolVersion(() -> VERSION)
            .clientAcceptedVersions(VERSION::equals)
            .serverAcceptedVersions(VERSION::equals)
            .simpleChannel();

    private static int id = 0;
    private TZNetwork() {}

    public static void register() {
        CHANNEL.messageBuilder(MoneySyncPacket.class, id++)
                .encoder(MoneySyncPacket::encode).decoder(MoneySyncPacket::decode)
                .consumerMainThread(MoneySyncPacket::handle).add();
        CHANNEL.messageBuilder(OpenTransferScreenPacket.class, id++)
                .encoder(OpenTransferScreenPacket::encode).decoder(OpenTransferScreenPacket::decode)
                .consumerMainThread(OpenTransferScreenPacket::handle).add();
        CHANNEL.messageBuilder(TransferMoneyPacket.class, id++)
                .encoder(TransferMoneyPacket::encode).decoder(TransferMoneyPacket::decode)
                .consumerMainThread(TransferMoneyPacket::handle).add();
    }

    public static void sync(net.minecraft.server.level.ServerPlayer player, long balance) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new MoneySyncPacket(balance));
    }
}
