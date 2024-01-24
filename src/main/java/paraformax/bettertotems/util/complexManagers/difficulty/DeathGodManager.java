package paraformax.bettertotems.util.complexManagers.difficulty;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import paraformax.bettertotems.util.LivingEntityBridge;
import paraformax.bettertotems.util.PlayerEntityBridge;
import paraformax.bettertotems.util.complexManagers.difficulty.states.BasicState;

public class DeathGodManager {
    private static final BasicState[] states = {
            new BasicState(0.33, "", "", (player) -> {
                player.sendMessage(Text.literal("The ")
                        .setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))
                        .append(Text.literal("god of death").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true)))
                        .append(Text.literal(" is watching").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.AMBIENT);
            }),
            new BasicState(0.66, "", "", (player) -> {
                player.sendMessage(Text.literal("The ")
                        .setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))
                        .append(Text.literal("god of death").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true)))
                        .append(Text.literal(" is getting angry").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.AMBIENT);
            }),
            new BasicState(1, "gods_enemy", "", (player) -> {
                player.sendMessage(Text.literal("The ")
                        .setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))
                        .append(Text.literal("god of death").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true)))
                        .append(Text.literal(" is angry with you").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));

                player.sendMessage(Text.literal("Totems now have a 50% chance of working").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))));
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.AMBIENT);
            }),
            new BasicState(5, "gods_hatred", "gods_enemy", (player -> {
                player.sendMessage(Text.literal("The ")
                        .setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))
                        .append(Text.literal("god of death").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true)))
                        .append(Text.literal(" is furious").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));
                player.sendMessage(Text.literal("You won't naturally regenerate life").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))));
                LivingEntityBridge.getPersistentData(player).putBoolean("disableHealing", true);
            })),
            new BasicState(10, "gods_rage", "gods_hatred", (player -> {
                player.sendMessage(Text.literal("The ")
                        .setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))
                        .append(Text.literal("god of death").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN)).withUnderline(true)))
                        .append(Text.literal(" is furious").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)))));
                player.sendMessage(Text.literal("You won't naturally respawn").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))));

            }))
    };

    private static int loadLastStateApplication(ServerPlayerEntity player) {
        var playerNbt = LivingEntityBridge.getPersistentData(player);
        return playerNbt.contains("lastState") ? playerNbt.getInt("lastState") : -1;
    }

    private static void saveLastStateApplication(ServerPlayerEntity player, int state) {
        LivingEntityBridge.getPersistentData(player).putInt("lastState", state);
    }

    private static boolean verifyNewState(ServerPlayerEntity player, int newState) {
        var resurrection = PlayerEntityBridge.getResurrection(player);
        if (newState >= states.length) {
            return false;
        }
        var state = states[newState];
        return state.checkStateValidApplication(player, resurrection);
    }

    public static void performStateUpdate(Entity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            var lastState = loadLastStateApplication(player);
            var newState = lastState + 1;
            var do_perform = verifyNewState(player, newState);
            if (do_perform) {
                var state = states[newState];

                state.function.execute(player);
                PlayerEntityBridge.grantAdvancement(player, state.achievementUnlock);
                saveLastStateApplication(player, newState);
            }
        }
    }
}
