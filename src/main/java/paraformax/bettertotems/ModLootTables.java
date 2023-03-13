package paraformax.bettertotems;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTables {
    private static final Identifier BASTION_TREASURE_LOOT_TABLE_ID = new Identifier("minecraft:chests/bastion_treasure");
    private static final Identifier BASTION_BRIDGE_LOOT_TABLE_ID = new Identifier("minecraft:chests/bastion_bridge");
    private static final Identifier BASTION_GENERIC_LOOT_TABLE_ID = new Identifier("minecraft:chests/bastion_other");
    private static final Identifier BASTION_HOGLIN_STABLE_LOOT_TABLE_ID = new Identifier("minecraft:chests/bastion_hoglin_stable");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(BASTION_TREASURE_LOOT_TABLE_ID)) {
                LootPool newPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.60f))
                        .with(ItemEntry.builder(ModItems.HEART_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)))
                        .build();

                tableBuilder.pool(newPool);
            } else if (id.equals(BASTION_BRIDGE_LOOT_TABLE_ID)) {
                LootPool newPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(ModItems.HEART_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)))
                        .build();

                tableBuilder.pool(newPool);
            } else if (id.equals(BASTION_GENERIC_LOOT_TABLE_ID)) {
                LootPool newPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(ModItems.HEART_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)))
                        .build();

                tableBuilder.pool(newPool);
            } else if (id.equals(BASTION_HOGLIN_STABLE_LOOT_TABLE_ID)) {
                LootPool newPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.25f))
                        .with(ItemEntry.builder(ModItems.HEART_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)))
                        .build();

                tableBuilder.pool(newPool);
            }
        }));
    }
}
