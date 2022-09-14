package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileFireball extends ProjectileGun {
    private static final int IMPACT_PARTICLE_AMOUNT = 30;
    private static final int EXPOSION_AREA_FACTOR = 4;
    public static final Vec3d FIREBALL_COLOR = new Vec3d(1.0, 0.6, 0.5);

    public ProjectileFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
        this.setNoGravity(true);
    }

    public ProjectileFireball(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public ProjectileFireball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    @Override
    protected void spawnParticles() {
        float size = 0.25f;
        for (int i = 0; i < 2; i++) {
            ParticleManager.spawnCustomSmoke(this.world,
                    new Vec3d(this.posX, this.posY, this.posZ).add(new Vec3d(ModRandom.getFloat(size), ModRandom.getFloat(size), ModRandom.getFloat(size))),
                    FIREBALL_COLOR,
                    ModUtils.yVec(0.1f));
        }
    }

    @Override
    protected void spawnImpactParticles() {
        for (int i = 0; i < IMPACT_PARTICLE_AMOUNT; i++) {
            ParticleManager.spawnColoredExplosion(world, this.getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 2)), ModColors.FIREBALL_ORANGE);
            this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
                    this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
            ParticleManager.spawnEffect(world, getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 2)), FIREBALL_COLOR);
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        float knockbackFactor = 1.1f + this.getKnockback() * 0.4f;
        int fireFactor = this.isBurning() ? 10 : 5;
        ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, this::getGunDamage, this.shootingEntity, this.getPositionVector(),
                DamageSource.causeExplosionDamage(this.shootingEntity), knockbackFactor, fireFactor);
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        super.onHit(result);
    }

    @Override
    public Item getItemToRender() {
        return Items.FIRE_CHARGE;
    }
}
