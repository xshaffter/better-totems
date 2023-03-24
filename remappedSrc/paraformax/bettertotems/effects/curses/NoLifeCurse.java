package paraformax.bettertotems.effects.curses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.nbt.NbtCompound;
import paraformax.bettertotems.util.LivingEntityBridge;

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
