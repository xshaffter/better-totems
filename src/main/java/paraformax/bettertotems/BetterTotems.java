package paraformax.bettertotems;

import paraformax.bettertotems.config.ModConfigs;
import paraformax.bettertotems.effects.ModEffects;
import paraformax.bettertotems.events.AdvancementManager;
import paraformax.bettertotems.events.GameRuleManager;
import paraformax.bettertotems.events.PlayerRespawnHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paraformax.bettertotems.items.ModItems;

public class BetterTotems implements ModInitializer {
    public static final String MOD_ID = "better-totems";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfigs.registerConfigs();
        ModItems.registerModItems();
        ModEffects.registerEffects();
        ModLootTables.modifyLootTables();
        AdvancementManager.registerCriterions();
        GameRuleManager.registerGamerules();
        ServerPlayerEvents.AFTER_RESPAWN.register(new PlayerRespawnHandler());

        LOGGER.info("BETTER TOTEMS initialized");
    }
}
