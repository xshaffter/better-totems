package paraformax.bettertotems.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class LivingEntityBridge {

    public static NbtCompound getPersistentData(Entity entity) {
        var living = (IEntityDataSaver) entity;
        return living.better_totems$getPersistentData();
    }

    public static int getTickCounter(LivingEntity living) {
        var counter = (IEntityTickCounter) living;
        return counter.better_totems$getTickCounter();
    }

    public static void tick(LivingEntity living) {
        var counter = (IEntityTickCounter) living;
        counter.better_totems$setTickCounter(getTickCounter(living) + 1);
    }

    public static void reset(LivingEntity living) {
        var counter = (IEntityTickCounter) living;
        counter.better_totems$setTickCounter(0);
    }
}
