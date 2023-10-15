package paraformax.bettertotems.items.custom;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class HeartFragment extends Item {
    public HeartFragment() {
        super(new Settings().maxCount(8).rarity(Rarity.EPIC));
    }
}
