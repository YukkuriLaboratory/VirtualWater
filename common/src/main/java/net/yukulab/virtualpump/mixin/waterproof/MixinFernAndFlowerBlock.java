package net.yukulab.virtualpump.mixin.waterproof;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin({FernBlock.class, TallPlantBlock.class, MushroomPlantBlock.class, SnowBlock.class, FlowerBlock.class, TallFlowerBlock.class, CocoaBlock.class, SugarCaneBlock.class, CarpetBlock.class, FungusBlock.class, VineBlock.class, BambooBlock.class, SaplingBlock.class, DeadBushBlock.class, CarpetBlock.class, LilyPadBlock.class, NetherWartBlock.class, SproutsBlock.class, CropBlock.class, FlowerbedBlock.class, RootsBlock.class, CaveVinesHeadBlock.class, TwistingVinesBlock.class, TwistingVinesPlantBlock.class, WeepingVinesBlock.class, WeepingVinesPlantBlock.class, CaveVinesBodyBlock.class, AzaleaBlock.class, SweetBerryBushBlock.class})
public abstract class MixinFernAndFlowerBlock extends Block implements Waterloggable {
    public MixinFernAndFlowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void setDefaultState(BlockState state) {
        super.setDefaultState(state.with(Properties.WATERLOGGED, false));
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(Properties.WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (!state.canPlaceAt(world, pos) && List.of(Blocks.SUGAR_CANE,Blocks.BAMBOO).contains(state.getBlock())) {
            world.scheduleBlockTick(pos, this, 1);
        }
        if (direction == Direction.UP && neighborState.isOf(Blocks.BAMBOO) && neighborState.get(BambooBlock.AGE) > state.get(BambooBlock.AGE)) {
            world.setBlockState(pos, state.cycle(BambooBlock.AGE), Block.NOTIFY_LISTENERS);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(Properties.WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return Fluids.EMPTY.getDefaultState();
    }
}