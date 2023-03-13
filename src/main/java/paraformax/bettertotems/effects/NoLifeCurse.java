package paraformax.bettertotems.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import paraformax.bettertotems.BetterTotems;
import paraformax.bettertotems.mixin.LivingEntityMixin;
import paraformax.bettertotems.util.LivingEntityBridge;

import java.util.UUID;

public class NoLifeCurse extends Curse {
    public NoLifeCurse() {
        super(0);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        var maxHealthManager = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealthManager != null) {
            int previousValue = (int) maxHealthManager.getBaseValue();
            entity.setHealth(4);
            maxHealthManager.setBaseValue(4);
            var persistentData = LivingEntityBridge.getPersistentData(entity);
            persistentData.putInt("maxLife", previousValue);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        var maxHealthManager = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        int previousValue = LivingEntityBridge.getPersistentData(entity).getInt("maxLife");
        if (maxHealthManager != null) {
            maxHealthManager.setBaseValue(previousValue);
        }
    }
}
