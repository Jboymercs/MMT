package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGolemSlam;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationCliffGolem;
import com.barribob.MaelstromMod.entity.render.RenderAzureGolem;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.MMAnimations;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.immutable.Stream;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class EntityAzureGolem extends EntityLeveledMob implements IAttack, IAnimatable {

    private final String ANIM_PUNCH = "punch";
    private final String ANIM_JUMP = "jump";
    private final String ANIM_SMASH = "smash";
    private final String ANIM_DEATH = "death";
    private final String ANIM_IDLE = "idle";
    private final String ANIM_WALK = "walk";
    private final String ANIM_RUN = "run";
    private final String ANIM_IDLE_MOVEMENT = "idleTwo";
    protected static final DataParameter<Boolean> FIGHT_MODES = EntityDataManager.createKey(EntityAzureGolem.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> PUNCH_ATTACK = EntityDataManager.createKey(EntityAzureGolem.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> LEAP_ATTACK = EntityDataManager.createKey(EntityAzureGolem.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SMASH_ATTACK = EntityDataManager.createKey(EntityAzureGolem.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> RUNNING = EntityDataManager.createKey(EntityAzureGolem.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> IDLING = EntityDataManager.createKey(EntityAzureGolem.class, DataSerializers.BOOLEAN);



    public EntityAzureGolem(World worldIn) {
        super(worldIn);
        this.setSize(2.2F, 6.0F);
        this.isImmuneToExplosions();
        this.isImmuneToFire = true;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FIGHT_MODES, Boolean.valueOf(false));
        this.dataManager.register(PUNCH_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(LEAP_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SMASH_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(RUNNING, Boolean.valueOf(false));
        this.dataManager.register(IDLING, Boolean.valueOf(false));

    }

    public boolean isFightMode() {return this.dataManager.get(FIGHT_MODES);}
    public void setFightMode(boolean value) {this.dataManager.set(FIGHT_MODES, Boolean.valueOf(value));}
    public boolean isPunchAttack() {return this.dataManager.get(PUNCH_ATTACK);}
    public void setPunchAttack(boolean value) {this.dataManager.set(PUNCH_ATTACK, Boolean.valueOf(value));}
    public boolean isLeapAttack() {return this.dataManager.get(LEAP_ATTACK);}
    public void setLeapAttack(boolean value) {this.dataManager.set(LEAP_ATTACK, Boolean.valueOf(value));}
    public boolean isSmashAttack() {return this.dataManager.get(SMASH_ATTACK);}
    public void setSmashAttack(boolean value) {this.dataManager.set(SMASH_ATTACK, Boolean.valueOf(value));}

    public void setRunning(boolean value) {this.dataManager.set(RUNNING, Boolean.valueOf(value));}
    public boolean isRunning() {return this.dataManager.get(RUNNING);}

    public boolean isIdling() {return this.dataManager.get(IDLING);}
    public void setIdling(boolean value) {
        this.dataManager.set(IDLING, Boolean.valueOf(value));
    }
    private AnimationFactory factory = new AnimationFactory(this);



    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.3, 40, 4F, 0.3F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return this.world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
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
        return LootTableHandler.AZURE_GOLEM;
    }


    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {


            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
       animationData.addAnimationController(new AnimationController(this, "controller_attack", 0, this::PredicateAttack));
       animationData.addAnimationController(new AnimationController(this, "controller_idle", 0, this::PredicateIdle));
       animationData.addAnimationController(new AnimationController(this, "controller_run", 10, this::PredicateRun));
    }

    private <E extends IAnimatable>PlayState PredicateAttack(AnimationEvent<E> event) {
        if(this.isPunchAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_PUNCH, false));
            return PlayState.CONTINUE;
        }
        if(this.isSmashAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_SMASH, false));
            return PlayState.CONTINUE;
        }
        if(this.isLeapAttack()) {
            //Is Hold on Last Frame
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_JUMP, false));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
     return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState PredicateRun(AnimationEvent<E> event) {
        if(!this.isFightMode() && this.isRunning()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_RUN, true));
            return PlayState.CONTINUE;
        }
        if(!this.isFightMode() && this.isIdling()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE_MOVEMENT, false));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    private<E extends IAnimatable>PlayState PredicateIdle(AnimationEvent<E> event) {
        if(!this.isFightMode()) {

            if ((!(event.getLimbSwingAmount() > -0.10F && event.getLimbSwingAmount() < 0.10F)) && !this.isRunning()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_WALK, true));

            }
            else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE, true));
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private Consumer<EntityLivingBase> prevAttack;
    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        if(!this.isFightMode()) {
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(punchAttack));
            double[] weights = {
                    (distance < 4) ? 1/distance : 0 // Punch Attack
            };

            prevAttack = ModRandom.choice(attacks, rand, weights).next();

            prevAttack.accept(target);
        }
        return 40;
    }

    private final Consumer<EntityLivingBase> punchAttack = (target) -> {
        this.setFightMode(true);
        this.setPunchAttack(true);

        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2,1.2,0)));
            DamageSource source = ModDamageSource.builder().disablesShields().directEntity(this).type(ModDamageSource.MOB).build();
            float damage = this.getAttack();
        ModUtils.handleAreaImpact(2.5f, (e) -> damage, this, offset, source, 0.7F, 0, false);
        }, 25);
        addEvent(()-> {
        this.setFightMode(false);
        this.setPunchAttack(false);
        }, 55);
    };

    protected boolean jumpSafeInitiate;
    private final Consumer<EntityLivingBase> leapAttack = (target) -> {
      this.setFightMode(true);
      this.setLeapAttack(true);
      //Preps the Jump Animation and initiates the Leap
      addEvent(()-> {
          Vec3d targetedPos = target.getPositionVector().add(ModUtils.yVec(target.getEyeHeight()));
          double distSq = this.getDistanceSq(targetedPos.x, targetedPos.y, targetedPos.z);
          double distance = Math.sqrt(distSq);
        addEvent(()-> {
            ModUtils.leapTowards(this, targetedPos, 0.25f * ((float) distance), 0.05f * ((float) distance / 2));
            addEvent(()-> jumpSafeInitiate = true, 10);
        }, 18);
      }, 0);
    };

    protected boolean tickDelaySmash;
    private final Consumer<EntityLivingBase> hulkSmash = (target)-> {
        this.setSmashAttack(true);
        jumpSafeInitiate = false;
        addEvent(()-> tickDelaySmash = true, 3);
        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2,0,0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage = this.getAttack();
            float explosionFactor = 2.0f;
            ModUtils.handleAreaImpact(2.0f, (e)-> damage, this, offset, source, 0.5f, 0, false);
            world.newExplosion(this, offset.x, offset.y, offset.z, explosionFactor, false, true);
        }, 8);

        addEvent(()-> {
            this.setSmashAttack(false);
            this.setFightMode(false);
            tickDelaySmash = false;
        }, 46);
    };
    public int leapCoolDown = 200;
    public int idleCoolDown = 0;

    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityLivingBase target = this.getAttackTarget();
        //Idle Handler
        if(rand.nextInt(10) == 0 && target == null && idleCoolDown > 600 ) {
            this.setIdling(true);
            addEvent(()-> this.setIdling(false), 65);
            idleCoolDown = 0;
        } else {
            idleCoolDown++;
        }
        if(target != null) {
            this.setRunning(true);
        }
    if(!world.isRemote && !this.isBeingRidden() && target != null) {
        double distSq = this.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
        double distance = Math.sqrt(distSq);
        //Initiates the Jumping Part of the attack
        if(distance > 4 && distance < 16 && !this.isFightMode() && leapCoolDown > 200) {
            leapAttack.accept(target);
            leapCoolDown = 0;
        }
        //Adds to the cool down Timer, if it is not currently doing one of these functions
        if(!this.isLeapAttack() && !this.isSmashAttack()) {
            leapCoolDown++;
        }
        //Handles the Transistion between Jumping and Smashing
        if(this.isLeapAttack() && jumpSafeInitiate) {
            BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
            if(distance < 4) {
                hulkSmash.accept(target);
                this.setLeapAttack(false);
            }
            //This checks if there is ground below the Golem, if there is it will initiate it's hulk smash early
            if(!world.isAirBlock(pos.down())) {
                hulkSmash.accept(target);
                this.setLeapAttack(false);
            }
        }
        if(this.isSmashAttack() && tickDelaySmash || this.isPunchAttack()) {
            this.motionX = 0;
            this.motionZ = 0;
        }
        if(this.isLeapAttack()) {
            AxisAlignedBB breakBlocks = getEntityBoundingBox().grow(1.2, 0, 1.2).offset(0, 0.1, 0);
            ModUtils.destroyBlocksInAABB(breakBlocks, world, this);
        }

    }
        else {
            this.setRunning(false);
    }
    }
}
