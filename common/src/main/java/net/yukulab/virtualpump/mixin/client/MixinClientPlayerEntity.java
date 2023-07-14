package net.yukulab.virtualpump.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Inject(
            method = "getUnderwaterVisibility",
            at = @At("RETURN"),
            cancellable = true
    )
    private void changeWaterVisibility(CallbackInfoReturnable<Float> cir) {
        var returnValue = cir.getReturnValue();
        if (returnValue == 1.0F) {
            cir.setReturnValue(2.0F);
        }
    }
}
