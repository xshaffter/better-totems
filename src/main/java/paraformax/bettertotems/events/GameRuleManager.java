package paraformax.bettertotems.events;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameRuleManager {

    public static GameRules.Key<GameRules.IntRule> RESURRECTION_GOD_TOLERANCE;

    public static void registerGamerules() {
        RESURRECTION_GOD_TOLERANCE = GameRuleRegistry.register("resurrectiongodTolerance", GameRules.Category.MOBS, GameRuleFactory.createIntRule(3));
    }
}
