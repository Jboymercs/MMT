package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAIFollowAttackers;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSkullAttack;
import com.barribob.MaelstromMod.entity.util.IAcceleration;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


// Revised Maelstrom Skull, Added in a flying ai and aswell adjusted range and revamped particles used for the skull.
/**
 Model, Texture, Animations all done by GDrayn. Revision of AI and changes done by UnseenDontRun.
 */

public class EntityFloatingSkull extends EntityMaelstromMob implements IAcceleration {
    Vec3d acceleration = Vec3d.ZERO;
    public EntityFloatingSkull(World worldIn) {
        super(worldIn);
        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
        if (world.isRemote) {
            ModBBAnimations.animation(this, "skull.idle", false);
        }

        }





    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.setNoGravity(true);
        this.tasks.addTask(3, new EntityAIFollowAttackers(this, 2));
        this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 60, 5, 9.5f, 0.5f));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
        this.noClip = false;
        Vec3d prevAcceleration = acceleration;
        acceleration = ModUtils.getEntityVelocity(this).scale(0.1).add(this.acceleration.scale(0.9));
        if (world.isRemote) {
            ParticleManager.spawnDarkFlames(world, rand,
                    new Vec3d(this.posX + ModRandom.getFloat(0.5f), this.posY + 0.1f + ModRandom.getFloat(0.1f), this.posZ + ModRandom.getFloat(0.5f)));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected float getSoundPitch() {
        return 0.8f + ModRandom.getFloat(0.1f);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        if (swingingArms) {
            this.world.setEntityState(this, (byte) 4);
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {


        } else {
            super.handleStatusUpdate(id);
        }
    }

    /**
     * Shoots a projectile in a similar fashion to the snow golem (see
     * EntitySnowman)
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (!world.isRemote) {

            world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 0.5F,
                    0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));

            float inaccuracy = 0.0f;
            float speed = 0.5f;
            EntityFloatingSkull.this.getLookHelper().setLookPositionWithEntity(EntityFloatingSkull.this.getAttackTarget(), (float)EntityFloatingSkull.this.getHorizontalFaceSpeed(),(float)EntityFloatingSkull.this.getVerticalFaceSpeed());
            ModBBAnimations.animation(this, "skull.shoot", false);
            ProjectileSkullAttack projectile = new ProjectileSkullAttack(world, this, this.getAttack());
            projectile.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, speed, inaccuracy);
            projectile.setTravelRange(13f);


            world.spawnEntity(projectile);
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        ModUtils.aerialTravel(this, strafe, vertical, forward);
    }


    @Override
    public Vec3d getAcceleration() { return acceleration;
    }
}