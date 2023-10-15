package paraformax.bettertotems.items.totems;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ChorusTotem extends PerfectTotem {

    public ChorusTotem() {
        super(List.of(), List.of(), ImmutableMultimap.of());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.better-totems.chorus_totem.tooltip"));
    }

    @SuppressWarnings("unused")
    @Override
    public void performResurrection(Entity entity) {
        LivingEntity resurrected = (LivingEntity) entity;
        World world = resurrected.getWorld();
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            double centerX = resurrected.getX();
            double centerY = resurrected.getY();
            double centerZ = resurrected.getZ();
            double tpRadius = 64;

            double worldBottom = world.getBottomY();
            double maxLogicalHeight = worldBottom + serverWorld.getLogicalHeight() - 1;

            for (int i = 0; i < 16; ++i) {
                double tpVerticalRadius = resurrected.getRandom().nextInt(16) - 8;

                double tpX = centerX + (resurrected.getRandom().nextDouble() - 0.5) * tpRadius;
                double tpY = MathHelper.clamp(centerY + tpVerticalRadius, worldBottom, maxLogicalHeight);
                double tpZ = centerZ + (resurrected.getRandom().nextDouble() - 0.5) * tpRadius;

                if (resurrected.hasVehicle()) {
                    resurrected.stopRiding();
                }

                Vec3d vec3d = resurrected.getPos();
                if (resurrected.teleport(tpX, tpY, tpZ, true)) {
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(resurrected));
                    SoundEvent soundEvent = resurrected instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound(null, centerX, centerY, centerZ, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    resurrected.playSound(soundEvent, 1.0F, 1.0F);
                    break;
                }
            }

            if (resurrected instanceof PlayerEntity) {
                ((PlayerEntity) resurrected).getItemCooldownManager().set(this, 20);
            }
        }

        resurrected.setHealth(1.0f);
        resurrected.clearStatusEffects();
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));
        super.performResurrection(entity);
    }
}
