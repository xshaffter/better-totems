package paraformax.bettertotems.events;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import paraformax.bettertotems.BetterTotems;
import paraformax.bettertotems.events.criterion.DummyCriterion;

public class AdvancementManager {

    private static Advancement getAdvancement(MinecraftServer server, final String advancement) {
        return server.getAdvancementLoader().get(new Identifier(BetterTotems.MOD_ID, advancement));
    }

    public static void grantAdvancement(ServerPlayerEntity player, final String advancement) {
        var adv = getAdvancement(player.server, advancement);
        var unobtained = player.getAdvancementTracker().getProgress(adv).getUnobtainedCriteria();
        for (var criterion : unobtained) {
            player.getAdvancementTracker().grantCriterion(adv, criterion);
        }
    }

    public static void registerCriterions() {
        CriterionRegistry.register(new DummyCriterion());
    }

    public static boolean hasAdvancement(ServerPlayerEntity player, final String advancement) {
        var adv = getAdvancement(player.server, advancement);
        var progress = player.getAdvancementTracker().getProgress(adv);
        return progress.isDone();
    }

}
