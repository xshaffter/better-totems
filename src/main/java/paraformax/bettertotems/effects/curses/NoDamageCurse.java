package paraformax.bettertotems.effects.curses;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

public class NoDamageCurse extends DamageModifierCurse {
    public NoDamageCurse() {
        super(0, 0.5);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "123E3B4A-124E-49AF-6C6B-9971489B5BA7", 0.0, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    }

}
