package paraformax.bettertotems.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

public interface BaseTotem {

    boolean checkProbability();

    boolean performResurrection(DamageSource source, Entity resurrected);

    void postRevive(Entity entity);
}
