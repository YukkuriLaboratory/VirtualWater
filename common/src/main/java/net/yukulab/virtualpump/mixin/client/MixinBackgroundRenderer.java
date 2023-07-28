package net.yukulab.virtualpump.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public abstract class MixinBackgroundRenderer
{
    @Inject(method = "applyFog", at = @At("RETURN"))
    private static void applyFogModify(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo info)
    {
        var FOG_START = -8.0F;
        var FOG_END = 150.0F;
        RenderSystem.setShaderFogStart(FOG_START);
        RenderSystem.setShaderFogEnd(FOG_END);
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
    }
}
