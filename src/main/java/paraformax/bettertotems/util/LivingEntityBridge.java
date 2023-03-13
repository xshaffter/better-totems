package paraformax.bettertotems.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import paraformax.bettertotems.mixin.LivingEntityMixin;

public class LivingEntityBridge {

    public static NbtCompound getPersistentData(Entity entity) {
        var living = (IEntityDataSaver) entity;
        return living.getPersistentData();
    }
}
