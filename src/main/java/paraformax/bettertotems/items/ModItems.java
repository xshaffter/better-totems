package paraformax.bettertotems.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import paraformax.bettertotems.BetterTotems;
import paraformax.bettertotems.items.custom.Heart;
import paraformax.bettertotems.items.custom.HeartFragment;
import paraformax.bettertotems.items.custom.VoodooTotem;
import paraformax.bettertotems.items.custom.TotemFragment;
import paraformax.bettertotems.items.totems.*;

public class ModItems {
    public static final Item ENHANCED_TOTEM = new EnhancedTotem();
    public static final Item INVENTORY_TOTEM = new InventoryTotem();
    public static final Item TANK_TOTEM = new TankTotem();
    public static final Item CURSED_TOTEM = new CursedTotem();
    public static final Item CHORUS_TOTEM = new ChorusTotem();
    public static final Item TOTEM_FRAGMENT = new TotemFragment();
    public static final Item HEART = new Heart();
    public static final Item HEART_FRAGMENT = new HeartFragment();
    public static final Item VOODOO_TOTEM = new VoodooTotem();

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(BetterTotems.MOD_ID, name), item);
    }

    private static void addItemsToGroups() {
        addItemToGroup(ItemGroups.COMBAT, ENHANCED_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, INVENTORY_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, TANK_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, CURSED_TOTEM);
        addItemToGroup(ItemGroups.COMBAT, CHORUS_TOTEM);
        addItemToGroup(ItemGroups.INGREDIENTS, TOTEM_FRAGMENT);
        addItemToGroup(ItemGroups.INGREDIENTS, HEART_FRAGMENT);
        addItemToGroup(ItemGroups.INGREDIENTS, VOODOO_TOTEM);
        addItemToGroup(ItemGroups.FOOD_AND_DRINK, HEART);
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void addItemToGroup(RegistryKey<ItemGroup> group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAfter(Items.TOTEM_OF_UNDYING, item));
    }

    public static void registerModItems() {
        registerItem("enhanced_totem", ENHANCED_TOTEM);
        registerItem("inventory_totem", INVENTORY_TOTEM);
        registerItem("tank_totem", TANK_TOTEM);
        registerItem("cursed_totem", CURSED_TOTEM);
        registerItem("chorus_totem", CHORUS_TOTEM);
        registerItem("totem_fragment", TOTEM_FRAGMENT);
        registerItem("heart", HEART);
        registerItem("heart_fragment", HEART_FRAGMENT);
        registerItem("voodoo_totem", VOODOO_TOTEM);
        addItemsToGroups();
    }
}
