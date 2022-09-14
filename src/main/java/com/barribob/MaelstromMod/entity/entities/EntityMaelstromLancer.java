package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIJumpAtTarget;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromLancer;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class EntityMaelstromLancer extends EntityMaelstromMob implements IAttack {
    public EntityMaelstromLancer(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.8f);
    }

    @Override
    protected void initAnimation() {
        List<List<AnimationClip<ModelMaelstromLancer>>> animationSpear = new ArrayList<List<AnimationClip<ModelMaelstromLancer>>>();
        List<AnimationClip<ModelMaelstromLancer>> rightArmXStream = new ArrayList<AnimationClip<ModelMaelstromLancer>>();
        List<AnimationClip<ModelMaelstromLancer>> leftArmZStream = new ArrayList<AnimationClip<ModelMaelstromLancer>>();
        List<AnimationClip<ModelMaelstromLancer>> bodyStream = new ArrayList<AnimationClip<ModelMaelstromLancer>>();
        List<AnimationClip<ModelMaelstromLancer>> spearStream = new ArrayList<AnimationClip<ModelMaelstromLancer>>();
        List<AnimationClip<ModelMaelstromLancer>> leftArmXStream = new ArrayList<AnimationClip<ModelMaelstromLancer>>();
        List<AnimationClip<ModelMaelstromLancer>> leftForearmXStream = new ArrayList<AnimationClip<ModelMaelstromLancer>>();

        BiConsumer<ModelMaelstromLancer, Float> rightArmX = (model, f) -> model.rightArm.rotateAngleX = f;
        BiConsumer<ModelMaelstromLancer, Float> leftArmZ = (model, f) -> model.leftArm.rotateAngleZ = f;
        BiConsumer<ModelMaelstromLancer, Float> bodyX = (model, f) -> model.body.rotateAngleX = f;
        BiConsumer<ModelMaelstromLancer, Float> spearX = (model, f) -> model.spear.rotateAngleX = -f;
        BiConsumer<ModelMaelstromLancer, Float> leftArmX = (model, f) -> model.leftArm.rotateAngleX = -f;
        BiConsumer<ModelMaelstromLancer, Float> leftForearmX = (model, f) -> model.leftForearm.rotateAngleX = f;

        leftArmXStream.add(new AnimationClip(6, 0, -65, leftArmX));
        leftArmXStream.add(new AnimationClip(4, -65, -65, leftArmX));
        leftArmXStream.add(new AnimationClip(5, -65, 80, leftArmX));
        leftArmXStream.add(new AnimationClip(10, 80, 80, leftArmX));
        leftArmXStream.add(new AnimationClip(5, 80, 0, leftArmX));

        leftArmZStream.add(new AnimationClip(10, 0, 0, leftArmZ));
        leftArmZStream.add(new AnimationClip(5, 0, -20, leftArmZ));
        leftArmZStream.add(new AnimationClip(10, -20, -20, leftArmZ));
        leftArmZStream.add(new AnimationClip(5, -20, 0, leftArmZ));

        bodyStream.add(new AnimationClip(6, 0, 0, bodyX));
        bodyStream.add(new AnimationClip(4, 0, -15, bodyX));
        bodyStream.add(new AnimationClip(5, -15, 30, bodyX));
        bodyStream.add(new AnimationClip(10, 30, 30, bodyX));
        bodyStream.add(new AnimationClip(5, 30, 0, bodyX));

        spearStream.add(new AnimationClip(10, 0, 0, spearX));
        spearStream.add(new AnimationClip(5, 0, -50, spearX));
        spearStream.add(new AnimationClip(10, -50, -50, spearX));
        spearStream.add(new AnimationClip(5, -50, 0, spearX));

        leftForearmXStream.add(new AnimationClip(10, -50, -50, leftForearmX));
        leftForearmXStream.add(new AnimationClip(5, -50, 0, leftForearmX));
        leftForearmXStream.add(new AnimationClip(10, 0, 0, leftForearmX));
        leftForearmXStream.add(new AnimationClip(5, 0, -50, leftForearmX));

        rightArmXStream.add(new AnimationClip(6, 0, -40, rightArmX));
        rightArmXStream.add(new AnimationClip(4, -40, -40, rightArmX));
        rightArmXStream.add(new AnimationClip(5, -40, 40, rightArmX));
        rightArmXStream.add(new AnimationClip(10, 40, 40, rightArmX));
        rightArmXStream.add(new AnimationClip(5, 40, 0, rightArmX));

        animationSpear.add(rightArmXStream);
        animationSpear.add(leftArmZStream);
        animationSpear.add(bodyStream);
        animationSpear.add(spearStream);
        animationSpear.add(leftArmXStream);
        animationSpear.add(leftForearmXStream);

        this.currentAnimation = new StreamAnimation<ModelMaelstromLancer>(animationSpear) {
            @Override
            public void setModelRotations(ModelMaelstromLancer model, float limbSwing, float limbSwingAmount, float partialTicks) {
                model.leftArm.offsetY = (float) Math.cos(Math.toRadians(ticksExisted * 4)) * 0.05f;
                model.rightArm.offsetY = (float) Math.cos(Math.toRadians(ticksExisted * 4)) * 0.05f;
                super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
            }
        };
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<EntityMaelstromLancer>(this, 1.0f, 10, 5, 0.5f, 20.0f));
        this.tasks.addTask(0, new AIJumpAtTarget(this, 0.4f, 0.5f));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsHandler.ENTITY_SHADE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsHandler.ENTITY_SHADE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsHandler.ENTITY_SHADE_HURT;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (rand.nextInt(20) == 0) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            getCurrentAnimation().startAnimation();
        } else if (id == ModUtils.PARTICLE_BYTE) {
            if (this.getElement().equals(Element.NONE)) {
                ParticleManager.spawnMaelstromPotionParticle(world, rand, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), false);
            }

            ParticleManager.spawnEffect(world, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), getElement().particleColor);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public void onLivingUpdate() {
        if (!world.isRemote && this.isLeaping()) {
            Vec3d dir = this.getLookVec().scale(2.2);
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(0.8f)).add(dir);
            ModUtils.handleAreaImpact(0.2f, (e) -> this.getAttack(), this, pos, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.20f, 0, false);
        }
        super.onLivingUpdate();
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        this.world.setEntityState(this, (byte) 4);

        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 0.9f, 0.3f);
            this.setLeaping(true);
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, ModRandom.getFloat(0.1f) + 1.2f);
        }, 10);

        return 40;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    }
}
