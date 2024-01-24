package paraformax.bettertotems.blocks.blockstates;


import net.minecraft.util.StringIdentifiable;

public enum BooleanProperty implements StringIdentifiable {
    NO,
    YES;

    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return switch (this) {
            case YES -> "yes";
            case NO -> "no";
        };
    }
}