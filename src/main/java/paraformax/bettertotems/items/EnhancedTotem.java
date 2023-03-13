package paraformax.bettertotems.items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnhancedTotem extends CustomTotem {

    public EnhancedTotem() {
        super(new Item.Settings(), 80, List.of(), List.of(), List.of());
    }

    @Override
    public boolean performResurrection(DamageSource source, Entity resurrected) {
        boolean resurrect = checkProbability();
        if (resurrect) {
            var entity = ((LivingEntity) resurrected);
            entity.setHealth(entity.getMaxHealth());
        }

        return resurrect;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.better-totems.enhanced_totem.tooltip.shift"));
        } else {
            tooltip.add(Text.translatable("item.better-totems.enhanced_totem.tooltip"));
        }
    }
}
