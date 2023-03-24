package paraformax.bettertotems;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import paraformax.bettertotems.effects.IncreaseLifeEffect;
import paraformax.bettertotems.effects.curses.NoArmorCurse;
import paraformax.bettertotems.effects.curses.NoEffectCurse;
import paraformax.bettertotems.effects.curses.NoLifeCurse;

public class ModEffects {
    public static final StatusEffect NO_LIFE = new NoLifeCurse();
    public static final StatusEffect NO_ARMOR = new NoArmorCurse();
    public static final StatusEffect INCREASE_LIFE = new IncreaseLifeEffect();
    public static final StatusEffect NO_EFFECT = new NoEffectCurse();

    private static void registerEffect(String name, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(BetterTotems.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        registerEffect("no_life", NO_LIFE);
        registerEffect("no_armor", NO_ARMOR);
        registerEffect("increase_life", INCREASE_LIFE);
        registerEffect("no_effect", NO_EFFECT);
    }
}
