package paraformax.bettertotems.items.totems;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import paraformax.bettertotems.BetterTotems;

import java.util.List;
import java.util.Objects;

public class InventoryTotem extends PerfectTotem {

    public InventoryTotem() {
        super(List.of(), List.of());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.better-totems.inventory_totem.tooltip"));
        tooltip.add(Text.translatable("item.better-totems.inventory_totem.tooltip2"));
    }

    @Override
    public boolean canRevive(DamageSource source, Entity resurrected) {
        return true;
    }

    @Override
    public void performResurrection(Entity resurrected) {
        if (resurrected.getWorld().isClient) {
            return;
        }
        if (resurrected instanceof ServerPlayerEntity player) {
            player.clearStatusEffects();
            player.setFireTicks(0);
            player.setOnFire(false);
            player.setHealth(player.getMaxHealth());

            BlockPos spawnPoint;
            ServerWorld dimension = player.server.getOverworld();

            spawnPoint = player.getSpawnPointPosition();
            if (spawnPoint == null) {
                spawnPoint = Objects.requireNonNull(player.server.getWorld(World.OVERWORLD)).getSpawnPos();
            }

            var attributeManager = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attributeManager != null) {
                player.sendMessage(Text.literal("The ").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))).append(Text.literal("god of resurrection").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true))).append(Text.literal(" is angry with you").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));
                player.sendMessage(Text.literal("You've lost 2 maximum hearts").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))));
                attributeManager.setBaseValue(attributeManager.getBaseValue() - 4);
            }

            player.teleport(dimension, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), player.getYaw(), player.getPitch());
        }
    }
    @SuppressWarnings("unused")
    @Override
    public void postRevive(Entity resurrected) {
        resurrected.getWorld().sendEntityStatus(resurrected, EntityStatuses.USE_TOTEM_OF_UNDYING);
    }
}
