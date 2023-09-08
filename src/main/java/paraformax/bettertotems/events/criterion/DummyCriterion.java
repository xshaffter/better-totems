package paraformax.bettertotems.events.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import paraformax.bettertotems.BetterTotems;

public class DummyCriterion implements Criterion<DummyCriterion.Conditions> {
    private static final Identifier ID = new Identifier(BetterTotems.MOD_ID, "dummy");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void beginTrackingCondition(PlayerAdvancementTracker manager, ConditionsContainer conditions) {

    }

    @Override
    public void endTrackingCondition(PlayerAdvancementTracker manager, ConditionsContainer conditions) {

    }

    @Override
    public void endTracking(PlayerAdvancementTracker tracker) {

    }

    @Override
    public Conditions conditionsFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return null;
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate item;

        public Conditions(LootContextPredicate player, ItemPredicate item) {
            super(DummyCriterion.ID, player);
            this.item = item;
        }


        public boolean matches(ItemStack stack) {
            return this.item.test(stack);
        }

        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("item", this.item.toJson());
            return jsonObject;
        }
    }
}
