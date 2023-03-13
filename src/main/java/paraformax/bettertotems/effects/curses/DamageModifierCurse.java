package paraformax.bettertotems.effects.curses;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class DamageModifierCurse extends DamageModifierStatusEffect {
    @SuppressWarnings("SameParameterValue")
    public DamageModifierCurse(int color, double modifier) {
        super(StatusEffectCategory.HARMFUL, color, modifier);
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
        return this.modifier / amplifier + 1;
    }

}
