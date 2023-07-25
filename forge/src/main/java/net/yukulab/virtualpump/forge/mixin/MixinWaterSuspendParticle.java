package net.yukulab.virtualpump.forge.mixin;

import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterSuspendParticle.UnderwaterFactory.class)
public abstract class MixinWaterSuspendParticle {
    @Inject(
            method = "createParticle",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectUnderwaterParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
    }
}
