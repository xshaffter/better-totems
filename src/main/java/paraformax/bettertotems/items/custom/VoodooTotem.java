package paraformax.bettertotems.items.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import paraformax.bettertotems.blocks.ModBlocks;
import paraformax.bettertotems.blocks.blockstates.BooleanProperty;
import paraformax.bettertotems.util.LivingEntityBridge;

import static paraformax.bettertotems.blocks.custom.RespawnAltar.ON_USE;

public class VoodooTotem extends Item {
    public VoodooTotem() {
        super(new FabricItemSettings()
                .maxCount(64)
                .fireproof()
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var position = context.getBlockPos();
        if (context.getWorld().isClient) {
            return ActionResult.PASS;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
        var blockState = context.getWorld().getBlockState(position);

        assert player != null;
        if (blockState.isOf(ModBlocks.RESPAWN_ALTAR)) {
            var playerNbt = LivingEntityBridge.getPersistentData(player);
            if (blockState.get(ON_USE).equals(BooleanProperty.YES)) {
                player.sendMessage(Text.translatable("block.better-totems.respawn_altar.error.already_on_use"));
                return ActionResult.SUCCESS;
            }
            if (playerNbt.contains("altar")) {
                player.sendMessage(Text.translatable("block.better-totems.respawn_altar.error.already_has_one"));
                return ActionResult.SUCCESS;
            }

            context.getStack().decrement(1);
            var compound = new NbtCompound();

            compound.putInt("x", position.getX());
            compound.putInt("y", position.getY());
            compound.putInt("z", position.getZ());

            playerNbt.put("altar", compound);

            context.getWorld().setBlockState(position, blockState.with(ON_USE, BooleanProperty.YES));
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.AMBIENT);
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }
}
