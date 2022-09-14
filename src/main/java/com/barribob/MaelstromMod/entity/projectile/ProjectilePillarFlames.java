package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectilePillarFlames extends Projectile {
    public ProjectilePillarFlames(World worldIn, EntityLivingBase throwerIn, float baseDamage) {
        super(worldIn, throwerIn, baseDamage);
        this.setNoGravity(true);
    }

    public ProjectilePillarFlames(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public ProjectilePillarFlames(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    @Override
    protected void spawnParticles() {
        world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0, 0, 0);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        this.setFire(1);
        ModUtils.handleBulletImpact(result.entityHit, this, this.getDamage(), ModDamageSource.causeElementalThrownDamage(this, shootingEntity, getElement()));
        super.onHit(result);
    }
}
