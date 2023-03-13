package paraformax.bettertotems.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class Curse extends StatusEffect {
    protected Curse(int color) {
        super(StatusEffectCategory.HARMFUL, color);
    }
}
