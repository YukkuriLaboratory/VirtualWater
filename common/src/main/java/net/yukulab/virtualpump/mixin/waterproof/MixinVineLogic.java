package net.yukulab.virtualpump.mixin.waterproof;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VineLogic.class)
public class MixinVineLogic {
    @Inject(
            method = "isValidForWeepingStem",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void checkInWater(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || state.isOf(Blocks.WATER));
    }
}
