package paraformax.bettertotems.blocks.custom;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RespawnAltar extends Block {
    public RespawnAltar() {
        super(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)
                .luminance(3)
                .nonOpaque()
        );
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
