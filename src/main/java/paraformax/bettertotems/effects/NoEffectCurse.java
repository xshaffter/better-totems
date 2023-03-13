package paraformax.bettertotems.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import paraformax.bettertotems.BetterTotems;

import java.util.stream.Collectors;

public class NoEffectCurse extends Curse {
    public NoEffectCurse() {
        super(0);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        var effects = entity.getStatusEffects().stream().map(StatusEffectInstance::getEffectType).toList();
        for (var effect : effects) {
            if (effect.isBeneficial()) {
                entity.removeStatusEffect(effect);
            }
        }
    }
}
