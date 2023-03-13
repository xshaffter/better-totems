package paraformax.bettertotems;

import paraformax.bettertotems.items.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item ENHANCED_TOTEM = new EnhancedTotem();
    public static final Item INVENTORY_TOTEM = new InventoryTotem();
    public static final Item TANK_TOTEM = new TankTotem();
    public static final Item CURSED_TOTEM = new CursedTotem();
    public static final Item TOTEM_FRAGMENT = new TotemFragment();
    public static final Item HEART = new Heart();
    public static final Item HEART_FRAGMENT = new HeartFragment();

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(BetterTotems.MOD_ID, name), item);
    }

    private static void addItemsToGroups() {
        addItemToGroup(ItemGroups.COMBAT, ENHANCED_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, INVENTORY_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, TANK_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, CURSED_TOTEM);
        addItemToGroup(ItemGroups.INGREDIENTS, TOTEM_FRAGMENT);
        addItemToGroup(ItemGroups.INGREDIENTS, HEART_FRAGMENT);
        addItemToGroup(ItemGroups.FOOD_AND_DRINK, HEART);
    }

    private static void addItemToGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        registerItem("enhanced_totem", ENHANCED_TOTEM);
        registerItem("inventory_totem", INVENTORY_TOTEM);
        registerItem("tank_totem", TANK_TOTEM);
        registerItem("cursed_totem", CURSED_TOTEM);
        registerItem("totem_fragment", TOTEM_FRAGMENT);
        registerItem("heart", HEART);
        registerItem("heart_fragment", HEART_FRAGMENT);
        addItemsToGroups();
    }
}
