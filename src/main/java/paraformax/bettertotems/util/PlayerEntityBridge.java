package paraformax.bettertotems.util;

import net.minecraft.advancement.Advancement;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import paraformax.bettertotems.BetterTotems;

public class PlayerEntityBridge {
    public static void increaseResurrection(Entity living) {
        if (living instanceof ServerPlayerEntity player) {
            player.increaseStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING), 1);
        }
    }

    public static int getResurrection(Entity living) {
        if (living instanceof ServerPlayerEntity player) {
            return player.getStatHandler().getStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
        }
        return 0;
    }

    private static Advancement getAdvancement(MinecraftServer server, final String advancement) {
        return server.getAdvancementLoader().get(new Identifier(BetterTotems.MOD_ID, advancement));
    }

    public static void grantAdvancement(Entity entity, final String advancement) {
        if (advancement.isEmpty()) {
            return;
        }
        if (entity instanceof ServerPlayerEntity player) {
            var adv = getAdvancement(player.server, advancement);
            var unobtained = player.getAdvancementTracker().getProgress(adv).getUnobtainedCriteria();
            for (var criterion : unobtained) {
                player.getAdvancementTracker().grantCriterion(adv, criterion);
            }
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean hasAdvancement(Entity entity, final String advancement) {
        if (advancement.isEmpty()) {
            return true;
        }
        if (entity instanceof ServerPlayerEntity player) {
            var adv = getAdvancement(player.server, advancement);
            var progress = player.getAdvancementTracker().getProgress(adv);
            return progress.isDone();
        }
        return false;
    }

}
