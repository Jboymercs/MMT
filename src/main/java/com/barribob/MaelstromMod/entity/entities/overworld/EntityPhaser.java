package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.SpawnUtil;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityPhaser extends EntityLeveledMob implements IAttack, IAnimatable {
    private Consumer<EntityLivingBase> prevAttacks;

    private final String WALK_ANIM = "move";
    private final String IDLE_ANIM = "idle";
    private final String SPIN_ANIM = "spinMove";

    private static DataParameter<Boolean> SPINNING = EntityDataManager.createKey(EntityPhaser.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityPhaser.class, DataSerializers.BOOLEAN);
    private AnimationFactory factory = new AnimationFactory(this);

    public boolean motionless;

    public EntityPhaser(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.0f);
    }
    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 10, 9F, 0.3F));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));

    }
    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(SPINNING, Boolean.valueOf(false));
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));

    }
    public void setSpinning(boolean value) {this.dataManager.set(SPINNING, Boolean.valueOf(value));}
    public boolean isSpinning() {return this.dataManager.get(SPINNING);}
    public void setFightMode(boolean value) {this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));}
    public boolean isFightMode(){return this.dataManager.get(FIGHT_MODE);}

    public boolean spinUpdate;

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(spinUpdate) {
            AxisAlignedBB box = getEntityBoundingBox().grow(1.20, 0.1, 1.20).offset(0, 0.1, 0);
            ModUtils.destroyLowGradeBlocksInAABB(box, world, this);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(motionless) {
            this.motionX = 0;
            this.motionZ = 0;
        }
        EntityLivingBase target = this.getAttackTarget();
        if (target != null && !this.isFightMode()) {
            double distSq = this.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
            double distance = Math.sqrt(distSq);
            if(distance < 7) {
                double d0 = (this.posX - target.posX) * 0.030;
                double d1 = (this.posY - target.posY) * 0.01;
                double d2 = (this.posZ - target.posZ) * 0.030;
                this.addVelocity(d0, d1, d2);

            }
        }

    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        if(!this.isFightMode()) {
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(spinAttack));
            double[] weights = {
                    (distance < 10) ? 1/distance : 0
            };
            prevAttacks = ModRandom.choice(attacks, rand, weights).next();
            prevAttacks.accept(target);
        }
        return (prevAttacks == spinAttack) ? 200 : 10;
    }

    private Consumer<EntityLivingBase> spinAttack = (target)-> {
        this.setFightMode(true);
        this.setSpinning(true);
        addEvent(()-> {
            motionless = true;
            Vec3d targetedPosition = target.getPositionVector();
            float distance = getDistance(target);

            addEvent(()-> {
                motionless = false;
               ModUtils.leapTowards(this, targetedPosition, (float) (0.2 * distance), 0.3f);
            }, 12);
        }, 0);
        addEvent(()-> {
            for(int i = 0; i < 30; i += 5) {
                addEvent(()-> {
                    Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1.5, 0)));
                    DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                            .directEntity(this)
                            .build();
                    float damage = getAttack();
                    ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                    playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
                }, i);
            }
            spinUpdate = true;
        }, 15);

        addEvent(()-> spinUpdate = false ,45);

        addEvent(()-> {
            this.setSpinning(false);
            this.setFightMode(false);
        }, 60);
    };

    @Override
    public boolean getCanSpawnHere() {
        if(!SpawnUtil.isDay(world)) {
            return super.getCanSpawnHere();
        }
        return false;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "phaser_idle", 0, this::predicateIdle));
        animationData.addAnimationController(new AnimationController(this, "phaser_abil", 0, this::predicateAttack));
    }

    private<E extends IAnimatable>PlayState predicateIdle(AnimationEvent<E> event) {
        if(!this.isFightMode()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(WALK_ANIM, true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    private<E extends IAnimatable>PlayState predicateAttack(AnimationEvent<E> event) {

            if (this.isSpinning()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation(SPIN_ANIM, false));
                return PlayState.CONTINUE;
            }

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

        super.readEntityFromNBT(compound);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {return SoundEvents.ENTITY_SKELETON_HURT;}

    protected SoundEvent getDeathSound(DamageSource damageSourceIn) {return SoundEvents.ENTITY_SKELETON_DEATH;}
}
