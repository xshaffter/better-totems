package paraformax.bettertotems.config;

import com.mojang.datafixers.util.Pair;
import paraformax.bettertotems.BetterTotems;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;
    public static boolean MAGIC_MILK;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(BetterTotems.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("key.magicmilk", true), "boolean");
    }

    private static void assignConfigs() {
        MAGIC_MILK = CONFIG.getOrDefault("key.magicmilk", true);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}