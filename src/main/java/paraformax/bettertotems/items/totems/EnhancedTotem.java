package paraformax.bettertotems.items.totems;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

    @SuppressWarnings("unused")
    @Override
    public void performResurrection(Entity resurrected) {
        var entity = ((LivingEntity) resurrected);
        entity.setHealth(entity.getMaxHealth());
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
