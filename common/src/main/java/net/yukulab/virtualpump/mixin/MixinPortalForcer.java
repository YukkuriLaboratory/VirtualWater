package net.yukulab.virtualpump.mixin;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.PortalForcer;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(PortalForcer.class)
public abstract class MixinPortalForcer {
    @Shadow
    @Final
    private ServerWorld world;

    @Inject(
            method = "createPortal",
            at = @At("RETURN")
    )
    private void replaceAirToWater(BlockPos pos, Direction.Axis axis, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
        var portalChunk = world.getChunk(pos);
        var portalChunkPos = portalChunk.getPos();
        List<Chunk> arounds = Lists.newArrayList();
        for (int x = portalChunkPos.x - 1; x <= portalChunkPos.x + 1; x++) {
            for (int z = portalChunkPos.z - 1; z <= portalChunkPos.z + 1; z++) {
                arounds.add(world.getChunk(x, z));
            }
        }
        arounds.forEach(
                chunk -> chunk.forEachBlockMatchingPredicate(
                        blockState -> blockState != null && blockState.isAir(),
                        (blockPos, blockState) -> {
                            if (blockPos.getY() < 128) {
                                chunk.setBlockState(blockPos, Blocks.WATER.getDefaultState(), false);
                            }
                        }
                )
        );
    }
}
