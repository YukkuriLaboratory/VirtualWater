package net.yukulab.virtualpump.forge.mixin;

import com.pam.pamhc2crops.blocks.BlockPamCrop;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockPamCrop.class)
public abstract class MixinBlockPamCrop {
    @Inject(
            method = "appendProperties",
            at = @At("RETURN")
    )
    private void injectWaterLogged(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(Properties.WATERLOGGED);
    }
}
