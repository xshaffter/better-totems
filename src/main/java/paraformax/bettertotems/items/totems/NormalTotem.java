package paraformax.bettertotems.items.totems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import paraformax.bettertotems.util.BaseTotem;

import java.util.Random;

public class NormalTotem implements BaseTotem {
    public final int resurrectionProbability;

    public NormalTotem() {
        this.resurrectionProbability = 50;
    }

    public boolean checkProbability() {
        Random rnd = new Random();
        int doResurrection = rnd.nextInt(101);
        return doResurrection <= resurrectionProbability;
    }

    @Override
    public boolean canRevive(DamageSource source, Entity resurrected) {
        return checkProbability();
    }

    @SuppressWarnings("unused")
    public void performResurrection(Entity resurrected) {
        resurrected.getWorld().sendEntityStatus(resurrected, EntityStatuses.USE_TOTEM_OF_UNDYING);
    }


    @SuppressWarnings("unused")
    public void postRevive(Entity entity) {
        if (entity instanceof LivingEntity living) {
            living.setHealth(1.0f);
            living.clearStatusEffects();
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        }
    }
}
