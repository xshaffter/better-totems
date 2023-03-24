package paraformax.bettertotems.items.totems;

import paraformax.bettertotems.ModEffects;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CursedTotem extends PerfectTotem {
    private final List<StatusEffectInstance> CURSES = List.of(
            new StatusEffectInstance(StatusEffects.BLINDNESS, 10*20, 0),
            new StatusEffectInstance(ModEffects.NO_LIFE, 120*20, 0),
            new StatusEffectInstance(ModEffects.NO_ARMOR, 120*20, 0),
            new StatusEffectInstance(ModEffects.NO_EFFECT, 120*20, 0)
    );
    public CursedTotem() {
        super(List.of(), List.of());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.better-totems.cursed_totem.tooltip.shift"));
            tooltip.add(Text.translatable("item.better-totems.cursed_totem.tooltip.shift2"));
        } else {
            tooltip.add(Text.translatable("item.better-totems.cursed_totem.tooltip"));
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void postRevive(Entity entity) {
        Random rand = new Random();
        int randomCurse = rand.nextInt(CURSES.size());
        LivingEntity user = (LivingEntity) entity;
        user.setHealth(1);
        user.addStatusEffect(CURSES.get(randomCurse));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
    }
}
