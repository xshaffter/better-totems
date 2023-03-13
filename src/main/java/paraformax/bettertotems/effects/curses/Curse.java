package paraformax.bettertotems.effects.curses;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class Curse extends StatusEffect {
    @SuppressWarnings("SameParameterValue")
    protected Curse(int color) {
        super(StatusEffectCategory.HARMFUL, color);
    }
}
