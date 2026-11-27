package ru.tz.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.tz.registry.TZRegistry;

public class TZSecretChestBlockEntity extends ChestBlockEntity {
    public TZSecretChestBlockEntity(BlockPos pos, BlockState state) {
        super(TZRegistry.SECRET_CHEST_ENTITY.get(), pos, state);
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.tz.secret_chest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new TZSecretChestMenu(id, inventory, this);
    }
}
