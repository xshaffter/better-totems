package paraformax.bettertotems.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class NoArmorEffect extends StatusEffect {
    public NoArmorEffect() {
        super(StatusEffectCategory.HARMFUL, 0);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, UUID.randomUUID().toString(), 0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, UUID.randomUUID().toString(), 0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
