package paraformax.bettertotems.items;

import paraformax.bettertotems.util.StatModified;
import paraformax.bettertotems.util.StatModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.Random;

public abstract class CustomTotem extends Item implements BaseTotem {

    protected List<StatusEffectInstance> EFFECTS;
    protected List<StatusEffectInstance> CURSES;
    protected List<StatModifier> STAT_MODIFIERS;
    public int resurrectionProbability;

    public CustomTotem(
            Settings settings,
            int resurrectionProbability,
            List<StatusEffectInstance> effects,
            List<StatusEffectInstance> curses,
            List<StatModifier> statModifiers
    ) {
        super(settings.rarity(Rarity.EPIC).maxCount(1));
        this.resurrectionProbability = resurrectionProbability;
        EFFECTS = effects;
        CURSES = curses;
        STAT_MODIFIERS = statModifiers;
    }

    public boolean checkProbability() {
        Random rnd = new Random();
        int doResurrection = rnd.nextInt(101);
        return doResurrection <= resurrectionProbability;
    }

    public static boolean isCustomTotem(Item item) {
        return item instanceof CustomTotem;
    }

    public boolean performResurrection(DamageSource source, Entity resurrected) {
        if (source.isOutOfWorld()){
            return false;
        }
        return checkProbability();
    }

    public void whileHolding(Entity resurrected) {
        for (var effect : EFFECTS) {
            ((LivingEntity) resurrected).addStatusEffect(effect);
        }
        for (var effect : CURSES) {
            ((LivingEntity) resurrected).addStatusEffect(effect);
        }
    }

    public List<StatModified> manageStats(LivingEntity entity) {
        List<StatModified> uuids = new java.util.ArrayList<>(List.of());

        for (StatModifier modifier : STAT_MODIFIERS) {
            var attributeManager = entity.getAttributeInstance(modifier.attribute());
            assert attributeManager != null;
            attributeManager.addPersistentModifier(modifier.modifier());
            uuids.add(new StatModified(modifier.attribute(), modifier.modifier().getId()));
        }

        return uuids;
    }

    public void postRevive(Entity entity) {

    }
}
