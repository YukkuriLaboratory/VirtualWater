package net.yukulab.virtualpump.mixin.waterproof;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SaplingBlock.class, TallPlantBlock.class, TorchflowerBlock.class, BeetrootsBlock.class, CropBlock.class, SnowBlock.class, SugarCaneBlock.class, VineBlock.class, NetherWartBlock.class, CocoaBlock.class, AbstractPlantStemBlock.class, BambooBlock.class, FlowerbedBlock.class, CaveVinesBodyBlock.class, SweetBerryBushBlock.class, ChorusFlowerBlock.class, ChorusPlantBlock.class, CactusBlock.class})
public abstract class MixinPropertyInjectableBlock {
    @Inject(
            method = "appendProperties",
            at = @At("RETURN")
    )
    private void injectWaterLogged(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(Properties.WATERLOGGED);
    }
}
