package paraformax.bettertotems.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import paraformax.bettertotems.BetterTotems;
import paraformax.bettertotems.blocks.custom.RespawnAltar;

public class ModBlocks {
    public static final Block RESPAWN_ALTAR = new RespawnAltar();

    private static void registerBlock(String name, Block block, final BlockItem blockItem) {
        Registry.register(Registries.BLOCK, new Identifier(BetterTotems.MOD_ID, name), block);
        registerBlockItem(name, blockItem);
    }

    private static void registerBlockItem(final String name, final BlockItem blockItem) {
        Registry.register(Registries.ITEM, new Identifier(BetterTotems.MOD_ID, name), blockItem);
    }

    private static void registerBlockAuto(final String name, final Block block) {
        registerBlock(name, block, new BlockItem(block, new FabricItemSettings().fireproof().maxCount(64)));
    }

    private static void addItemsToGroups() {
        addItemToGroup(new ItemStack(RESPAWN_ALTAR).getItem());
    }

    private static void addItemToGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(item));
    }

    public static void registerModBlocks() {
        registerBlockAuto("respawn_altar", RESPAWN_ALTAR);
        addItemsToGroups();
    }
}
