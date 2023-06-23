package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.action.ActionSporeBomb;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileSporeBomb extends Projectile{
    private static final int PARTICLE_AMOUNT = 1;
    private static final int EXPOSION_AREA_FACTOR = 2;

    public ProjectileSporeBomb(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
    }

    public ProjectileSporeBomb(World worldIn) {
        super(worldIn);
    }

    public ProjectileSporeBomb(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        for (int i = 0; i < this.PARTICLE_AMOUNT; i++) {
            ParticleManager.spawnColoredSmoke(world, getPositionVector(), ModColors.GREEN, new Vec3d(0, 0.1, 0));
        }
    }

    @Override
    protected void spawnImpactParticles() {
        ModUtils.circleCallback(2, 30, (pos) -> {
            pos = new Vec3d(pos.x, 0, pos.y);
            ParticleManager.spawnDust(world, pos.add(this.getPositionVector()).add(ModUtils.yVec(5.6)), ModColors.GREEN, pos.normalize().scale(0.3).add(ModUtils.yVec(0.1)), ModRandom.range(2, 6));
        } );
    }
    @Override
    protected void onHit(RayTraceResult result) {
        if(!world.isRemote) {
            new ActionSporeBomb().performAction(this, null);
        }
        DamageSource source = ModDamageSource.builder()
                .indirectEntity(shootingEntity)
                .directEntity(this)
                .type(ModDamageSource.EXPLOSION)
                .element(getElement())
                .stoppedByArmorNotShields().build();

        ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> this.getDamage(), this.shootingEntity, this.getPositionVector(), source);
        this.playSound(SoundEvents.BLOCK_SLIME_PLACE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        super.onHit(result);
    }

}
