package ru.tz.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.tz.TZMod;
import ru.tz.block.TZLockedDoorBlock;
import ru.tz.block.TZSearchBlock;
import ru.tz.item.TZKeyItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class TZRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TZMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TZMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TZMod.MOD_ID);

    public static final RegistryObject<Item> KEY = ITEMS.register("door_key", () -> new TZKeyItem(new Item.Properties().stacksTo(1)));
    private static final List<RegistryObject<Item>> TAB_ITEMS = new ArrayList<>();
    public static final RegistryObject<CreativeModeTab> TZ_TAB = TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(net.minecraft.network.chat.Component.translatable("itemGroup.tz.main"))
                    .icon(() -> KEY.get().getDefaultInstance())
                    .displayItems((parameters, output) -> TAB_ITEMS.forEach(item -> output.accept(item.get())))
                    .build());

    static {
        addDoor("oak", BlockSetType.OAK, Blocks.OAK_DOOR);
        addDoor("spruce", BlockSetType.SPRUCE, Blocks.SPRUCE_DOOR);
        addDoor("birch", BlockSetType.BIRCH, Blocks.BIRCH_DOOR);
        addDoor("jungle", BlockSetType.JUNGLE, Blocks.JUNGLE_DOOR);
        addDoor("acacia", BlockSetType.ACACIA, Blocks.ACACIA_DOOR);
        addDoor("dark_oak", BlockSetType.DARK_OAK, Blocks.DARK_OAK_DOOR);
        addDoor("mangrove", BlockSetType.MANGROVE, Blocks.MANGROVE_DOOR);
        addDoor("cherry", BlockSetType.CHERRY, Blocks.CHERRY_DOOR);
        addDoor("bamboo", BlockSetType.BAMBOO, Blocks.BAMBOO_DOOR);
        addDoor("crimson", BlockSetType.CRIMSON, Blocks.CRIMSON_DOOR);
        addDoor("warped", BlockSetType.WARPED, Blocks.WARPED_DOOR);
        addSearchable("search_chest");
        addSearchable("search_crafting_table");
        addSearchable("search_barrel");
        addSearchable("search_furnace");
        addSearchable("search_smoker");
        addSearchable("search_blast_furnace");
        addSearchable("search_bed");
        addSearchable("search_beehive");
        addSearchable("search_bookshelf");
        addSearchable("search_chiseled_bookshelf");
        TAB_ITEMS.add(KEY);
    }

    private static void addDoor(String name, BlockSetType type, Block vanillaDoor) {
        RegistryObject<Block> block = BLOCKS.register("locked_" + name + "_door",
                () -> new TZLockedDoorBlock(BlockBehaviour.Properties.copy(vanillaDoor), type));
        RegistryObject<Item> item = ITEMS.register("locked_" + name + "_door",
                () -> new BlockItem(block.get(), new Item.Properties()));
        TAB_ITEMS.add(item);
    }

    private static void addSearchable(String name) {
        // Search props are generic blocks, not vanilla block-state copies.
        // Copying beds/furnaces/chests also copies state-dependent callbacks
        // (part/lit/facing etc.) that TZSearchBlock does not define.
        RegistryObject<Block> block = BLOCKS.register(name,
                () -> new TZSearchBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(net.minecraft.world.level.block.SoundType.WOOD)));
        RegistryObject<Item> item = ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        TAB_ITEMS.add(item);
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TABS.register(bus);
    }

    private TZRegistry() {}
}
