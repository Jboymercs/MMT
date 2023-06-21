package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.*;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityAbberrant;
import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileVoidLeaf;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.collection.immutable.Stream;
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
import java.util.function.Supplier;

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
    private static final DataParameter<Boolean> SPIKE_WAVE = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEAF_ATTACK = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private Consumer<EntityLivingBase> prevAttacks;

    public EntityVoidBlossom(World worldIn) {
        super(worldIn);
        this.setSize(2.0f, 2.0f);
        this.setImmovable(true);
        this.setNoGravity(true);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(SPIKE_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SPIKE_WAVE, Boolean.valueOf(false));
        this.dataManager.register(LEAF_ATTACK, Boolean.valueOf(false));
    }
    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public void setSpikeAttack(boolean value) {this.dataManager.set(SPIKE_ATTACK, Boolean.valueOf(value));}
    public void setSpikeWave(boolean value) {this.dataManager.set(SPIKE_WAVE, Boolean.valueOf(value));}
    public void setLeafAttack(boolean value) {this.dataManager.set(LEAF_ATTACK, Boolean.valueOf(value));}
    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public boolean isSpikeAttack() {return this.dataManager.get(SPIKE_ATTACK);}
    public boolean isSpikeWave() {return this.dataManager.get(SPIKE_WAVE);}
    public boolean isLeafAttack() {return this.dataManager.get(LEAF_ATTACK);}

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "blossom_idle_controller", 0, this::predicateIdle));
    animationData.addAnimationController(new AnimationController(this, "blossom_attack", 0, this::predicateAttack));
    animationData.addAnimationController(new AnimationController(this, "blossom_summon_death", 0, this::predicateSpawn));
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 0, 60, 20F, 0F));
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
        if(this.isSpikeWave()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_SPIKE_AOE, false));
            return PlayState.CONTINUE;
        }
        if(this.isLeafAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_LEAF_STRIKE, false));
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
            List<Consumer<EntityLivingBase>> attacksMelee = new ArrayList<>(Arrays.asList(spikeAttack, spikeWave, leafAttack));
            double[] weights = {
                    (distance < 20 && prevAttacks != spikeAttack) ? distance * 0.02 : 0, // Spike Attack Simple
                    (distance < 20 && prevAttacks != spikeWave) ? distance * 0.02 : 0, //Spike Wave
                    (distance < 20 && prevAttacks != leafAttack) ? distance * 0.02 : 0 // Leaf Attack
            };
            prevAttacks = ModRandom.choice(attacksMelee, rand, weights).next();
            prevAttacks.accept(target);
        }
        return 60;
    }


    //Spike Attack Simple
    private Consumer<EntityLivingBase> spikeAttack = (target)-> {
        this.setFightMode(true);
        this.setSpikeAttack(true);
        addEvent(()-> {
            if(!world.isRemote) {
                for(int i = 0; i < 120; i += 40) {
                    addEvent(this::spawnSpikeAction, i);
                }
            }
        }, 25);


        addEvent(()-> {
        this.setFightMode(false);
        this.setSpikeAttack(false);
        }, 120);
    };

    //Spike Wave Initiator
    private Consumer<EntityLivingBase> spikeWave = (target)-> {
      this.setFightMode(true);
      this.setSpikeWave(true);
        addEvent(()-> new ActionShortRangeWave().performAction(this, target), 20);
        addEvent(()-> new ActionMediumRangeWave().performAction(this, target), 50);
        addEvent(()-> new ActionLongRangeWave().performAction(this, target), 80);
      addEvent(()-> {
          this.setFightMode(false);
          this.setSpikeWave(false);
      }, 104);
    };

    Supplier<Projectile> leafProjectileSupplier = () -> new ProjectileVoidLeaf(this.world, this, this.getAttack());
    //Leaf Attack
    private Consumer<EntityLivingBase> leafAttack = (target) -> {
      this.setFightMode(true);
      this.setLeafAttack(true);

      addEvent(()-> new ActionLeafAttack(leafProjectileSupplier, 0.8f).performAction(this, target), 28);
        addEvent(()-> new ActionLeafAttack(leafProjectileSupplier, 0.8f).performAction(this, target), 74);
      addEvent(()-> {
        this.setFightMode(false);
        this.setLeafAttack(false);
      }, 106);
    };

    public void spawnSpikeAction() {
        EntityLivingBase target = this.getAttackTarget();
        //1
        if(target != null) {
            EntityVoidSpike spike = new EntityVoidSpike(this.world);
            spike.setPosition(target.posX, target.posY, target.posZ);
            world.spawnEntity(spike);
            //2
            EntityVoidSpike spike2 = new EntityVoidSpike(this.world);
            spike2.setPosition(target.posX + 1, target.posY, target.posZ);
            world.spawnEntity(spike2);
            //3
            EntityVoidSpike spike3 = new EntityVoidSpike(this.world);
            spike3.setPosition(target.posX - 1, target.posY, target.posZ);
            world.spawnEntity(spike3);
            //4
            EntityVoidSpike spike4 = new EntityVoidSpike(this.world);
            spike4.setPosition(target.posX, target.posY, target.posZ + 1);
            world.spawnEntity(spike4);
            //5
            EntityVoidSpike spike5 = new EntityVoidSpike(this.world);
            spike5.setPosition(target.posX, target.posY, target.posZ - 1);
            world.spawnEntity(spike5);
            //6
            EntityVoidSpike spike6 = new EntityVoidSpike(this.world);
            spike6.setPosition(target.posX + 1, target.posY, target.posZ + 1);
            world.spawnEntity(spike6);
            //7
            EntityVoidSpike spike7 = new EntityVoidSpike(this.world);
            spike7.setPosition(target.posX + 1, target.posY, target.posZ - 1);
            world.spawnEntity(spike7);
            //8
            EntityVoidSpike spike8 = new EntityVoidSpike(this.world);
            spike8.setPosition(target.posX - 1, target.posY, target.posZ - 1);
            world.spawnEntity(spike8);
            //9
            EntityVoidSpike spike9 = new EntityVoidSpike(this.world);
            spike9.setPosition(target.posX - 1, target.posY, target.posZ + 1);
            world.spawnEntity(spike9);
            //10
            EntityVoidSpike spike10 = new EntityVoidSpike(this.world);
            spike10.setPosition(target.posX + 2, target.posY, target.posZ);
            world.spawnEntity(spike10);
            //11
            EntityVoidSpike spike11 = new EntityVoidSpike(this.world);
            spike11.setPosition(target.posX - 2, target.posY, target.posZ);
            world.spawnEntity(spike11);
            //12
            EntityVoidSpike spike12 = new EntityVoidSpike(this.world);
            spike12.setPosition(target.posX, target.posY, target.posZ + 2);
            world.spawnEntity(spike12);
            //13
            EntityVoidSpike spike13 = new EntityVoidSpike(this.world);
            spike13.setPosition(target.posX, target.posY, target.posZ - 2);
            world.spawnEntity(spike13);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        // Make sure we save as immovable to avoid some weird states
        if (!this.isImmovable()) {
            this.setImmovable(true);
            this.setPosition(0, 0, 0);// Setting any position teleports it back to the initial position
        }
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (this.hasCustomName()) {

        }
        super.readEntityFromNBT(compound);
    }
}
