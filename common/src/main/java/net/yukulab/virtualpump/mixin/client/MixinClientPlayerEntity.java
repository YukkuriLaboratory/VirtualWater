package net.yukulab.virtualpump.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.tag.BiomeTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Shadow @Final public ClientPlayNetworkHandler networkHandler;

    @Shadow @Final protected MinecraftClient client;

    @Shadow
    private int underwaterVisibilityTicks;
    private float visibilityMultiply = 1;
    private int lastChangedTick = underwaterVisibilityTicks;
    private boolean isInCloserWaterFog = false;

    @Inject(
            method = "getUnderwaterVisibility",
            at = @At("RETURN"),
            cancellable = true
    )
    private void changeWaterVisibility(CallbackInfoReturnable<Float> cir) {
        var returnValue = cir.getReturnValue();
        var world = this.networkHandler.getWorld();
        var playerPos = this.client.player != null ? this.client.player.getBlockPos() : null;
        if (playerPos != null && world.getBiome(playerPos).isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
            if (!isInCloserWaterFog) {
                isInCloserWaterFog = true;
                lastChangedTick = underwaterVisibilityTicks;
            }
            visibilityMultiply = Math.max(1, visibilityMultiply - (underwaterVisibilityTicks - lastChangedTick) * 0.012f);
        }else {
            if (isInCloserWaterFog) {
                isInCloserWaterFog = false;
                lastChangedTick = underwaterVisibilityTicks;
            }
            visibilityMultiply = Math.min(2, visibilityMultiply + (underwaterVisibilityTicks - lastChangedTick) * 0.012f);
        }
        cir.setReturnValue(returnValue * visibilityMultiply);
    }
}
