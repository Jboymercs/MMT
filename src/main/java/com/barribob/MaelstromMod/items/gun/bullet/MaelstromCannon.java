package com.barribob.MaelstromMod.items.gun.bullet;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromCannon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MaelstromCannon implements BulletFactory {
    @Override
    public Projectile get(World world, EntityPlayer player, ItemStack stack, float damage) {
        return new ProjectileMaelstromCannon(world, player, damage, stack);
    }
}
