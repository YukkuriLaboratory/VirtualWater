package net.yukulab.virtualpump.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SpreadableBlock.class)
public abstract class MixinSpreadableBlock {
    @Inject(
            method = "canSurvive",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;getLevel()I"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private static void ignoreWater(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockPos blockPos, BlockState belowBlock) {
        if (belowBlock.isOf(Blocks.WATER) || belowBlock.getBlock() instanceof Waterloggable) {
            cir.setReturnValue(true);
        }
    }
}
