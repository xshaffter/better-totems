package paraformax.bettertotems.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class InventoryTotem extends PerfectTotem {

    public InventoryTotem() {
        super(List.of(), List.of(), List.of());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.better-totems.inventory_totem.tooltip"));
        tooltip.add(Text.translatable("item.better-totems.inventory_totem.tooltip2"));
    }

    @Override
    public void postRevive(Entity resurrected) {
        super.postRevive(resurrected);
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
                attributeManager.setBaseValue(attributeManager.getBaseValue() - 4);
            }

            player.teleport(dimension, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), player.getYaw(), player.getPitch());
        }
    }
}
