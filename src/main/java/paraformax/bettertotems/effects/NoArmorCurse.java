package paraformax.bettertotems.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class NoArmorCurse extends Curse {
    public NoArmorCurse() {
        super(0);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, UUID.randomUUID().toString(), 0.01, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, UUID.randomUUID().toString(), 0.01, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
