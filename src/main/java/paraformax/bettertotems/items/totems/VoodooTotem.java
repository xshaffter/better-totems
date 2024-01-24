package paraformax.bettertotems.items.totems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paraformax.bettertotems.blocks.blockstates.BooleanProperty;
import paraformax.bettertotems.util.BaseTotem;
import paraformax.bettertotems.util.LivingEntityBridge;

import java.util.List;
import java.util.Objects;

import static paraformax.bettertotems.blocks.custom.RespawnAltar.ON_USE;

public class VoodooTotem implements BaseTotem {
    @Override
    public boolean checkProbability() {
        return false;
    }

    @Override
    public boolean canRevive(DamageSource source, Entity resurrected) {
        var nbtData = LivingEntityBridge.getPersistentData(resurrected);
        return nbtData.contains("altar");
    }

    @Override
    public void performResurrection(Entity resurrected) {
        if (resurrected instanceof ServerPlayerEntity player) {
            player.clearStatusEffects();
            player.setFireTicks(0);
            player.setOnFire(false);
            player.setHealth(1);
            player.getHungerManager().setFoodLevel(1);

            var nbtData = LivingEntityBridge.getPersistentData(player);
            var altar = nbtData.getCompound("altar");

            BlockPos spawnPoint;
            ServerWorld dimension = player.server.getOverworld();

            player.getInventory().clear();
            BlockPos altarPosition = new BlockPos(altar.getInt("x"), altar.getInt("y"), altar.getInt("z"));
            dimension.setBlockState(altarPosition, player.getServerWorld().getBlockState(altarPosition).with(ON_USE, BooleanProperty.NO));
            nbtData.remove("altar");
            player.sendMessage(Text.literal("altar destruido"), false);


            spawnPoint = player.getSpawnPointPosition();
            if (spawnPoint == null) {
                spawnPoint = Objects.requireNonNull(player.server.getWorld(World.OVERWORLD)).getSpawnPos();
            }


            player.teleport(dimension, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), player.getYaw(), player.getPitch());
        }
    }

    @Override
    public void postRevive(Entity entity) {
        entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.AMBIENT);
    }
}
