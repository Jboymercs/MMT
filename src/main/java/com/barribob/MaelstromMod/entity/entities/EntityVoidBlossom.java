package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.*;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSporeBomb;
import com.barribob.MaelstromMod.entity.projectile.ProjectileVoidLeaf;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityVoidBlossom extends EntityLeveledMob implements IAnimatable, IAttack {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.NOTCHED_6));
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
    private static final DataParameter<Boolean> SPORE_ATTACK = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private Consumer<EntityLivingBase> prevAttacks;

    public EntityVoidBlossom(World worldIn) {
        super(worldIn);
        this.setSize(2.0f, 9.0f);
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
        this.dataManager.register(SPORE_ATTACK, Boolean.valueOf(false));
    }
    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public void setSpikeAttack(boolean value) {this.dataManager.set(SPIKE_ATTACK, Boolean.valueOf(value));}
    public void setSpikeWave(boolean value) {this.dataManager.set(SPIKE_WAVE, Boolean.valueOf(value));}
    public void setLeafAttack(boolean value) {this.dataManager.set(LEAF_ATTACK, Boolean.valueOf(value));}
    public void setSporeAttack(boolean value) {this.dataManager.set(SPORE_ATTACK, Boolean.valueOf(value));
    }
    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public boolean isSpikeAttack() {return this.dataManager.get(SPIKE_ATTACK);}
    public boolean isSpikeWave() {return this.dataManager.get(SPIKE_WAVE);}
    public boolean isLeafAttack() {return this.dataManager.get(LEAF_ATTACK);}
    public boolean isSporeAttack() {return this.dataManager.get(SPORE_ATTACK);}

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
        if(this.isSporeAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_LAUNCH_SPORE, false));
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
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(getHealth() / getMaxHealth());
        EntityLivingBase target = this.getAttackTarget();
        if(target != null && !this.isSporeAttack() && !this.isSpikeWave()) {
            this.getLookHelper().setLookPositionWithEntity(target, 0.8f, 0.8f);
        }
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        double HealthChange = this.getHealth() / this.getMaxHealth();
        if(!this.isFightMode()) {
            List<Consumer<EntityLivingBase>> attacksMelee = new ArrayList<>(Arrays.asList(spikeAttack, spikeWave, leafAttack, sporeBomb));
            double[] weights = {
                    (distance < 20 && prevAttacks != spikeAttack) ? distance * 0.02 : 0, // Spike Attack Simple
                    (distance < 20 && prevAttacks != spikeWave) ? distance * 0.02 : 0, //Spike Wave
                    (distance < 20 && prevAttacks != leafAttack && HealthChange < 0.5) ? distance * 0.02 : 0, // Leaf Attack only active at 50% below Health
                    (distance < 20 && prevAttacks != sporeBomb && HealthChange < 0.5) ? distance * 0.02 : 0 //Spore Bomb Attack active only at 50% below Health
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
      addEvent(()-> playSound(SoundsHandler.BLOSSOM_BURROW, 1.0f, 1.0f), 25);
        addEvent(()-> new ActionShortRangeWave().performAction(this, target), 30);
        addEvent(()-> new ActionMediumRangeWave().performAction(this, target), 60);
        addEvent(()-> new ActionLongRangeWave().performAction(this, target), 90);
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
      addEvent(()-> new ActionLeafAttack(leafProjectileSupplier, 0.8f).performAction(this, target), 50);
        addEvent(()-> new ActionLeafAttack(leafProjectileSupplier, 0.8f).performAction(this, target), 74);
      addEvent(()-> {
        this.setFightMode(false);
        this.setLeafAttack(false);
      }, 106);
    };
    //Spore bomb
    private Consumer<EntityLivingBase> sporeBomb = (target) -> {
        this.setFightMode(true);
        this.setSporeAttack(true);
        addEvent(()-> playSound(SoundsHandler.SPORE_PREPARE, 1.0f, 1.0f), 30);
        addEvent(()-> {
            ProjectileSporeBomb projectile = new ProjectileSporeBomb(this.world);
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(12.0D));
            Vec3d targetPos = target.getPositionVector().add(ModUtils.yVec(14));
            Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.7);
            projectile.setVelocity(velocity.x, velocity.y -0.5, velocity.z);
            projectile.setPosition(pos.x, pos.y, pos.z);
            projectile.setTravelRange(40F);
            world.spawnEntity(projectile);
        }, 46);

        addEvent(()-> {
            this.setFightMode(false);
            this.setSporeAttack(false);
        }, 60);
    };


    //Doesn't work as of now 20230621
    public BlockPos findSurface(BlockPos targetPos) {
        BlockPos statePos = new BlockPos(0, 0, 0).add(targetPos);
        if(world.isAirBlock(statePos) && !world.isBlockFullCube(statePos.down())) {
            statePos.down();
            if(world.isAirBlock(statePos) && !world.isBlockFullCube(statePos.down())) {
                statePos.down();
                if(world.isAirBlock(statePos) && !world.isBlockFullCube(statePos.down())) {
                    statePos.down();
                    if(world.isAirBlock(statePos) && !world.isBlockFullCube(statePos.down())) {
                        statePos.down();
                    }
                }
            }
        } else {
           return new BlockPos(statePos.getX(), statePos.getY(), statePos.getZ());
        }

        return statePos;
    }

    public void spawnSpikeAction() {
        EntityLivingBase target = this.getAttackTarget();
        //1
        if(target != null) {
            EntityVoidSpike spike = new EntityVoidSpike(this.world);
            BlockPos area = target.getPosition();
            this.findSurface(area);
            spike.setPosition(area.getX(), area.getY(), area.getZ());
            world.spawnEntity(spike);
            //2
            EntityVoidSpike spike2 = new EntityVoidSpike(this.world);
            BlockPos area2 = new BlockPos(target.posX + 1, target.posY, target.posZ);
            this.findSurface(area2);
            spike2.setPosition(area2.getX(), area2.getY(), area2.getZ());
            world.spawnEntity(spike2);
            //3
            EntityVoidSpike spike3 = new EntityVoidSpike(this.world);
            BlockPos area3 = new BlockPos(target.posX - 1, target.posY, target.posZ);
            this.findSurface(area3);
            spike3.setPosition(area3.getX(), area3.getY(), area3.getZ());
            world.spawnEntity(spike3);
            //4
            EntityVoidSpike spike4 = new EntityVoidSpike(this.world);
            BlockPos area4 = new BlockPos(target.posX , target.posY, target.posZ + 1);
            this.findSurface(area4);
            spike4.setPosition(area4.getX(),area4.getY(), area4.getZ());
            world.spawnEntity(spike4);
            //5
            EntityVoidSpike spike5 = new EntityVoidSpike(this.world);
            BlockPos area5 = new BlockPos(target.posX , target.posY, target.posZ - 1);
            this.findSurface(area5);
            spike5.setPosition(area5.getX(), area5.getY(), area5.getZ());
            world.spawnEntity(spike5);
            //6
            EntityVoidSpike spike6 = new EntityVoidSpike(this.world);
            BlockPos area6 = new BlockPos(target.posX + 1, target.posY, target.posZ + 1);
            this.findSurface(area6);
            spike6.setPosition(area6.getX(), area6.getY(), area6.getZ());
            world.spawnEntity(spike6);
            //7
            EntityVoidSpike spike7 = new EntityVoidSpike(this.world);
            BlockPos area7 = new BlockPos(target.posX +1 , target.posY, target.posZ - 1);
            this.findSurface(area7);
            spike7.setPosition(area7.getX(), area7.getY(), area7.getZ());
            world.spawnEntity(spike7);
            //8
            EntityVoidSpike spike8 = new EntityVoidSpike(this.world);
            BlockPos area8 = new BlockPos(target.posX -1, target.posY, target.posZ - 1);
            this.findSurface(area8);
            spike8.setPosition(area8.getX(), area8.getY(), area8.getZ());
            world.spawnEntity(spike8);
            //9
            EntityVoidSpike spike9 = new EntityVoidSpike(this.world);
            BlockPos area9 = new BlockPos(target.posX -1, target.posY, target.posZ + 1);
            this.findSurface(area9);
            spike9.setPosition(area9.getX(), area9.getY(), area9.getZ());
            world.spawnEntity(spike9);
            //10
            EntityVoidSpike spike10 = new EntityVoidSpike(this.world);
            BlockPos area10 = new BlockPos(target.posX +2, target.posY, target.posZ );
            this.findSurface(area10);
            spike10.setPosition(area10.getX(), area10.getY(), area10.getZ());
            world.spawnEntity(spike10);
            //11
            EntityVoidSpike spike11 = new EntityVoidSpike(this.world);
            BlockPos area11 = new BlockPos(target.posX - 2, target.posY, target.posZ );
            this.findSurface(area11);
            spike11.setPosition(area11.getX(), area11.getY(), area11.getZ());
            world.spawnEntity(spike11);
            //12
            EntityVoidSpike spike12 = new EntityVoidSpike(this.world);
            BlockPos area12 = new BlockPos(target.posX , target.posY, target.posZ + 2);
            this.findSurface(area12);
            spike12.setPosition(area12.getX(), area12.getY(), area12.getZ());
            world.spawnEntity(spike12);
            //13
            EntityVoidSpike spike13 = new EntityVoidSpike(this.world);
            BlockPos area13 = new BlockPos(target.posX , target.posY, target.posZ -2);
            this.findSurface(area13);
            spike13.setPosition(area13.getX(), area13.getY(), area13.getZ());
            world.spawnEntity(spike13);
        }
    }

    @Override
    public void setCustomNameTag(@Nonnull String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(@Nonnull EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(@Nonnull EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
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
            this.bossInfo.setName(this.getDisplayName());
        }
        super.readEntityFromNBT(compound);
    }
}
