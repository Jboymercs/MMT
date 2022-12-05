package com.barribob.MaelstromMod.entity.entities.overworld;

import akka.japi.pf.FI;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMonolithFireball;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.particle.ModParticle;
import com.barribob.MaelstromMod.renderer.ITarget;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * The first vanilla dimension boss to be implemented in, A magical fiery knight that derives it's magic from chaotic energy.
 */
public class EntityNetherKnight extends EntityLeveledMob implements IAttack, IAnimatable, IEntityMultiPart {

    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    private AnimationFactory factory = new AnimationFactory(this);
    /**
     * One of the Nether Bosses for the vanilla dimensions, these guys will be less complex.
     */

    private static final DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> STAB_MODE = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SWING_MODE = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> SUMMON_MODE = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FIRE_RINGS = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> AOE_STRIKE = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEAP_ATTACK = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private final String WALK_ANIM = "walk";
    private final String IDLE_ANIM = "idle";
    private final String SIMPLE_STRIKE = "swing";
    private final String STAB_ATTACK = "stab";
    private final String AOE_ATTACK = "aoe";
    private final String SUMMON_METOERS = "summon";
    private final String SUMMON_FIRE_RINGS = "fire_rings";
    private final String LEAP_ANIM = "leap";

    private final MultiPartEntityPart frontCore = new MultiPartEntityPart(this, "frontCore", 0.5f, 0.5f);
    private final MultiPartEntityPart backCore = new MultiPartEntityPart(this, "backCore", 0.5f, 0.5f);
    private final MultiPartEntityPart model = new MultiPartEntityPart(this, "model", 1.0f, 3.4f);


    private Consumer<EntityLivingBase> prevAttacks;

    public EntityNetherKnight(World worldIn) {
        super(worldIn);
        this.setSize(1.0f, 3.4f);
        this.isImmuneToFire = true;
        this.isImmuneToExplosions();

    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(STAB_MODE, Boolean.valueOf(false));
        this.dataManager.register(SWING_MODE, Boolean.valueOf(false));
        this.dataManager.register(SUMMON_MODE, Boolean.valueOf(false));
        this.dataManager.register(FIRE_RINGS, Boolean.valueOf(false));
        this.dataManager.register(AOE_STRIKE, Boolean.valueOf(false));
        this.dataManager.register(LEAP_ATTACK, Boolean.valueOf(false));
    }

    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public void setSwingMode(boolean value) {
        this.dataManager.set(SWING_MODE, Boolean.valueOf(value));
    }
    public boolean isSwingMode() {
        return this.dataManager.get(SWING_MODE);
    }
    public void setStabMode(boolean value) {
        this.dataManager.set(STAB_MODE, Boolean.valueOf(value));
    }
    public boolean isStabMode() {
        return this.dataManager.get(STAB_MODE);
    }
    public void setSummonMode(boolean value) {
        this.dataManager.set(SUMMON_MODE, Boolean.valueOf(value));
    }
    public boolean isSummonMode() {
        return this.dataManager.get(SUMMON_MODE);
    }
    public void setFireRings(boolean value) {this.dataManager.set(FIRE_RINGS, Boolean.valueOf(value));}
    public boolean isFireRings() {return this.dataManager.get(FIRE_RINGS);}
    public void setAoeStrike(boolean value) {this.dataManager.set(AOE_STRIKE, Boolean.valueOf(value));}
    public boolean isAoeStrike() {
        return this.dataManager.get(AOE_STRIKE);
    }
    public void setLeapAttack(boolean value) {this.dataManager.set(LEAP_ATTACK, Boolean.valueOf(value));}
    public boolean isLeapAttack() {return this.dataManager.get(LEAP_ATTACK);}

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth()/ this.getMaxHealth());

        if (this.isSwingMode()) {
            this.motionX = 0;
            this.motionZ = 0;
            this.motionY = 0;
        }

    }


    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 5, 24F, 0.1F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "knight_idle", 0, this::predicateKnight));
        animationData.addAnimationController(new AnimationController(this, "knight_attacks", 0, this::predicateAttacks));
    }


    private <E extends IAnimatable>PlayState predicateKnight(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isFightMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WALK_ANIM, true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }

        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicateAttacks(AnimationEvent<E> event) {
        if (this.isSwingMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SIMPLE_STRIKE, false));
            return PlayState.CONTINUE;
        }
        if (this.isStabMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(STAB_ATTACK, false));
            return PlayState.CONTINUE;
        }
        if (this.isSummonMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_METOERS, false));
            return PlayState.CONTINUE;
        }
        if(this.isFireRings()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_FIRE_RINGS, false));
            return PlayState.CONTINUE;
        }
        if(this.isAoeStrike()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(AOE_ATTACK, false));
            return PlayState.CONTINUE;
        }
        if(this.isLeapAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(LEAP_ANIM, false));
            return PlayState.CONTINUE;
        }

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            ModUtils.circleCallback(2, 30, (pos) -> {
                pos = new Vec3d(pos.x, 0, pos.y);
                ParticleManager.spawnDust(world, pos.add(this.getPositionVector()).add(ModUtils.yVec(5.6)), ModColors.FIREBALL_ORANGE, pos.normalize().scale(0.3).add(ModUtils.yVec(0.1)), ModRandom.range(2, 15));
            } );
        }
        if(id == ModUtils.SECOND_PARTICLE_BYTE) {
            ModUtils.circleCallback(2, 30, (pos)-> {
                pos = new Vec3d(pos.x, 0, pos.y);
                ParticleManager.spawnDust(world, pos.add(this.getPositionVector()).add(ModUtils.yVec(0.1)), ModColors.RED, pos.normalize().scale(0.9).add(ModUtils.yVec(0.2)), ModRandom.range(2, 5));

            });
        }


        super.handleStatusUpdate(id);
    }

    /**
     * Move Speed for when the entity is in combat only, this will allow the entity to seemingly move quicker in combat. This will allow you to make the knight slower, or faster.
     */
    public void moveTowards() {
        EntityLivingBase targetedEntity = this.getAttackTarget();
        if (targetedEntity != null) {
            Vec3d target = this.getAttackTarget().getPositionVector();
            Vec3d dir = target.subtract(this.getPositionVector().normalize());
            Vec3d move = new Vec3d(dir.x, dir.y, dir.z);
            float moveSpeed = this.getConfigFloat("nether_knight_move_speed");
            this.motionX += move.x * moveSpeed;
            this.motionY += move.y * moveSpeed;
            this.motionZ += move.z * moveSpeed;



            ModUtils.moveTowards(this, target);

        }

    }






    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {

        if (!this.isFightMode()) {
            double distance = Math.sqrt(distanceSq);
            double HealthChange = this.getHealth() / this.getMaxHealth();
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(simpleSwing, stabAttack, summonFire, fireRings, aoeAttack, leapAttack));
            double[] weights = {
                    (distance < 5 ) ? 1 / distance : 0, // Simple Strike
                    (distance > 5) ? 1.3 / distance : 0, // Stab Dash
                    (distance > 10) ? distance * 0.08 : 0, // Summon Fireballs
                    (distance > 10 && HealthChange < 0.65) ? distance * 0.08 : 0, // Summon Fire Rings @ 65% health
                    (distance < 5 && HealthChange < 0.50) ? 1/distance : 0, // AOE Attack @ 50% health
                    (distance > 10  && prevAttacks == summonFire || prevAttacks == fireRings) ? distance * 0.09 : 0 // Leap Attack

            };

            prevAttacks = ModRandom.choice(attacks, rand, weights).next();
            prevAttacks.accept(target);
        }
        return (prevAttacks == summonFire || prevAttacks == fireRings) ? 140 : 5 ;
    }

    private final Consumer<EntityLivingBase> leapAttack = (target) -> {
      this.setFightMode(true);
      float distances = getDistance(target);
      if(distances > 10) {
          this.setLeapAttack(true);
          addEvent(() -> {

              Vec3d targetPos = target.getPositionVector();

              for (int tickd = 0; tickd < 40; tickd += 2) {
                  addEvent(() -> {
                      ModUtils.circleCallback(0.3f, 20, (pos) -> {
                          pos = new Vec3d(pos.x, 0, pos.y);
                          ParticleManager.spawnDust(world, pos.add(target.getPositionVector()), ModColors.YELLOW, pos.normalize(), ModRandom.range(0, 2));
                      });
                  }, tickd);
              }
              addEvent(() -> {
                  double distance = getDistance(targetPos.x, targetPos.y, targetPos.z);

                  ModUtils.leapTowards(this, targetPos, (float) (0.55 * Math.sqrt(distance)), 0.5f);
              }, 23);
          }, 5);
          addEvent(() -> {
              DamageSource source = ModDamageSource.builder()
                      .type(ModDamageSource.EXPLOSION)
                      .directEntity(this)
                      .element(getElement())
                      .stoppedByArmorNotShields().build();

              Vec3d pos = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.2, 0, 0)));
              float damage = this.getAttack() * getConfigFloat("nether_knight_slam");
              ModUtils.handleAreaImpact(2, (e) -> damage, this, pos, source);
              this.world.newExplosion(this, pos.x, pos.y + 1, pos.z, (float) getMobConfig().getDouble("nether_knight_explosion"), false, true);
          }, 46);

          addEvent(() -> this.setLeapAttack(false), 90);
      }
      addEvent(()-> this.setFightMode(false), 90);
    };

    //AOE Attack
    private final Consumer<EntityLivingBase> aoeAttack = (target) -> {
      this.setFightMode(true);
      this.setAoeStrike(true);
      addEvent(()-> {
          Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.0, 1.5, 0)));
          DamageSource source = ModDamageSource.builder()
                  .type(ModDamageSource.MOB)
                  .directEntity(this)
                  .build();
          float damage = getAttack() * getConfigFloat("nether_knight_swing");
          ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.5f, 0, false);
          playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
      }, 33);


      addEvent(()-> {
          world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
      }, 52);
      addEvent(()-> {
          Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.0, 0, 0)));
          DamageSource source = ModDamageSource.builder()
                  .type(ModDamageSource.MOB)
                  .directEntity(this)
                  .build();
          float damage = getAttack() * getConfigFloat("nether_knight_slam");
          ModUtils.handleAreaImpact(4.0f, (e) -> damage, this, offset, source, 1.0f, 1, false);

          playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.5f, 1.0f / getRNG().nextFloat() * 0.4f + 0.4f);

      }, 55);

      addEvent(() -> this.setAoeStrike(false), 80);
      addEvent(() -> this.setFightMode(false), 80);
    };
    //Summon Fire Rings
    private final Consumer<EntityLivingBase> fireRings = (target) -> {
        this.setFightMode(true);
        this.setFireRings(true);

        addEvent(() -> {
            //Particle Code
        }, 20);

        addEvent(()-> {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }, 40);

        addEvent(()-> {
            for(int tick = 0; tick < 15; tick += 5) {
                addEvent(()-> {
                    EntityFireRing fireRing = new EntityFireRing(this.world);
                    Vec3d spawnPos = this.getPositionVector();
                    BlockPos pos = new BlockPos(spawnPos.x + ModRandom.range(-10, 10), spawnPos.y + 7, spawnPos.z + ModRandom.range(-10, 10));
                    fireRing.setPosition(pos.getX(), pos.getY(), pos.getZ());
                    this.world.spawnEntity(fireRing);
                }, tick);
            }
        }, 44);


        addEvent(()-> this.setFireRings(false), 60);
        addEvent(()-> this.setFightMode(false), 60);

    };


    // Summon Fireballs
    private final Consumer<EntityLivingBase> summonFire = (target) -> {
        this.setFightMode(true);
        this.setSummonMode(true);
        addEvent(() -> {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }, 20);


        addEvent(() -> {
                        for (int i = 0; i < 180; i += 60) {
                        addEvent(() -> {
                            ProjectileMonolithFireball meteor = new ProjectileMonolithFireball(world, this, this.getAttack() * this.getConfigFloat("nether_knight_fireball"), null);
                            Vec3d pos = target.getPositionVector().add(ModUtils.yVec(ModRandom.range(14, 16)));
                            meteor.setPosition(pos.x, pos.y, pos.z);
                            meteor.shoot(this, 90, 0, 0.0F, 0.3f, 0);
                            meteor.motionX -= this.motionX;
                            meteor.motionZ -= this.motionZ;
                            meteor.setTravelRange(100f);
                            world.spawnEntity(meteor);

                        }, i);
                        }
                        }, 24);
        addEvent(() -> this.setSummonMode(false), 60);
        addEvent(() -> this.setFightMode(false), 60);
    };

    //Simple Swing
    private final Consumer<EntityLivingBase> simpleSwing = (target) -> {
        this.setFightMode(true);
        this.setSwingMode(true);
            addEvent(() -> {
                Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.2, 1.5, 0)));
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .build();
                float damage = getAttack() * getConfigFloat("nether_knight_swing");
                ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
            }, 46);

        addEvent(() -> this.setSwingMode(false), 70);
        addEvent(() -> this.setFightMode(false), 70);
    };

    // Stab Attack
    private final Consumer<EntityLivingBase> stabAttack = (target) -> {
        this.setFightMode(true);
        this.setStabMode(true);
        addEvent(() -> {
            Vec3d grabPos = target.getPositionVector();
            BlockPos facePos = new BlockPos(grabPos.x, grabPos.y + 2, grabPos.z);
            addEvent(()-> {
                this.facePosition(facePos, 180.0f, 0.0f);
                ModUtils.leapTowards(this, grabPos, 1.8f, 0.2f);
            }, 15);
        }, 25);
        addEvent(()->{
            for(int fi = 0; fi < 10; fi += 1) {
                addEvent(()-> {

                    Vec3d targetPos = this.getPositionVector();
                    BlockPos fireDest = new BlockPos(targetPos.x, targetPos.y, targetPos.z);
                    this.setBlockToFire(fireDest);


                }, fi);
            }

        }, 40);

        addEvent(() -> {

            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.5, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .disablesShields().build();
            float damage = getAttack() * getConfigFloat("nether_knight_stab");
            ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.1f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 43);
        addEvent(() -> {

            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.0, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .disablesShields().build();
            float damage = getAttack() * getConfigFloat("nether_knight_stab");
            ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.1f, 0, false);
        }, 52);

        addEvent(() -> this.setStabMode(false), 70);
        addEvent(() -> this.setFightMode(false), 70);
    };

    public boolean setBlockToFire(BlockPos pos) {
        return world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }


    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }

        super.readEntityFromNBT(compound);
    }



    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void facePosition(BlockPos pos, float maxPitchIncrease, float maxYawIncrease) {
        double d0 = posX - this.posX;
        double d1 = posZ - this.posZ;

        double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        this.rotationPitch = this.updateRotations(this.rotationPitch, f1, maxPitchIncrease);
        this.rotationYaw = this.updateRotations(this.rotationYaw, f, maxYawIncrease);
    }


    private float updateRotations(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
        return false;
    }
}
