package net.yukulab.virtualpump.mixin.waterproof;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({FernBlock.class, MushroomPlantBlock.class, FlowerBlock.class, CarpetBlock.class, FungusBlock.class, DeadBushBlock.class, CarpetBlock.class, LilyPadBlock.class, RootsBlock.class, AzaleaBlock.class})
public abstract class MixinNonePropertyBlocks extends Block {
    public MixinNonePropertyBlocks(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.WATERLOGGED);
    }
}
