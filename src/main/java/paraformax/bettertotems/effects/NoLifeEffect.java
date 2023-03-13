package paraformax.bettertotems.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class NoLifeEffect extends StatusEffect {
    public NoLifeEffect() {
        super(StatusEffectCategory.HARMFUL, 0);
        this.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, UUID.randomUUID().toString(), -16, EntityAttributeModifier.Operation.ADDITION);
    }

}
