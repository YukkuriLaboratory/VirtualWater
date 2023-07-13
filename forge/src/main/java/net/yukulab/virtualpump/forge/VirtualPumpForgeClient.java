package net.yukulab.virtualpump.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;

public class VirtualPumpForgeClient {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(VirtualPumpForgeClient::fixFog);
    }

    public static void fixFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        switch (camera.getSubmersionType()) {
            case WATER, LAVA, POWDER_SNOW -> {
                RenderSystem.setShaderFogStart(-8.0F);
                RenderSystem.setShaderFogEnd(250.0F);
                RenderSystem.setShaderFogShape(FogShape.CYLINDER);
                event.setCanceled(true);
            }
            default -> {
            }
        }
    }
}
