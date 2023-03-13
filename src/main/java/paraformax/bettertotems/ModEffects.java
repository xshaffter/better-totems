package paraformax.bettertotems;

import paraformax.bettertotems.effects.IncreaseLifeEffect;
import paraformax.bettertotems.effects.NoArmorEffect;
import paraformax.bettertotems.effects.NoLifeEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect NO_LIFE = new NoLifeEffect();
    public static final StatusEffect NO_ARMOR = new NoArmorEffect();
    public static final StatusEffect INCREASE_LIFE = new IncreaseLifeEffect();

    private static void registerEffect(String name, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(BetterTotems.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        registerEffect("no_life", NO_LIFE);
        registerEffect("no_armor", NO_ARMOR);
        registerEffect("increase_life", INCREASE_LIFE);
    }
}
