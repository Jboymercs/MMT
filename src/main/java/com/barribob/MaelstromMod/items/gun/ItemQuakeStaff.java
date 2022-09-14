package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * A short range quake attack
 */
public class ItemQuakeStaff extends ItemStaff {
    public ItemQuakeStaff(String name, int maxDamage, float level, CreativeTabs tab) {
        super(name, maxDamage, level, tab);
    }

    public float getBaseDamage() {
        return 6 * ModConfig.balance.weapon_damage;
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
        float inaccuracy = 0.0f;
        float speed = 0.5f;
        float pitch = 0; // Projectiles aim straight ahead always

        // Shoots projectiles in a small arc
        for (int i = 0; i < 5; i++) {
            ProjectileQuake projectile = new ProjectileQuake(world, player, ModUtils.getEnchantedDamage(stack, getLevel(), getBaseDamage()), stack);
            projectile.setPosition(player.posX, player.posY, player.posZ);
            projectile.shoot(player, pitch, player.rotationYaw - 20 + (i * 10), 0.0F, speed, inaccuracy);
            projectile.setTravelRange(8f);
            world.spawnEntity(projectile);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(ModUtils.getDamageTooltip(ModUtils.getEnchantedDamage(stack, this.getLevel(), getBaseDamage())));
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("quake_staff"));
    }

    @Override
    public boolean doesDamage() {
        return true;
    }
}
