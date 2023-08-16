package net.yukulab.virtualpump.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.yukulab.virtualpump.VirtualPump;

@Mod(VirtualPump.MOD_ID)
public class VirtualPumpForge {
    public VirtualPumpForge() {
//        EventBuses.registerModEventBus(VirtualPump.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        VirtualPump.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> VirtualPumpForgeClient::init);
    }
}