package net.yukulab.virtualpump.mixin.end.dragon;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.DyingPhase;
import net.minecraft.entity.boss.dragon.phase.LandingPhase;
import net.minecraft.entity.boss.dragon.phase.TakeoffPhase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({TakeoffPhase.class, DyingPhase.class, LandingPhase.class})
public abstract class MixinDragonPhases extends AbstractPhase {
    public MixinDragonPhases(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Redirect(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getTopPosition(Lnet/minecraft/world/Heightmap$Type;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;"
            )
    )
    private BlockPos ignoreWater(World instance, Heightmap.Type type, BlockPos blockPos) {
        var dragonFight = dragon.getFight();
        if (dragonFight != null) {
            BlockPos exitPortalLocation = ((AccessorEnderDragonFight) dragonFight).getExitPortalLocation();
            return new BlockPos(blockPos.getX(), exitPortalLocation.getY() + 5, blockPos.getZ());
        }
        return instance.getTopPosition(type, blockPos);
    }
}
