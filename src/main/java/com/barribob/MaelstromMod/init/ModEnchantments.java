package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.enchantments.*;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Based on Jabelar's enchantment tutorial
 * https://jabelarminecraft.blogspot.com/p/minecraft-modding_6.html
 */
@ObjectHolder(Reference.MOD_ID)
public class ModEnchantments {
    public static final Enchantment reload = null;
    public static final Enchantment gun_power = null;
    public static final Enchantment impact = null;
    public static final Enchantment gun_flame = null;
    public static final Enchantment maelstrom_destroyer = null;
    public static final Enchantment critical_hit = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void onRegisterEnchantments(final RegistryEvent.Register<Enchantment> event) {
            final IForgeRegistry<Enchantment> registry = event.getRegistry();

            EntityEquipmentSlot[] weaponSlots = new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};

            registry.register(new EnchantmentReload("reload", Enchantment.Rarity.UNCOMMON, weaponSlots));
            registry.register(new EnchantmentPower("gun_power", Enchantment.Rarity.COMMON, weaponSlots));
            registry.register(new EnchantmentImpact("impact", Enchantment.Rarity.RARE, weaponSlots));
            registry.register(new EnchantmentEnflame("gun_flame", Enchantment.Rarity.RARE, weaponSlots));
            registry.register(new EnchantmentMaelstromDestroyer("maelstrom_destroyer", Enchantment.Rarity.RARE, weaponSlots));
            registry.register(new EnchantmentCriticalHit("critical_hit", Enchantment.Rarity.RARE, weaponSlots));
        }
    }
}
