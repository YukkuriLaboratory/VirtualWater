package net.yukulab.virtualpump.mixin;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({FernBlock.class, TallPlantBlock.class, MushroomPlantBlock.class, SnowBlock.class, FlowerBlock.class, TallFlowerBlock.class, CocoaBlock.class, SugarCaneBlock.class, CarpetBlock.class, FungusBlock.class, AmethystClusterBlock.class, VineBlock.class, BambooBlock.class, SaplingBlock.class, DeadBushBlock.class, CarpetBlock.class, LilyPadBlock.class, NetherWartBlock.class, CropBlock.class, FlowerbedBlock.class})
public abstract class MixinFernAndFlowerBlock extends Block implements FluidFillable {
    public MixinFernAndFlowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }
}