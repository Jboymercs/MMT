package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIJumpAtTarget;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

public class EntityMaelstromShielder extends EntityMaelstromMob implements IAttack, IAnimatable, IElement {
    /**
     * The Shielder, first found in Lush Tier Dungeon or in Raids which will be unlocked after defeating the Crimson Kingdom
     */
    public final String IDLE_ANIM = "idle";
    public final String ATTACK_ANIM = "attack";
    public final String STAB_ATTACK = "stabAttack";
    private Consumer<EntityLivingBase> prevAttack;


    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMaelstromShielder(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.8f);
    }

    private final Consumer<EntityLivingBase> SimpleAttack = (target) -> {
        this.setStriking(true);
        this.setfightMode(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, this.getAttackTarget().getPositionVector(), 0.4f, 0.3f);
        }, 10);
        addEvent(() -> {
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec());
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            ModUtils.handleAreaImpact(0.8f, (e) -> this.getAttack(), this, pos, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.20f, 0, false);

        }, 13);


        addEvent(() -> EntityMaelstromShielder.super.setStriking(false), 20);
        addEvent(() -> EntityMaelstromShielder.super.setfightMode(false), 60);

    };

    private final Consumer<EntityLivingBase> StabAttack = (target) -> {
        this.setfightMode(true);
        this.setArcleap(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, this.getAttackTarget().getPositionVector(), 0.7f, 0.2f);

        },7);
        addEvent(() ->{
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec());
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            ModUtils.handleAreaImpact(1.0f, (e) -> this.getAttack(), this, pos, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.40f, 0, false);


        }, 20);

        addEvent(() -> EntityMaelstromShielder.super.setArcleap(false), 30);
        addEvent(() -> EntityMaelstromShielder.super.setfightMode(false), 60);

    };
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
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        if (!this.isfightMode()) {
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(SimpleAttack, StabAttack));
            double[] weights = {
                    1,
                    1
            };
            prevAttack = ModRandom.choice(attacks, rand, weights).next();
            prevAttack.accept(target);
        }
        return 60;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0f, 60, 3f, 0.3f));


    }

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "shielder_idle", 0, this::predicateShielder));
    animationData.addAnimationController(new AnimationController(this, "shielder_attack", 0, this::predicateSAttacks));


    }
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0.0F && this.canBlockDamageSource(source)) {
            this.damageShield(amount);

            if (!source.isProjectile()) {
                Entity entity = source.getImmediateSource();

                if (entity instanceof EntityLivingBase) {
                    this.blockUsingShield((EntityLivingBase) entity);
                }
            }
            this.playSound(SoundEvents.BLOCK_ANVIL_FALL, 1.0f, 0.6f + ModRandom.getFloat(0.2f));

            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    private boolean canBlockDamageSource(DamageSource damageSourceIn) {
        if (!damageSourceIn.isUnblockable() && !this.isfightMode()) {
            Vec3d vec3d = damageSourceIn.getDamageLocation();

            if (vec3d != null) {
                Vec3d vec3d1 = this.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(this.posX, this.posY, this.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                return vec3d2.dotProduct(vec3d1) < 0.0D;
            }
        }

        return false;
    }

    private <E extends IAnimatable> PlayState predicateShielder(AnimationEvent<E> event) {

        if (!this.isfightMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }


        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable>PlayState predicateSAttacks(AnimationEvent<E> event) {
        if (this.isStriking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ATTACK_ANIM, false));
            return PlayState.CONTINUE;
        }
        if (this.isArcLeaping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(STAB_ATTACK, false));
            return PlayState.CONTINUE;
        }

        event.getController().markNeedsReload();
        return PlayState.STOP;
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
    public AnimationFactory getFactory() {
        return factory;
    }
}
