package paraformax.bettertotems.mixin;

import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paraformax.bettertotems.config.ModConfigs;
import paraformax.bettertotems.effects.ModEffects;
import paraformax.bettertotems.effects.curses.Curse;
import paraformax.bettertotems.items.ModItems;
import paraformax.bettertotems.items.totems.CustomTotem;
import paraformax.bettertotems.items.totems.InventoryTotem;
import paraformax.bettertotems.items.totems.NormalTotem;
import paraformax.bettertotems.util.BaseTotem;
import paraformax.bettertotems.util.IEntityDataSaver;
import paraformax.bettertotems.util.IEntityTickCounter;
import paraformax.bettertotems.util.PlayerEntityBridge;

import static paraformax.bettertotems.items.totems.CustomTotem.isCustomTotem;

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
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract double getAttributeValue(EntityAttribute attribute);

    @Shadow
    public abstract int getArmor();

    @Shadow
    public abstract void damageArmor(DamageSource source, float amount);

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

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
        if (this.getWorld().isClient) {
            callback.setReturnValue(false);
            return;
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

    @Inject(at = @At("HEAD"), method = "applyArmorToDamage", cancellable = true)
    public void onApplyDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> callback) {
        float returnedAmount = amount;

        if (source.isOf(DamageTypes.OUT_OF_WORLD)) {
            ItemStack offhand_stack = this.getStackInHand(Hand.OFF_HAND);
            ItemStack main_hand_stack = this.getStackInHand(Hand.MAIN_HAND);

            ItemStack totem = null;
            if (isCustomTotem(offhand_stack.getItem()) || offhand_stack.isOf(Items.TOTEM_OF_UNDYING)) {
                totem = offhand_stack;
            } else if (isCustomTotem(main_hand_stack.getItem()) || main_hand_stack.isOf(Items.TOTEM_OF_UNDYING)) {
                totem = main_hand_stack;
            }
            if (totem != null && totem.isOf(ModItems.INVENTORY_TOTEM)) {
                var totem_item = (InventoryTotem) totem.getItem();
                totem_item.performResurrection(this);
                callback.setReturnValue(0f);
                return;
            }
        }

        if (this.hasStatusEffect(ModEffects.NO_ARMOR)) {
            callback.setReturnValue(returnedAmount);
        } else if (!source.isIn(DamageTypeTags.BYPASSES_ARMOR)) {
            this.damageArmor(source, returnedAmount);
            returnedAmount = DamageUtil.getDamageLeft(returnedAmount, this.getArmor(), (float) this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
        }
        callback.setReturnValue(returnedAmount);
    }

    @Inject(at = @At("HEAD"), method = "attackLivingEntity")
    public void a(LivingEntity target, CallbackInfo ci) {

    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useCustomTotem(DamageSource source, CallbackInfoReturnable<Boolean> callback) {
        //initializes PlayerEntity entity, which is a copy of this cast to Living Entity and then PlayerEntity
        LivingEntityMixin entity = this;
        int totems_used = PlayerEntityBridge.getResurrection(this);
        if (totems_used < 3) {
            if (totems_used == 2) {
                this.sendMessage(Text.literal("The god of resurrection is angry with you"));
                this.sendMessage(Text.literal("Totems now have a 50% chance of working"));
                PlayerEntityBridge.grantAdvancement(this, "gods_enemy");

            }
            return;
        }

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
            boolean resurrect = totem_item.canRevive(source, this);

            if (resurrect) {
                totem_item.performResurrection(this);
                totem.decrement(1);
                totem_item.postRevive(this);
                PlayerEntityBridge.increaseResurrection(this);
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
