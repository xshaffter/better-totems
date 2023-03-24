package paraformax.bettertotems.items.totems;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import paraformax.bettertotems.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public abstract class CustomTotem extends Item implements BaseTotem {

    protected final List<StatusEffectInstance> EFFECTS;
    protected final List<StatusEffectInstance> CURSES;
    protected final Multimap<EntityAttribute, EntityAttributeModifier> STAT_MODIFIERS;
    public final int resurrectionProbability;

    public CustomTotem(
            Settings settings,
            int resurrectionProbability,
            List<StatusEffectInstance> effects,
            List<StatusEffectInstance> curses,
            Multimap<EntityAttribute, EntityAttributeModifier> statModifiers
    ) {
        super(settings.rarity(Rarity.EPIC).maxCount(1));
        this.resurrectionProbability = resurrectionProbability;
        EFFECTS = effects;
        CURSES = curses;
        STAT_MODIFIERS = statModifiers;
    }

    public CustomTotem(
            Settings settings,
            int resurrectionProbability,
            List<StatusEffectInstance> effects,
            List<StatusEffectInstance> curses
    ) {
        this(settings, resurrectionProbability, effects, curses, ImmutableMultimap.of());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.OFFHAND) {
            return this.STAT_MODIFIERS;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public final boolean checkProbability() {
        Random rnd = new Random();
        int doResurrection = rnd.nextInt(100) + 1;
        return doResurrection <= resurrectionProbability;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int tickWait = 100;
        if (entity instanceof LivingEntity living) {
            int tickCount = LivingEntityBridge.getTickCounter(living);
            if (living.getOffHandStack() == stack) {
                if (tickCount % tickWait == 0) {
                    this.whileHolding(living);
                    LivingEntityBridge.reset(living);
                }
                LivingEntityBridge.tick(living);
            }
        }
    }

    public static boolean isCustomTotem(Item item) {
        return item instanceof CustomTotem;
    }

    @Override
    public boolean canRevive(DamageSource source, Entity resurrected) {
        if (source.isOutOfWorld()) {
            return false;
        }
        return checkProbability();
    }

    @SuppressWarnings("unused")
    @Override
    public void performResurrection(Entity resurrected) {
        resurrected.world.sendEntityStatus(resurrected, EntityStatuses.USE_TOTEM_OF_UNDYING);
    }

    public void whileHolding(LivingEntity resurrected) {
        for (var effect : EFFECTS) {
            resurrected.addStatusEffect(new StatusEffectInstance(effect));
        }
        for (var effect : CURSES) {
            resurrected.addStatusEffect(new StatusEffectInstance(effect));
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void postRevive(Entity entity) {

    }
}
