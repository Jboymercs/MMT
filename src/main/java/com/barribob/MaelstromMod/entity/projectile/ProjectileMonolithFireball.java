package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileMonolithFireball extends ProjectileGun {
    private static final int EXPOSION_AREA_FACTOR = 2;
    public static final Vec3d FIREBALL_COLOR = new Vec3d(1.0, 0.6, 0.5);

    public ProjectileMonolithFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
        this.setNoGravity(true);
    }

    public ProjectileMonolithFireball(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public ProjectileMonolithFireball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    @Override
    public void onUpdate() {
        this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.5f, ModRandom.getFloat(0.2f) + 1.0f);
        super.onUpdate();
    }

    @Override
    protected void spawnParticles() {
        float size = 0.25f;
        ParticleManager.spawnEffect(this.world, getPositionVector().add(ModRandom.randVec().scale(size)), ModColors.RED);
        world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY, this.posZ, 0, 0, 0);

        float groundHeight = ModUtils.findGroundBelow(world, getPosition()).getY() + 1.2f;
        Vec3d indicationPos = new Vec3d(posX, groundHeight, posZ);
        ModUtils.circleCallback(EXPOSION_AREA_FACTOR, 6, (pos) -> {
            Vec3d circleOffset = rotateCircleOverTime(pos);
            ParticleManager.spawnEffect(world, indicationPos.add(circleOffset), ModColors.RED);
        });
    }

    private Vec3d rotateCircleOverTime(Vec3d pos) {
        Vec3d circleOffset = new Vec3d(pos.x, 0, pos.y);
        circleOffset = ModUtils.rotateVector2(circleOffset, ModUtils.Y_AXIS, ticksExisted * 2);
        return circleOffset;
    }

    @Override
    protected void spawnImpactParticles() {
        for (int i = 0; i < 30; i++) {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
                    this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
            this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
                    this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
            ParticleManager.spawnEffect(world, getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 2)), ModColors.RED);
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        float knockbackFactor = 1.1f + this.getKnockback() * 0.4f;
        int fireFactor = this.isBurning() ? 8 : 3;

        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.EXPLOSION)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .stoppedByArmorNotShields()
                .element(getElement()).build();

        ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, this::getGunDamage, this.shootingEntity, this.getPositionVector(),
                source, knockbackFactor, fireFactor);
        if (!world.isRemote) {
            for (int j = 0; j < 5; j++) {
                Vec3d pos = getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR - 1));
                if (world.isBlockFullCube(new BlockPos(pos).down()) && world.isAirBlock(new BlockPos(pos))) {
                    world.setBlockState(new BlockPos(pos), Blocks.FIRE.getDefaultState());
                }
            }
        }
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        super.onHit(result);
    }
}
