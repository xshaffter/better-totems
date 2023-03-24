package paraformax.bettertotems.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

@SuppressWarnings("unused")
public interface BaseTotem {

    boolean checkProbability();

    boolean canRevive(DamageSource source, Entity resurrected);

    void performResurrection(Entity resurrected);

    void postRevive(Entity entity);
}
