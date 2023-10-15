package paraformax.bettertotems.items.totems;

import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import paraformax.bettertotems.effects.ModEffects;
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
    private final List<StatusEffectInstance> ON_DEATH_CURSES = List.of(
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
        int randomCurse = rand.nextInt(ON_DEATH_CURSES.size());
        LivingEntity user = (LivingEntity) entity;
        user.setHealth(1);

        entity.sendMessage(Text.literal("The ").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))).append(Text.literal("god of resurrection").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true))).append(Text.literal(" laughs on you").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));
        entity.sendMessage(Text.literal("You have been coursed").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))));
        user.addStatusEffect(ON_DEATH_CURSES.get(randomCurse));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
    }
}
