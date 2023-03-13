package paraformax.bettertotems.items;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import paraformax.bettertotems.util.StatModifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TankTotem extends PerfectTotem {

    public TankTotem() {
        super(Arrays.asList(
                new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0),
                new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 1)
        ), Arrays.asList(
                new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 0),
                new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 0),
                new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0)
        ), ImmutableMultimap.of(
                EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("tank_totem_health", 4, EntityAttributeModifier.Operation.ADDITION)
        ));

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.better-totems.tank_totem.tooltip.shift"));
            tooltip.add(Text.translatable("item.better-totems.tank_totem.tooltip.shift2"));
        } else {
            tooltip.add(Text.translatable("item.better-totems.tank_totem.tooltip"));
        }
    }

    @SuppressWarnings("unused")
    @Override
    public boolean performResurrection(DamageSource source, Entity entity) {
        LivingEntity resurrected = (LivingEntity) entity;

        resurrected.setHealth(1.0f);
        resurrected.clearStatusEffects();
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        return super.performResurrection(source, entity);
    }
}
