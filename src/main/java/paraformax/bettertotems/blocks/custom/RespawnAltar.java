package paraformax.bettertotems.blocks.custom;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paraformax.bettertotems.blocks.blockstates.BooleanProperty;
import paraformax.bettertotems.blocks.blockstates.PropertyManager;

public class RespawnAltar extends Block {
    public static final EnumProperty<BooleanProperty> ON_USE = PropertyManager.ON_USE;

    public RespawnAltar() {
        super(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)
                .luminance(3)
                .nonOpaque()
        );
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(ON_USE, BooleanProperty.NO);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ON_USE);
    }
}
