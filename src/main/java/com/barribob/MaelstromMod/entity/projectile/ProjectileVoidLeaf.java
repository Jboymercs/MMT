package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileVoidLeaf extends Projectile{

    public ProjectileVoidLeaf(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
    }

    public ProjectileVoidLeaf(World worldIn) {
        super(worldIn);
    }

    public ProjectileVoidLeaf(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        ParticleManager.spawnEffect(world, getPositionVector(), ModColors.PINK);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        if (result.entityHit != null && !EntityMaelstromMob.isMaelstromMob(result.entityHit) && this.shootingEntity != null) {
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MAGIC)
                    .indirectEntity(this)
                    .directEntity(shootingEntity)
                    .element(getElement())
                    .stoppedByArmorNotShields().build();

            result.entityHit.attackEntityFrom(source, this.getDamage());
        }
        this.playSound(SoundEvents.BLOCK_SNOW_BREAK, 1.0f + ModRandom.getFloat(0.2f), 1.0f + ModRandom.getFloat(0.2f));
        super.onHit(result);
    }
}
