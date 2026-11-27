package ru.tz.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import ru.tz.block.TZLockedDoorBlock;

public class TZKeyItem extends Item {
    private static final String TAG_DIMENSION = "BoundDimension";
    private static final String TAG_POS = "BoundDoor";

    public TZKeyItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        if (!(state.getBlock() instanceof TZLockedDoorBlock)) return InteractionResult.PASS;

        BlockPos lowerPos = state.getValue(TZLockedDoorBlock.HALF) == DoubleBlockHalf.UPPER
                ? clickedPos.below() : clickedPos;
        CompoundTag tag = context.getItemInHand().getOrCreateTag();
        String dimension = level.dimension().location().toString();
        String position = lowerPos.getX() + "," + lowerPos.getY() + "," + lowerPos.getZ();

        if (!tag.contains(TAG_DIMENSION) || !tag.contains(TAG_POS)) {
            if (context.getPlayer() == null || !context.getPlayer().isCrouching()) {
                return InteractionResult.FAIL;
            }
            if (!level.isClientSide) {
                tag.putString(TAG_DIMENSION, dimension);
                tag.putString(TAG_POS, position);
                context.getPlayer().displayClientMessage(
                        net.minecraft.network.chat.Component.literal("Ключ привязан к двери"), true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        boolean matches = dimension.equals(tag.getString(TAG_DIMENSION))
                && position.equals(tag.getString(TAG_POS));
        if (!matches) {
            if (!level.isClientSide && context.getPlayer() != null) {
                context.getPlayer().displayClientMessage(
                        net.minecraft.network.chat.Component.literal("Этот ключ не подходит"), true);
            }
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            TZLockedDoorBlock.openForOneMinute(level, clickedPos, state);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
