package net.yukulab.virtualpump.mixin.end.dragon;

import net.minecraft.entity.boss.dragon.phase.SittingFlamingPhase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SittingFlamingPhase.class)
public abstract class MixinSittingFlamingPhase {
    @Redirect(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isAir(Lnet/minecraft/util/math/BlockPos;)Z"
            )
    )
    private boolean ignoreWater(World instance, BlockPos blockPos) {
        return instance.isAir(blockPos) || instance.isWater(blockPos);
    }
}
