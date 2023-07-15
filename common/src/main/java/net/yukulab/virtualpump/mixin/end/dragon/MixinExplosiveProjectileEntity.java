package net.yukulab.virtualpump.mixin.end.dragon;

import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExplosiveProjectileEntity.class)
public abstract class MixinExplosiveProjectileEntity {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/hit/HitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;"
            )
    )
    private HitResult.Type ignoreWater(HitResult instance) {
        var entity = (ExplosiveProjectileEntity) (Object) this;
        if (entity instanceof DragonFireballEntity && instance instanceof BlockHitResult blockHitResult) {
            var hitPos = blockHitResult.getBlockPos();
            var hitBlock = entity.getWorld().getBlockState(hitPos);
            if (hitBlock != null && hitBlock.isOf(Blocks.WATER)) {
                return HitResult.Type.MISS;
            }
        }
        return instance.getType();
    }
}
