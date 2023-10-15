package paraformax.bettertotems.items.totems;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnhancedTotem extends CustomTotem {

    public EnhancedTotem() {
        super(new Item.Settings(), 80, List.of(), List.of());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.better-totems.enhanced_totem.tooltip.shift"));
        } else {
            tooltip.add(Text.translatable("item.better-totems.enhanced_totem.tooltip"));
        }
    }

    @Override
    public void postRevive(Entity entity) {
        super.postRevive(entity);
        var living = ((LivingEntity) entity);
        living.setHealth(living.getMaxHealth());
        living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
    }
}
