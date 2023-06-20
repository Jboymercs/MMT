package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityAbberrant;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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

public class EntityVoidBlossom extends EntityLeveledMob implements IAnimatable, IAttack {
    private final String ANIM_IDLE_TARGET = "idle";
    private final String ANIM_IDLE_NONTARGET = ""; // Might need one more to hold on frame for this to stay in state
    private final String ANIM_SPIKE_SMALL = "spike";
    private final String ANIM_SPIKE_AOE = "spike_wave";
    private final String ANIM_LAUNCH_SPORE = "spore";
    private final String ANIM_LEAF_STRIKE = "leaf_blade";
    private final String ANIM_DEATH = "death";
    private final String ANIM_SUMMON = "spawn";
    private static final DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SPIKE_ATTACK = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private Consumer<EntityLivingBase> prevAttacks;

    public EntityVoidBlossom(World worldIn) {
        super(worldIn);
        this.setSize(2.0f, 2.0f);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(SPIKE_ATTACK, Boolean.valueOf(false));
    }
    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public void setSpikeAttack(boolean value) {this.dataManager.set(SPIKE_ATTACK, Boolean.valueOf(value));}
    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public boolean isSpikeAttack() {return this.dataManager.get(SPIKE_ATTACK);}

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "blossom_idle_controller", 0, this::predicateIdle));
    animationData.addAnimationController(new AnimationController(this, "blossom_attack", 10, this::predicateAttack));
    animationData.addAnimationController(new AnimationController(this, "blossom_summon_death", 10, this::predicateSpawn));
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 0, 40, 20F, 0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

    private <E extends IAnimatable>PlayState predicateIdle(AnimationEvent<E> event) {

            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE_TARGET, true));

        return PlayState.CONTINUE;
    }
    private <E extends IAnimatable> PlayState predicateAttack(AnimationEvent<E> event) {
        if(this.isSpikeAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_SPIKE_SMALL, false));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
    private <E extends IAnimatable>PlayState predicateSpawn(AnimationEvent<E> event) {
        return PlayState.STOP;
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        if(!this.isFightMode()) {
            List<Consumer<EntityLivingBase>> attacksMelee = new ArrayList<>(Arrays.asList(spikeAttack));
            double[] weights = {
                    (distance < 20) ? distance * 0.02 : 0 // Spike Attack Simple
            };
            prevAttacks = ModRandom.choice(attacksMelee, rand, weights).next();
            prevAttacks.accept(target);
        }
        return 40;
    }

    //Spike Attack Simple
    private Consumer<EntityLivingBase> spikeAttack = (target)-> {
        this.setFightMode(true);
        this.setSpikeAttack(true);

        addEvent(()-> {
        this.setFightMode(false);
        this.setSpikeAttack(false);
        }, 118);
    };
}
