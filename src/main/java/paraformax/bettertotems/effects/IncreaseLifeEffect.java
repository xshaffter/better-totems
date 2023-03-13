package paraformax.bettertotems.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class IncreaseLifeEffect extends StatusEffect {
    public IncreaseLifeEffect() {
        super(StatusEffectCategory.HARMFUL, 0);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        var healthManager = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthManager != null) {
            healthManager.setBaseValue(healthManager.getBaseValue() + 2);
        }

        super.onApplied(entity, attributes, amplifier);
    }
}
