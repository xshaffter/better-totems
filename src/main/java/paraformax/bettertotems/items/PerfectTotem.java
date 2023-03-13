package paraformax.bettertotems.items;

import paraformax.bettertotems.util.StatModifier;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.List;

public abstract class PerfectTotem extends CustomTotem {

    public PerfectTotem(List<StatusEffectInstance> effects, List<StatusEffectInstance> curses, List<StatModifier> modifiers) {
        super(new Settings(), 100, effects, curses, modifiers);
    }
}
