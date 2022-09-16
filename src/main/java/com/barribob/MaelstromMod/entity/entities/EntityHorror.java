package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAIWanderWithGroup;
import com.barribob.MaelstromMod.entity.animation.AnimationManager;
import com.barribob.MaelstromMod.entity.animation.AnimationManagerServer;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.util.IAcceleration;
import com.barribob.MaelstromMod.init.MMAnimations;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;

import java.util.logging.Logger;

import static java.lang.Math.abs;

/**
 * Model, Animations, and Textures done by GDrayn, AI revision done by UnseenDontRun.
 */
public class EntityHorror extends EntityMaelstromMob implements IAnimatable {
    public static final float PROJECTILE_INACCURACY = 6;
    public static final float PROJECTILE_VELOCITY = 1.2f;
    public static final float PROJECTILE_SPEED = 1.0f;


    private AnimationFactory factory = new AnimationFactory(this);

    public EntityHorror(World worldIn) {
        super(worldIn);
        this.setSize(1.3F, 1.3F);


    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();


    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }



    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }
    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 80, 20.0f, 0.5f));


    }
    /**
     * Spawns smoke out of the middle of the entity
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            for (int i = 0; i < 5; i++) {
                ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX + ModRandom.getFloat(0.4f), this.posY + 1.5, this.posZ + ModRandom.getFloat(0.4f)), true);
            }

        }


    }


    //Redone for playing nice with Geckolib and overall change up the whole ideal of the Cauldron AI and attack methods


    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (!world.isRemote) {
            ProjectileHorrorAttack projectile = new ProjectileHorrorAttack(this.world, this, this.getAttack());
            projectile.posY = this.posY + this.getEyeHeight() + 0.8f;
            double d0 = target.posY + target.getEyeHeight() - 0.9f;
            double d1 = target.posX - this.posX;
            double d2 = d0 - projectile.posY;
            double d3 = target.posZ - this.posZ;
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.6F;
            projectile.shoot(d1, d2 + f, d3, this.PROJECTILE_SPEED, this.PROJECTILE_INACCURACY);
            this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

        }

    }



    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsHandler.ENTITY_HORROR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsHandler.ENTITY_HORROR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsHandler.ENTITY_HORROR_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 0.25f;
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(MMAnimations.WalkController(this));
        animationData.addAnimationController(MMAnimations.IdleController(this));



    }


}
