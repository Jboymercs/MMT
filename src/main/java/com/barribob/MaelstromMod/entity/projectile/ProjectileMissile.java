package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityHunterMissile;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProjectileMissile extends Projectile{

    public static final int AGE = 20 * 4;
    public ProjectileMissile(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
    }

    public ProjectileMissile(World worldIn) {
        super(worldIn);
    }
    public ProjectileMissile(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            ModUtils.avoidOtherEntities(this, 0.2, 3, e -> e instanceof ProjectileMissile || e == this.shootingEntity);
        }
        if (!world.isRemote && ticksExisted > AGE) {
            this.onImpact();
            this.setDead();
        }
    }

    private void onImpact() {
        if (shootingEntity != null && shootingEntity instanceof EntityLeveledMob) {
            EntityHunterMissile missile = new EntityHunterMissile(world, (EntityLeveledMob) shootingEntity);
            ModUtils.setEntityPosition(missile, getPositionVector());
            world.spawnEntity(missile);
        }
    }
    @Override
    protected void onHit(@Nullable RayTraceResult result) {
        if (!world.isRemote) {
            onImpact();
        }
        super.onHit(result);
    }


}
