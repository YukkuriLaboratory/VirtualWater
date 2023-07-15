package net.yukulab.virtualpump.mixin.end.dragon;

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnderDragonFight.class)
public interface AccessorEnderDragonFight {
    @Accessor("exitPortalLocation")
    BlockPos getExitPortalLocation();
}
