package paraformax.bettertotems.mixin;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Shadow;
import paraformax.bettertotems.items.CustomTotem;
import paraformax.bettertotems.util.IEntityDataSaver;
import paraformax.bettertotems.util.StatModified;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

import static paraformax.bettertotems.items.CustomTotem.isCustomTotem;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements IEntityDataSaver {

    @Shadow
    public abstract void sendMessage(Text message);

    @Shadow
    public abstract void sendMessage(Text message, boolean overlay);

    @Shadow
    public abstract void endCombat();

    @Shadow
    protected abstract boolean isBedTooFarAway(BlockPos pos);

    private List<StatModified> onHandUUIDS = List.of();
    private Item lastOffHand = null;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    @Inject(method = "onDisconnect", at = @At(value = "RETURN"))
    public void onDisconnect(CallbackInfo callback) {
        for (var modified : onHandUUIDS) {
            Objects.requireNonNull(this.getAttributeInstance(modified.attribute())).removeModifier(modified.uuid());
        }
    }


    @Inject(method = "playerTick", at = @At(value = "RETURN"))
    public void onSelectSlot(CallbackInfo callback) {
        ItemStack item = this.getStackInHand(Hand.OFF_HAND);
        if (isCustomTotem(item.getItem())) {
            CustomTotem totem = (CustomTotem) item.getItem();
            totem.whileHolding(this);
        }

        if (lastOffHand != item.getItem()) {
            for (var modified : onHandUUIDS) {
                Objects.requireNonNull(this.getAttributeInstance(modified.attribute())).removeModifier(modified.uuid());
            }
            lastOffHand = item.getItem();
            if (isCustomTotem(item.getItem())) {
                CustomTotem totem = (CustomTotem) item.getItem();
                onHandUUIDS = totem.manageStats(this);
            }
        }
    }
}
