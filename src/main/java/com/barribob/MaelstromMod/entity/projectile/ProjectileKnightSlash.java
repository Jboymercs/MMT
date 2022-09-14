package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ProjectileKnightSlash extends Projectile{

    private static final int PARTICLE_AMOUNT = 10;
    public ProjectileKnightSlash(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
    }
    public ProjectileKnightSlash(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public ProjectileKnightSlash(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    @Override
    protected void spawnParticles() {
        float size = 0.25f;
        for (int i = 0; i < this.PARTICLE_AMOUNT; i++) {
            ParticleManager.spawnEffect(this.world, getPositionVector().add(ModRandom.randVec().scale(size)), ModColors.AZURE);
        }
    }

    @Override
    protected void onHit(@Nullable RayTraceResult result) {
        if(result != null && EntityMaelstromMob.isMaelstromMob(result.entityHit)) {
            return;
        }

        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.PROJECTILE)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .stoppedByArmorNotShields().build();

        ModUtils.handleAreaImpact(0.6f, (e) -> getDamage(), shootingEntity, getPositionVector(), source, 0, 0, false);
        playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0f, 1.0f + ModRandom.getFloat(0.2f));
        super.onHit(result);
    }
}