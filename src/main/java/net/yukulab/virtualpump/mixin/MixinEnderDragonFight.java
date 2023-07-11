package net.yukulab.virtualpump.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragonFight.class)
public abstract class MixinEnderDragonFight {
    @Redirect(
            method = "generateEndPortal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;getTopPosition(Lnet/minecraft/world/Heightmap$Type;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;"
            )
    )
    private BlockPos ignoreWater(ServerWorld instance, Heightmap.Type type, BlockPos blockPos) {
        return new BlockPos(blockPos.getX(), instance.getTopY(), blockPos.getZ()).down(8);
    }

    @Redirect(
            method = "dragonKilled",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            )
    )
    private boolean fixEggSpawn(ServerWorld instance, BlockPos blockPos, BlockState blockState) {
        return instance.setBlockState(new BlockPos(blockPos.getX(), instance.getTopY(), blockPos.getZ()).down(2), blockState);
    }
}
