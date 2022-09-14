package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGeyser;
import com.barribob.MaelstromMod.entity.action.ActionGolemSlam;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationCliffGolem;
import com.barribob.MaelstromMod.entity.animation.AnimationGroundFistBump;
import com.barribob.MaelstromMod.entity.render.RenderAzureGolem;
import com.barribob.MaelstromMod.entity.render.RenderCliffGolem;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityCliffGolem extends EntityLeveledMob implements IRangedAttackMob {
    private ComboAttack attackHandler = new ComboAttack();
    private byte groundPoundByte = 4;
    private byte geyserByte = 5;

    public EntityCliffGolem(World worldIn) {
        super(worldIn);
        this.setSize(1.4F * RenderCliffGolem.AZURE_GOLEM_SIZE, 2.7F * RenderCliffGolem.AZURE_GOLEM_SIZE);
        if (!worldIn.isRemote) {
            attackHandler.setAttack(groundPoundByte, new ActionGolemSlam());
            attackHandler.setAttack(this.geyserByte, new ActionGeyser());
        }
        this.healthScaledAttackFactor = 0.2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation() {
        this.currentAnimation = new AnimationCliffGolem();
        attackHandler.setAttack(groundPoundByte, new ActionGolemSlam(), () -> new AnimationCliffGolem());
        attackHandler.setAttack(this.geyserByte, new ActionGeyser(), () -> new AnimationGroundFistBump());
    }

    @Override
    public float getRenderSizeModifier() {
        return RenderCliffGolem.AZURE_GOLEM_SIZE;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIRangedAttack<EntityCliffGolem>(this, 1f, 60, 15, 20.0f, 0.1f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }

    @Override
    protected float getSoundPitch() {
        return 0.9f + ModRandom.getFloat(0.1f);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.SWAMP_BOSS;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        this.attackHandler.getCurrentAttackAction().performAction(this, target);
    }

    @Override
    public void swingArm(EnumHand hand) {
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        if (swingingArms) {
            double distance = this.getDistanceSq(this.getAttackTarget().posX, getAttackTarget().getEntityBoundingBox().minY, getAttackTarget().posZ);
            double meleeDistance = 7;
            if (distance < Math.pow(meleeDistance, 2)) {
                attackHandler.setCurrentAttack(this.groundPoundByte);
            } else {
                attackHandler.setCurrentAttack(this.geyserByte);
            }

            this.world.setEntityState(this, attackHandler.getCurrentAttack());
            this.motionY = 0.63f;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == this.groundPoundByte || id == this.geyserByte) {
            this.currentAnimation = attackHandler.getAnimation(id);
            getCurrentAnimation().startAnimation();
            this.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0F, 1.0F);
        } else {
            super.handleStatusUpdate(id);
        }
    }
}
