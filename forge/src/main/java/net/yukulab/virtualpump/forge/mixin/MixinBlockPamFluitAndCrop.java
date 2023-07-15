package net.yukulab.virtualpump.forge.mixin;

import com.pam.pamhc2crops.blocks.BlockPamCrop;
import com.pam.pamhc2trees.blocks.BlockPamFruit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BlockPamFruit.class, BlockPamCrop.class})
public class MixinBlockPamFluitAndCrop extends Block implements FluidFillable {
    public MixinBlockPamFluitAndCrop(Settings arg) {
        super(arg);
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
