package ru.tz.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;\nimport net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.tz.TZMod;
import ru.tz.block.TZLockedDoorBlock;
import ru.tz.block.TZSearchBlock;
import ru.tz.block.TZSearchBedBlock;
import ru.tz.block.TZSearchBedBlockEntity;
import ru.tz.block.TZSearchChestBlock;
import ru.tz.block.TZSearchChestBlockEntity;
import ru.tz.item.TZKeyItem;

import java.util.ArrayList;
import java.util.List;

public final class TZRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TZMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TZMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TZMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TZMod.MOD_ID);

    public static final RegistryObject<Item> KEY =
            ITEMS.register("door_key", () -> new TZKeyItem(new Item.Properties().stacksTo(1)));
    private static final List<RegistryObject<Item>> TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> TZ_TAB = TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(net.minecraft.network.chat.Component.translatable("itemGroup.tz.main"))
                    .icon(() -> KEY.get().getDefaultInstance())
                    .displayItems((parameters, output) -> TAB_ITEMS.forEach(item -> output.accept(item.get())))
                    .build());

    // These two blocks use the real vanilla block-entity renderers. Their geometry
    // and textures are therefore the same as the vanilla chest and red bed.
    public static final RegistryObject<Block> SEARCH_CHEST = BLOCKS.register("search_chest",
            () -> new TZSearchChestBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD),
                    () -> TZRegistry.SEARCH_CHEST_ENTITY.get()));
    public static final RegistryObject<BlockEntityType<TZSearchChestBlockEntity>> SEARCH_CHEST_ENTITY =
            BLOCK_ENTITIES.register("search_chest", () ->
                    BlockEntityType.Builder.of(TZSearchChestBlockEntity::new, SEARCH_CHEST.get()).build(null));

    public static final RegistryObject<Block> SEARCH_BED = BLOCKS.register("search_bed",
            () -> new TZSearchBedBlock(BlockBehaviour.Properties.of().strength(0.2F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<BlockEntityType<TZSearchBedBlockEntity>> SEARCH_BED_ENTITY =
            BLOCK_ENTITIES.register("search_bed", () ->
                    BlockEntityType.Builder.of(TZSearchBedBlockEntity::new, SEARCH_BED.get()).build(null));

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

        addSearchable("search_crafting_table", SoundType.WOOD);
        addSearchable("search_barrel", SoundType.WOOD);
        addSearchable("search_furnace", SoundType.STONE);
        addSearchable("search_smoker", SoundType.STONE);
        addSearchable("search_blast_furnace", SoundType.STONE);
        addSearchable("search_beehive", SoundType.WOOD);
        addSearchable("search_bookshelf", SoundType.WOOD);
        addSearchable("search_chiseled_bookshelf", SoundType.WOOD);\n\n        RegistryObject<Block> brewingTable = BLOCKS.register("brewing_table",\n                () -> new Block(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));\n        addBlockItem("brewing_table", brewingTable);\n        RegistryObject<Block> oven = BLOCKS.register("oven",\n                () -> new SmokerBlock(BlockBehaviour.Properties.copy(Blocks.SMOKER)));\n        addBlockItem("oven", oven);

        addBlockItem("search_chest", SEARCH_CHEST);
        addBlockItem("search_bed", SEARCH_BED);
        TAB_ITEMS.add(KEY);
    }

    private static void addDoor(String name, BlockSetType type, Block vanillaDoor) {
        RegistryObject<Block> block = BLOCKS.register("locked_" + name + "_door",
                () -> new TZLockedDoorBlock(BlockBehaviour.Properties.copy(vanillaDoor), type));
        RegistryObject<Item> item = ITEMS.register("locked_" + name + "_door",
                () -> new BlockItem(block.get(), new Item.Properties()));
        TAB_ITEMS.add(item);
    }

    private static void addSearchable(String name, SoundType sound) {
        RegistryObject<Block> block = BLOCKS.register(name,
                () -> new TZSearchBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(sound)));
        addBlockItem(name, block);
    }

    private static void addBlockItem(String name, RegistryObject<Block> block) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        TAB_ITEMS.add(item);
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        TABS.register(bus);
    }

    private TZRegistry() {}
}
