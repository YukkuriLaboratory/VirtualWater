package net.yukulab.virtualpump;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

public class Registries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(VirtualPump.MOD_ID, RegistryKeys.ITEM);

    // TODO アイテムの実装終わったらコメントアウトを外す
//    public static final Item VP_FIN = new VPFin(new Item.Settings().rarity(Rarity.UNCOMMON));

    public static void init() {
        ITEMS.register();
    }
}
