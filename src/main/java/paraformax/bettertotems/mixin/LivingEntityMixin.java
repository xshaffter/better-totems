package paraformax.bettertotems.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paraformax.bettertotems.ModEffects;
import paraformax.bettertotems.config.ModConfigs;
import paraformax.bettertotems.effects.curses.Curse;
import paraformax.bettertotems.util.BaseTotem;
import paraformax.bettertotems.items.CustomTotem;
import paraformax.bettertotems.items.NormalTotem;
import paraformax.bettertotems.util.IEntityDataSaver;
import paraformax.bettertotems.util.IEntityTickCounter;

import static paraformax.bettertotems.items.CustomTotem.isCustomTotem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IEntityDataSaver, IEntityTickCounter {

    private int tickCount = 0;
    private NbtCompound persistentData;

    @Override
    public int getTickCounter() {
        return tickCount;
    }

    @Override
    public void setTickCounter(int count) {
        tickCount = count;
    }

    @SuppressWarnings("unused")
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStackInHand(Hand hand);

    @Shadow
    public abstract void writeCustomDataToNbt(NbtCompound nbt);

    @Shadow
    public abstract boolean canBeRiddenInWater();


    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract void heal(float amount);

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    public abstract void setHealth(float health);

    @Inject(at = @At("HEAD"), method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", cancellable = true)
    public void onAddStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callback) {
        //noinspection ConstantConditions
        if (!this.hasStatusEffect(ModEffects.NO_EFFECT) && ((Entity) this) instanceof LivingEntity living) {
            callback.setReturnValue(living.addStatusEffect(effect, null));
            return;
        }
        callback.setReturnValue(false);
    }

    @Inject(at = @At("RETURN"), method = "tick")
    public void onTick(CallbackInfo ci) {
        if (this.getHealth() > this.getMaxHealth()) {
            this.setHealth(this.getMaxHealth());
        }
    }

    @Inject(at = @At("HEAD"), method = "clearStatusEffects", cancellable = true)
    public void onClearStatusEffects(CallbackInfoReturnable<Boolean> callback) {
        if (this.world.isClient) {
            callback.setReturnValue(false);
        }

        boolean bl = false;
        if (((Entity) this) instanceof LivingEntity living) {
            var effects = living.getStatusEffects().stream().map(StatusEffectInstance::getEffectType).toList();
            boolean byPass = (ModConfigs.MAGIC_MILK) || (((Entity) this) instanceof ServerPlayerEntity player && player.getAbilities().creativeMode);
            effects = effects.stream().filter(effect -> byPass || !(effect instanceof Curse)).toList();
            for (var effect : effects) {
                living.removeStatusEffect(effect);
                bl = true;
            }
        }
        //noinspection ConstantConditions
        callback.setReturnValue(bl);
    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useCustomTotem(DamageSource source, CallbackInfoReturnable<Boolean> callback) {
        //initializes PlayerEntity entity, which is a copy of this cast to Living Entity and then PlayerEntity
        LivingEntityMixin entity = this;

        //ItemStack object that is set to the offhand item that entity is carrying
        ItemStack offhand_stack = entity.getStackInHand(Hand.OFF_HAND);

        ItemStack main_hand_stack = entity.getStackInHand(Hand.MAIN_HAND);

        ItemStack totem = null;
        if (isCustomTotem(offhand_stack.getItem()) || offhand_stack.isOf(Items.TOTEM_OF_UNDYING)) {
            totem = offhand_stack;
        } else if (isCustomTotem(main_hand_stack.getItem()) || main_hand_stack.isOf(Items.TOTEM_OF_UNDYING)) {
            totem = main_hand_stack;
        }

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if (totem != null) {
            BaseTotem totem_item;
            if (isCustomTotem(totem.getItem())) {
                totem_item = (CustomTotem) totem.getItem();
            } else {
                totem_item = new NormalTotem();
            }
            boolean resurrect = totem_item.performResurrection(source, this);

            if (resurrect) {
                totem.decrement(1);
                totem_item.postRevive(this);
                this.world.sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);
            }

            callback.setReturnValue(resurrect);
            return;
        }
        callback.setReturnValue(false);

    }

    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    protected void writeNbt(NbtCompound nbt, CallbackInfo info) {
        if (persistentData != null) {
            nbt.put("bettertotems.data", persistentData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    protected void injectedReadNBT(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("bettertotems.data", NbtElement.COMPOUND_TYPE)) {
            persistentData = nbt.getCompound("bettertotems.data");
        }
    }
}
