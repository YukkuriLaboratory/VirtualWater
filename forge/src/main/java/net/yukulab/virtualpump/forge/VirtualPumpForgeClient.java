package net.yukulab.virtualpump.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.FogShape;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Objects;

public class VirtualPumpForgeClient {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(VirtualPumpForgeClient::fixFog);
    }

    public static void fixFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        if (Objects.requireNonNull(camera.getSubmersionType()) == CameraSubmersionType.WATER) {
            RenderSystem.setShaderFogStart(0F);
            RenderSystem.setShaderFogEnd(0F);
            RenderSystem.setShaderFogShape(FogShape.CYLINDER);
            event.setCanceled(true);
        }
    }
}
