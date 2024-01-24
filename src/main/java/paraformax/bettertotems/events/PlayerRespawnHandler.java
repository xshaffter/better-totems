package paraformax.bettertotems.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerRespawnHandler implements ServerPlayerEvents.AfterRespawn {

    @Override
    public void afterRespawn(ServerPlayerEntity oldEntity, ServerPlayerEntity newEntity, boolean alive) {
        var oldAttribute = oldEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        var newAttribute = newEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert oldAttribute != null;
        assert newAttribute != null;
        newAttribute.setFrom(oldAttribute);
    }
}
