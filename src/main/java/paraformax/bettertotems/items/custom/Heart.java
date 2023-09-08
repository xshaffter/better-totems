package paraformax.bettertotems.items.custom;

import paraformax.bettertotems.effects.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.UseAction;

public class Heart extends Item {
    public Heart() {
        super(new Settings()
                .maxCount(8)
                .rarity(Rarity.EPIC)
                .food(new FoodComponent.Builder()
                        .hunger(0)
                        .alwaysEdible()
                        .statusEffect(new StatusEffectInstance(ModEffects.INCREASE_LIFE, 0, 0, false, false), 1)
                        .snack()
                        .build()
                )
        );
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return super.getUseAction(stack);
    }
}
