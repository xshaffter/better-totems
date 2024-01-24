package paraformax.bettertotems.util.complexManagers.difficulty.states;

import net.minecraft.server.network.ServerPlayerEntity;
import paraformax.bettertotems.events.GameRuleManager;
import paraformax.bettertotems.util.PlayerEntityBridge;
import paraformax.bettertotems.util.complexManagers.difficulty.managers.ExecuteState;

import java.util.function.Function;

public class BasicState {
    private final double tolerance;
    public final String achievementUnlock;
    private final String requiredAchievement;
    public final ExecuteState function;


    public BasicState(double tolerance, String achievementUnlock, String requiredAchievement, ExecuteState exec) {
        this.tolerance = tolerance;
        this.achievementUnlock = achievementUnlock;
        this.requiredAchievement = requiredAchievement;
        this.function = exec;
    }


    private boolean checkStateValidAchievementState(ServerPlayerEntity player) {
        return PlayerEntityBridge.hasAdvancement(player, requiredAchievement) && (achievementUnlock.isEmpty() || !PlayerEntityBridge.hasAdvancement(player, achievementUnlock));
    }

    public boolean checkStateValidApplication(ServerPlayerEntity player, int input) {
        return input >= getRealTolerance(player, tolerance) && checkStateValidAchievementState(player);
    }

    private int getRealTolerance(ServerPlayerEntity player, double tolerance) {
        int worldTolerance = player.getServerWorld().getGameRules().getInt(GameRuleManager.RESURRECTION_GOD_TOLERANCE);
        return (int) Math.round(worldTolerance * tolerance);
    }
}
