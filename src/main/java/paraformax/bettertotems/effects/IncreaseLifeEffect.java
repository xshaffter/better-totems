package paraformax.bettertotems.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public class IncreaseLifeEffect extends StatusEffect {
    public IncreaseLifeEffect() {
        super(StatusEffectCategory.HARMFUL, 0);
    }


    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        var healthManager = target.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthManager != null) {
            healthManager.setBaseValue(healthManager.getBaseValue() + 2 * (amplifier + 1));
        }

        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
    }

    @Override
    public boolean isInstant() {
        return true;
    }
}
