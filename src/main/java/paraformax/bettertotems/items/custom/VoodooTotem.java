package paraformax.bettertotems.items.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class VoodooTotem extends Item {
    public VoodooTotem() {
        super(new FabricItemSettings()
                .maxCount(64)
                .fireproof()
                .rarity(Rarity.EPIC)
        );
    }
}
