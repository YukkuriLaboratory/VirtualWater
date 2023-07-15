package net.yukulab.virtualpump.mixin.end;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragonFight.class)
public abstract class MixinEnderDragonFight {
    @Shadow
    private @Nullable BlockPos exitPortalLocation;

    @Redirect(
            method = "generateEndPortal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;getTopPosition(Lnet/minecraft/world/Heightmap$Type;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;"
            )
    )
    private BlockPos ignoreWater(ServerWorld instance, Heightmap.Type type, BlockPos blockPos) {
        var portalPos = new BlockPos(blockPos.getX(), instance.getTopY(), blockPos.getZ()).down(8);
        while (!instance.getBlockState(portalPos).isOf(Blocks.END_STONE) && portalPos.getY() > instance.getSeaLevel()) {
            portalPos = portalPos.down();
        }
        return portalPos;
    }

    @Redirect(
            method = "dragonKilled",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            )
    )
    private boolean fixEggSpawn(ServerWorld instance, BlockPos blockPos, BlockState blockState) {
        return instance.setBlockState(exitPortalLocation.up(5), blockState);
    }
}
