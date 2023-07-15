package net.yukulab.virtualpump.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.EnumSet;
import java.util.List;

@Mixin(ChunkStatus.class)
public abstract class MixinChunkStatus {
    @Shadow
    private static ChunkStatus register(String id, @Nullable ChunkStatus previous, int taskMargin, boolean shouldAlwaysUpgrade, EnumSet<Heightmap.Type> heightMapTypes, ChunkStatus.ChunkType chunkType, ChunkStatus.GenerationTask generationTask, ChunkStatus.LoadTask loadTask) {
        return null;
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/chunk/ChunkStatus;register(Ljava/lang/String;Lnet/minecraft/world/chunk/ChunkStatus;IZLjava/util/EnumSet;Lnet/minecraft/world/chunk/ChunkStatus$ChunkType;Lnet/minecraft/world/chunk/ChunkStatus$GenerationTask;Lnet/minecraft/world/chunk/ChunkStatus$LoadTask;)Lnet/minecraft/world/chunk/ChunkStatus;"
            )
    )
    private static ChunkStatus swapFull(String id, @Nullable ChunkStatus previous, int taskMargin, boolean shouldAlwaysUpgrade, EnumSet<Heightmap.Type> heightMapTypes, ChunkStatus.ChunkType chunkType, ChunkStatus.GenerationTask generationTask, ChunkStatus.LoadTask loadTask) {
        if (id.equals("light")) {
            return register(id, previous, taskMargin, shouldAlwaysUpgrade, heightMapTypes, chunkType, (chunkStatus, executor, world, chunkGenerator, structureTemplateManager, lightingProvider, fullChunkConverter, chunks, chunk) -> {
                chunk.forEachBlockMatchingPredicate((state) -> state != null && virtualpump$isReplaceTarget(state), (blockPos, blockState) -> {
                    if (world.getRegistryKey() != World.NETHER || blockPos.getY() < 128) {
                        chunk.setBlockState(blockPos, Blocks.WATER.getDefaultState(), false);
                        var below = blockPos.down();
                        var belowBlock = chunk.getBlockState(below);
                        if (belowBlock != null && belowBlock.isOf(Blocks.LAVA)) {
                            chunk.setBlockState(below, Blocks.OBSIDIAN.getDefaultState(), false);
                        }
                    }
                });
                return generationTask.doWork(chunkStatus, executor, world, chunkGenerator, structureTemplateManager, lightingProvider, fullChunkConverter, chunks, chunk);
            }, loadTask);
        } else {
            return register(id, previous, taskMargin, shouldAlwaysUpgrade, heightMapTypes, chunkType, generationTask, loadTask);
        }
    }

    @Unique
    private static boolean virtualpump$isReplaceTarget(BlockState state) {
        return state.isAir() || List.of(Blocks.FERN, Blocks.GRASS, Blocks.TALL_GRASS, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.SNOW, Blocks.POPPY, Blocks.DANDELION).contains(state.getBlock());
    }
}
