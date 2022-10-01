package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIJumpAtTarget;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class EntityMaelstromNavigator extends EntityMaelstromMob implements IAnimatable, IAttack, IElement {
    /**
     * The Navigator, specifically for later use, but is intended for Raids only.
     * @param worldIn
     */
    private AnimationFactory factory = new AnimationFactory(this);
    public final String IDLE_ANIM = "idle";
    public final String ATTACK_ANIM = "attack";
    public final String RALLY_ANIM = "rally";

    public EntityMaelstromNavigator(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.8f);

    }
    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0f, 5, 3f, 0.5f));
        this.tasks.addTask(0, new AIJumpAtTarget(this, 0.4f, 0.5f));

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
        if (id == ModUtils.PARTICLE_BYTE) {
            if (this.getElement().equals(Element.NONE)) {
                ParticleManager.spawnMaelstromPotionParticle(world, rand, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), false);
            }

            ParticleManager.spawnEffect(world, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), getElement().particleColor);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
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
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {


        this.setfightMode(true);
        ModUtils.leapTowards(this, this.getAttackTarget().getPositionVector(), 0.4f, 0.3f);
        addEvent(() -> {
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec());
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            ModUtils.handleAreaImpact(0.8f, (e) -> this.getAttack(), this, pos, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.20f, 0, false);
        }, 10);

        addEvent(() -> EntityMaelstromNavigator.super.setfightMode(false), 14);
        return this.isSpellCasting() ? 60 : 20;
    }
    AtomicBoolean Start = new AtomicBoolean(false);
    @Override
    public void onUpdate() {
        super.onUpdate();
        RallyCooldown--;
        if (RallyCooldown == 0) {
            System.out.println("Set to 3000");
            RallyCooldown = 3000;
        }
        if (RallyCooldown == 2900) {
            Start.set(true);
        }
        if (Start.get() == true) {
            this.setSpellCasting(true);
            addEvent(() -> {


                for (EntityShade Shade: world.getEntitiesWithinAABB(EntityShade.class, this.getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D))) {
                    for (EntityAITasks.EntityAITaskEntry Task : Shade.tasks.taskEntries) {
                        if (Task.action instanceof EntityAIFollow) {

                            Follow = true;

                        }
                    }

                }}, 35);
            addEvent(() -> Start.set(false), 60);
            addEvent(()-> EntityMaelstromNavigator.super.setSpellCasting(false), 60);
        }
        if (this.isSpellCasting()) {
            motionZ = 0;
            motionX = 0;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "navigator_idle", 0, this::predicateNavigatorIdle));
    animationData.addAnimationController(new AnimationController(this, "navigator_attack", 0, this::predicateNavAttack));
    }

    public int RallyCooldown = 3000;
    public boolean Follow = false;




    private <E extends IAnimatable>PlayState predicateNavigatorIdle(AnimationEvent<E> event) {
        if (!this.isfightMode() && !isSpellCasting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }

        return PlayState.CONTINUE;
    }
    private <E extends IAnimatable>PlayState predicateNavAttack(AnimationEvent<E> event) {
        if (this.isfightMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ATTACK_ANIM, false));
            return PlayState.CONTINUE;
        }
        if (this.isSpellCasting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(RALLY_ANIM, false));
            addEvent(() -> {
                playSound(SoundsHandler.ENTITY_NAVIGATOR_HORN, 1.0F, 1.0F / getRNG().nextFloat() * 0.8F + 0.4F);
            }, 35);
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
