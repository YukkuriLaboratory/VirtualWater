package net.yukulab.virtualpump.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;

public class VPFin extends ArmorItem {


    public VPFin(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()){
            if(entity instanceof PlayerEntity player){
                ItemStack itemStack = player.getEquippedStack(EquipmentSlot.FEET);

                if(itemStack.isOf(this) && !player.isSubmergedIn(FluidTags.WATER)){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 200, 0, false, false, true));
                }
            }
        }

        super.inventoryTick(stack, world, entity ,slot, selected);
    }


}
