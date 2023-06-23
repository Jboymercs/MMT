package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityVoidBlossom;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class EmtityGenericWave extends EntityLeveledMob implements IAnimatable {
    /**
     * A simple low AI that just damages the player and disappears following, creates a nice effect for waves or AOE
     */
    private AnimationFactory factory = new AnimationFactory(this);
    private final String ANIM_SHOOT = "shoot";


    public EmtityGenericWave(World worldIn) {
        super(worldIn);
        this.setImmovable(true);

        this.setSize(0.6f, 2.0f);
        this.noClip = true;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionX = 0;
        this.motionZ = 0;
        this.rotationYaw = 0;
        this.rotationPitch = 0;
        this.rotationYawHead = 0;
        this.renderYawOffset = 0;

        if(ticksExisted > 20 && ticksExisted < 27) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
        if(ticksExisted == 20) {
                this.playSound(SoundsHandler.SPORE_IMPACT, 0.75f, 1.0f);
                List<EntityLivingBase> targets = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox(), e -> !e.getIsInvulnerable() && (!(e instanceof EntityVoidBlossom || e instanceof EntityVoidSpike || e instanceof EmtityGenericWave)));

                if(!targets.isEmpty()) {
                    EntityLivingBase target = this.getAttackTarget();
                    Vec3d offset = this.getPositionVector().add(ModUtils.yVec(1.0D));
                    DamageSource source = ModDamageSource.builder().disablesShields().type(ModDamageSource.MOB).directEntity(this).build();
                    float damage = this.getAttack();
                    ModUtils.handleAreaImpact(0.5f, (e) -> damage, this, offset, source, 0.2f, 0, false);
                    addPotionEffect(new PotionEffect(MobEffects.POISON, 9, 3));
                    if(target != null) {
                        double distSq = this.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
                        double distance = Math.sqrt(distSq);
                        if (target.getActivePotionEffect(MobEffects.POISON) == null && distance < 1) {
                            target.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 2));
                        }
                    }
                }

        }
        if(ticksExisted == 30) {
            this.setDead();
        }
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
    }
    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            ParticleManager.spawnColoredSmoke(world, this.getPositionVector().add(ModUtils.yVec(0.5D)), ModColors.GREEN, new Vec3d(0,0.1,0));
            ParticleManager.spawnColoredSmoke(world, this.getPositionVector().add(ModUtils.yVec(1.5D)), ModColors.GREEN, new Vec3d(0,0.1,0));
            ParticleManager.spawnColoredSmoke(world, this.getPositionVector().add(ModUtils.yVec(3.0D)), ModColors.GREEN, new Vec3d(0,0.1,0));
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "wave_controller", 0, this::predicateIdle));
    }
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    private <E extends IAnimatable> PlayState predicateIdle(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_SHOOT, false));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
