package paraformax.bettertotems.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import paraformax.bettertotems.util.StatModifier;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.List;

public abstract class PerfectTotem extends CustomTotem {

    public PerfectTotem(List<StatusEffectInstance> effects, List<StatusEffectInstance> curses, Multimap<EntityAttribute, EntityAttributeModifier> modifiers) {
        super(new Settings(), 100, effects, curses, modifiers);
    }
    public PerfectTotem(List<StatusEffectInstance> effects, List<StatusEffectInstance> curses) {
        super(new Settings(), 100, effects, curses);
    }
}
