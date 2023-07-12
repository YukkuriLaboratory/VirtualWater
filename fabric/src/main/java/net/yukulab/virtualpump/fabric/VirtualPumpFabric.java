package net.yukulab.virtualpump.fabric;

import net.fabricmc.api.ModInitializer;
import net.yukulab.virtualpump.VirtualPump;

public class VirtualPumpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VirtualPump.init();
    }
}
