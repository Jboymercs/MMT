package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
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

public class EntityShadeKnight extends EntityLeveledMob implements IAnimatable, IAttack {

    private final String WALK_ANIM = "walk";
    private final String IDLE_ANIM = "idle";
    private final String PIERCE_ANIM = "pierce";
    private final String SWING_ANIM = "swing";
    private final String LEAP_ANIM = "leap";

    private final String SUMMON_ANIM = "summon";

    private Consumer<EntityLivingBase> prevAttacks;


    public boolean motionless;

    private static DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> PIERCE = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> SWING = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> LEAP = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> SUMMON = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);
    public EntityShadeKnight(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.0f);
    }
    @Override
    public void handleStatusUpdate(byte id) {
        if(id == ModUtils.PARTICLE_BYTE) {
            ModUtils.circleCallback(1, 30, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y);
                ParticleManager.spawnSmoke2(world, this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.2, 0.1, 0.3))), ModColors.GREY, pos.normalize().scale(0.1).add(ModUtils.yVec(0)));
            });
        }

        super.handleStatusUpdate(id);
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        if(motionless) {
            this.motionX = 0;
            this.motionZ = 0;
        }
    }

    @Override
    protected void initAnimation() {
        this.setSummon(true);
        if(this.isSummon()) {
            this.motionX = 0;
            this.motionZ = 0;
            addEvent(()-> {
                this.setSummon(false);
            }, 18);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        if(!this.isFightMode()) {
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(swingAttack, pierceAttack, leapAttack));
            double[] weights = {
                    (distance < 3) ? 1.2/distance : 0,
                    (distance < 5) ? 1/distance : 0,
                    (distance > 5) ? 1/distance : 0
            };
            prevAttacks = ModRandom.choice(attacks, rand, weights).next();
            prevAttacks.accept(target);
        }
        return (distance > 7) ? 80 : 10;
    }
    private Consumer<EntityLivingBase> leapAttack = (target)-> {
      this.setFightMode(true);
      this.setLeapMode(true);
        float distance = getDistance(target);
      addEvent(()-> {
        ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.15 * distance), 0.5f);
      }, 10);

      addEvent(()-> {
          motionless = true;
          Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.3, 1.0, 0)));
          DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                  .directEntity(this)
                  .build();
          float damage = getAttack();
          ModUtils.handleAreaImpact(2.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
          playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.4f / getRNG().nextFloat() * 0.4f + 0.2f);
          world.setEntityState(this, ModUtils.PARTICLE_BYTE);
      }, 20);
      addEvent(()-> {
          world.setEntityState(this, ModUtils.PARTICLE_BYTE);
      }, 29);
      addEvent(()-> {
          motionless = false;
        this.setFightMode(false);
        this.setLeapMode(false);
      }, 40);
    };
    private Consumer<EntityLivingBase> pierceAttack = (target) -> {
        this.setFightMode(true);
        this.setPierce(true);
        addEvent(()-> {
        ModUtils.leapTowards(this, target.getPositionVector(), 0.3f, 0.2f);
        }, 9);

        addEvent(()-> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.8, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                    .directEntity(this).disablesShields()
                    .build();
            float damage = getAttack();
            ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.4f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 16);

        addEvent(() -> {
        this.setFightMode(false);
        this.setPierce(false);
        }, 23);
    };

    private Consumer<EntityLivingBase> swingAttack = (target)-> {
        this.setFightMode(true);
        this.setSwingMode(true);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.6, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                    .directEntity(this)
                    .build();
            float damage = getAttack();
            ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 12);

        addEvent(()-> {
            this.setSwingMode(false);
            this.setFightMode(false);
        }, 25);
    };

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(PIERCE, Boolean.valueOf(false));
        this.dataManager.register(SWING, Boolean.valueOf(false));
        this.dataManager.register(LEAP, Boolean.valueOf(false));
        this.dataManager.register(SUMMON, Boolean.valueOf(false));

    }

    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public void setPierce(boolean value) {this.dataManager.set(PIERCE, Boolean.valueOf(value));}
    public boolean isPierce() {return this.dataManager.get(PIERCE);}
    public void setSwingMode(boolean value) {this.dataManager.set(SWING, Boolean.valueOf(value));}
    public boolean isSwingMode() {return this.dataManager.get(SWING);}
    public void setLeapMode(boolean value) {this.dataManager.set(LEAP, Boolean.valueOf(value));}
    public boolean isLeapMode() {return this.dataManager.get(LEAP);}
    public void setSummon(boolean value){this.dataManager.set(SUMMON, Boolean.valueOf(value));}
    public boolean isSummon(){return this.dataManager.get(SUMMON);}

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 10, 9F, 0.1F));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

    @Override
    public boolean getCanSpawnHere() {
        if(!SpawnUtil.isDay(world)) {
            return super.getCanSpawnHere();
        }
        return false;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "knight_idle", 0, this::PredicateIdle));
        animationData.addAnimationController(new AnimationController(this, "knight_attacks", 0, this::predicateAttack));
        animationData.addAnimationController(new AnimationController(this, "knight_summon",0, this::predicateSUmmon));
    }
    //Handles the IDLE
    private <E extends IAnimatable>PlayState PredicateIdle(AnimationEvent<E> event) {
        if(!this.isSummon()){
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WALK_ANIM, true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }
    }
        return PlayState.CONTINUE;
    }
    // Handles the Attacks
    private <E extends IAnimatable>PlayState predicateAttack(AnimationEvent<E> event) {
        if(this.isFightMode()) {
            if(this.isSwingMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(SWING_ANIM, false));
                return PlayState.CONTINUE;
            }
            if(this.isPierce()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(PIERCE_ANIM, false));
                return PlayState.CONTINUE;
            }
            if(this.isLeapMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(LEAP_ANIM, false));
                return PlayState.CONTINUE;
            }
        }

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
    private <E extends IAnimatable>PlayState predicateSUmmon(AnimationEvent<E> event) {
        if(this.isSummon()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_ANIM, false));
            return PlayState.CONTINUE;
        }
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
    public final boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.isProjectile()) {
            return false;
        }
        EntityLivingBase target = this.getAttackTarget();
        if(target instanceof EntityShadeKnight) {
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {return SoundEvents.ENTITY_PLAYER_HURT;}

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.BLOCK_METAL_STEP, 1.0F, 1.0F);
        this.playSound(SoundEvents.BLOCK_METAL_STEP, 1.0F, 1.0F);
    }
}
