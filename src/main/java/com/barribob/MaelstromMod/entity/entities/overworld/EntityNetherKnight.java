package com.barribob.MaelstromMod.entity.entities.overworld;

import akka.japi.pf.FI;
import com.barribob.MaelstromMod.entity.ai.EntityAIFollowAttackers;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMonolithFireball;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.entity.util.IPitch;
import com.barribob.MaelstromMod.particle.ModParticle;
import com.barribob.MaelstromMod.renderer.ITarget;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * The first vanilla dimension boss to be implemented in, A magical fiery knight that derives it's magic from chaotic energy.
 */
public class EntityNetherKnight extends EntityLeveledMob implements IAttack, IAnimatable, IEntityMultiPart, IPitch {

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
    private static final DataParameter<Boolean> SWINGVARIANT = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Float> LOOK = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.FLOAT);

    protected static final DataParameter<Boolean> STUNNED = EntityDataManager.createKey(EntityNetherKnight.class, DataSerializers.BOOLEAN);
    private final String WALK_ANIM = "walk";
    private final String IDLE_ANIM = "idle";
    private final String SIMPLE_STRIKE = "swing";
    private final String STAB_ATTACK = "stab";
    private final String AOE_ATTACK = "aoe";
    private final String SUMMON_METOERS = "summon";
    private final String SUMMON_FIRE_RINGS = "fire_rings";
    private final String LEAP_ANIM = "leap";
    private final String SWING_VARIANT = "swingVariant";
    private final String STUN_ANIM = "stunStatus";


    private final MultiPartEntityPart model = new MultiPartEntityPart(this, "model", 0.8f, 0.7f);
    private final MultiPartEntityPart lArm = new MultiPartEntityPart(this, "leftArm", 0.45f, 1.8f);
    private final MultiPartEntityPart rArm = new MultiPartEntityPart(this, "rightArm", 0.45f, 1.8f);
    private final MultiPartEntityPart knight = new MultiPartEntityPart(this, "knight", 0f, 0f);

    private final MultiPartEntityPart lLeg = new MultiPartEntityPart(this, "leftLeg", 0.45f, 1.5f);
    private final MultiPartEntityPart rLeg = new MultiPartEntityPart(this, "rightLeg", 0.5f, 1.5f);
    private final MultiPartEntityPart core = new MultiPartEntityPart(this, "core", 0.7f, 0.4f);
    private final MultiPartEntityPart chestLower = new MultiPartEntityPart(this, "lower", 0.7f, 0.5f);

    private final MultiPartEntityPart[] hitboxParts;

    public boolean hitOpener;
    public int hitTimer = 0;

    public float stunCounter = 0;

    public boolean targetShieldUse;


    private Consumer<EntityLivingBase> prevAttacks;

    public EntityNetherKnight(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
        this.isImmuneToExplosions();
        this.hitboxParts = new MultiPartEntityPart[]{model, knight, rArm, lArm, rLeg, lLeg, core, chestLower};
        this.setSize(0.3f, 3.4f);


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
        this.dataManager.register(SWINGVARIANT, Boolean.valueOf(false));
        this.dataManager.register(LOOK, 0f);
        this.dataManager.register(STUNNED, Boolean.valueOf(false));
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
    public void setSwingvariant(boolean value) {this.dataManager.set(SWINGVARIANT, Boolean.valueOf(value));}
    public boolean isSwingVariant() {return this.dataManager.get(SWINGVARIANT);}
    public void setStunned(boolean value) {this.dataManager.set(STUNNED, Boolean.valueOf(value));}
    public boolean isStunned() {return this.dataManager.get(STUNNED);}

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if (this.isSwingMode()) {
            this.motionX = 0;
            this.motionZ = 0;
            this.motionY = 0;
        }
        EntityLivingBase target = this.getAttackTarget();
        if(target != null) {
            boolean hasGround = false;
            if(!world.isRemote && this.isFightMode()) {
                AxisAlignedBB box = getEntityBoundingBox().grow(1.25, 0.1, 1.25).offset(0, 0.1, 0);
                ModUtils.destroyBlocksInAABB(box, world, this);
            }
            for(int i = 0; i > -10; i--) {
                if(!world.isAirBlock(getPosition().add(new BlockPos(0, i, 0)))) {
                    hasGround = true;
                }
            }

            if(!hasGround && motionY < -1) {
                this.setImmovable(true);
            } else if (this.isImmovable()) {
                this.setImmovable(false);
            }
        }
        if(!this.isStunned()) {
            hitOpener = false;
        }
        if(this.isStunned()) {
            hitOpener = true;
        }
        this.isBlocking();

        if(!world.isRemote && this.isLeaping()) {
            AxisAlignedBB box = getEntityBoundingBox().grow(1.0, 0.12, 1.0).offset(0, 0.12, 0);
            ModUtils.destroyBlocksInAABB(box, world, this);
        }

        }

        @Override
        public void onEntityUpdate() {
        super.onEntityUpdate();
        if(this.hitOpener) {
            if (rand.nextInt(20) == 0) {
                world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
            }
        }
        }

        private void setHitBoxPos(Entity entity, Vec3d offset) {
        Vec3d lookVel = ModUtils.getLookVec(this.getPitch(), this.renderYawOffset);
        Vec3d center = this.getPositionVector().add(ModUtils.yVec(1.7));

        Vec3d position = center.subtract(ModUtils.Y_AXIS.add(ModUtils.getAxisOffset(lookVel, offset)));
        ModUtils.setEntityPosition(entity, position);

        }

        public void stunMeter() {


        if(stunCounter >= 16) {
            this.setStunned(true);
            this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.8f, 0.8f + ModRandom.getFloat(0.1f));

            //play Animation
            addEvent(()-> this.setStunned(false), 80);

            addEvent(()-> stunCounter = 0, 80);
        }
        }

        @Override
        public void onLivingUpdate() {
        super.onLivingUpdate();
            Vec3d[] avec3d = new Vec3d[this.hitboxParts.length];
            for (int j = 0; j < this.hitboxParts.length; ++j) {
                avec3d[j] = new Vec3d(this.hitboxParts[j].posX, this.hitboxParts[j].posY, this.hitboxParts[j].posZ);
            }

            this.setHitBoxPos(model, new Vec3d(0, 1.95, 0));
            this.setHitBoxPos(rArm, new Vec3d(0, 0.5, 0.6 ));
            this.setHitBoxPos(lArm, new Vec3d(0, 0.5, -0.6));
            this.setHitBoxPos(lLeg, new Vec3d(0, -0.7, -0.225));
            this.setHitBoxPos(rLeg, new Vec3d(0, -0.7, 0.225));
            this.setHitBoxPos(core, new Vec3d(0, 1.525, 0));
            this.setHitBoxPos(chestLower, new Vec3d(0, 1.025, 0));

            Vec3d knightPos = this.getPositionVector();
            ModUtils.setEntityPosition(knight, knightPos);

            for (int l = 0; l < this.hitboxParts.length; ++l) {
                this.hitboxParts[l].prevPosX = avec3d[l].x;
                this.hitboxParts[l].prevPosY = avec3d[l].y;
                this.hitboxParts[l].prevPosZ = avec3d[l].z;
            }

        }

        private boolean attackCore;

        @Override
        public final boolean attackEntityFromPart(@Nonnull MultiPartEntityPart part,@Nonnull DamageSource source, float damage) {
        if(part == this.core && this.hitOpener) {
            this.attackCore = true;
            return this.attackEntityFrom(source, damage);
        }
        if(damage > 0.0f && !source.isUnblockable()) {
            if(!source.isProjectile()) {
                Entity entity = source.getImmediateSource();
                if (entity instanceof EntityLivingBase) {
                    this.blockUsingShield((EntityLivingBase) entity);

                }
            }

           this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0f, 0.6f + ModRandom.getFloat(0.2f));
            return false;
        }
        return false;
        }

        @Override
        public final boolean attackEntityFrom(DamageSource source, float amount) {
        if(!this.attackCore && !source.isUnblockable()) {
            return false;
        }
        this.hitOpener = false;
        this.hitTimer = 0;
        this.attackCore = false;
        return super.attackEntityFrom(source, amount);
        }






    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 40, 24F, 0.1F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "knight_idle", 0, this::predicateKnight));
        animationData.addAnimationController(new AnimationController(this, "knight_attacks", 0, this::predicateAttacks));
        animationData.addAnimationController(new AnimationController(this, "knight_stunned", 0, this::predicateStunned));
    }


    private <E extends IAnimatable>PlayState predicateKnight(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isFightMode() && !this.isStunned()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WALK_ANIM, true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }

        return PlayState.CONTINUE;
    }
    private<E extends IAnimatable> PlayState predicateStunned(AnimationEvent<E> event) {
            if(this.isStunned()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(STUN_ANIM, false));
                return PlayState.CONTINUE;
            }
            event.getController().markNeedsReload();
            return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState predicateAttacks(AnimationEvent<E> event) {
            if(!this.isStunned()) {
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
                if (this.isFireRings()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_FIRE_RINGS, false));
                    return PlayState.CONTINUE;
                }
                if (this.isAoeStrike()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(AOE_ATTACK, false));
                    return PlayState.CONTINUE;
                }
                if (this.isLeapAttack()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(LEAP_ANIM, false));
                    return PlayState.CONTINUE;
                }
                if (this.isSwingVariant()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(SWING_VARIANT, false));
                    return PlayState.CONTINUE;
                }
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
        if(id == ModUtils.THIRD_PARTICLE_BYTE) {
            Vec3d motion = new Vec3d(ModRandom.getFloat(0.1f), 0 * 0.2, ModRandom.getFloat(0.1f));
            ParticleManager.spawnCustomSmoke(world, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(2.3)), ModColors.YELLOW, motion);
        }


        super.handleStatusUpdate(id);
    }

    /**
     * Move Speed for when the entity is in combat only, this will allow the entity to seemingly move quicker in combat. This will allow you to make the knight slower, or faster.
     */







    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {

        if (!this.isFightMode() && !this.isStunned()) {
            double distance = Math.sqrt(distanceSq);
            double HealthChange = this.getHealth() / this.getMaxHealth();
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(simpleSwing, stabAttack, summonFire, fireRings, aoeAttack, leapAttack, swingVariant));
            double[] weights = {
                    (distance < 5 && prevAttacks != simpleSwing) ? 1 / distance : 0, // Simple Strike
                    (distance > 5) ? 1.1 / distance : 0, // Stab Dash
                    (distance > 12 && prevAttacks != summonFire) ? distance * 0.02 : 0, // Summon Fireballs
                    (distance > 12 && HealthChange < 0.65 && prevAttacks != fireRings) ? distance * 0.02 : 0, // Summon Fire Rings @ 65% health
                    (distance < 5 && HealthChange < 0.40 && prevAttacks != aoeAttack) ? 1.1 / distance : 0, // AOE Attack @ 50% health
                    (distance > 12) ? distance * 0.02 : 0, // Leap Attack
                    (distance < 5 && prevAttacks != swingVariant) ? 1 / distance : 0 // Swing Multi Strike
            };


            prevAttacks = ModRandom.choice(attacks, rand, weights).next();
            prevAttacks.accept(target);
        }
        return (prevAttacks == summonFire || prevAttacks == fireRings) ? 140 : 40 ;
    }
    private final Consumer<EntityLivingBase> swingVariant = (target) -> {
      this.setFightMode(true);
      this.setSwingvariant(true);

          addEvent(() -> {
              if(!this.isStunned()) {
                  targetShieldUse = true;
                  Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.8, 1.5, 0)));
                  DamageSource source = ModDamageSource.builder()
                          .type(ModDamageSource.MOB)
                          .directEntity(this)
                          .build();
                  float damage = getAttack() * getConfigFloat("nether_knight_swing");
                  ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                  playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
                  addEvent(() -> targetShieldUse = false, 5);
              }
          }, 33);

          addEvent(() -> {
              if(!this.isStunned()) {
                  ModUtils.leapTowards(this, target.getPositionVector(), 0.5f, 0.2f);
              }
          }, 40);


          addEvent(() -> {
              if(!this.isStunned()) {
                  Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.8, 1.5, 0)));
                  DamageSource source = ModDamageSource.builder()
                          .type(ModDamageSource.MOB)
                          .directEntity(this)
                          .disablesShields()
                          .build();
                  float damage = getAttack() * getConfigFloat("nether_knight_swing");
                  ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                  playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
              }

          }, 46);


          addEvent(() -> {
              if(!this.isStunned()) {
                  Vec3d grabPos = new Vec3d(target.posX, target.posY, target.posZ);


                  addEvent(() -> {
                      ModUtils.leapTowards(this, grabPos, 0.7f, 0.2f);

                      for (int fi = 0; fi < 7; fi += 1) {
                          addEvent(() -> {

                              Vec3d targetPos = this.getPositionVector();
                              BlockPos fireDest = new BlockPos(targetPos.x, targetPos.y, targetPos.z);
                              this.setBlockToFire(fireDest);


                          }, fi);
                      }


                  }, 18);
              }
          }, 50);


          addEvent(() -> {
              if(!this.isStunned()) {
                  targetShieldUse = true;
                  Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.2, 1.5, 0)));
                  DamageSource source = ModDamageSource.builder()
                          .type(ModDamageSource.MOB)
                          .directEntity(this)
                          .build();
                  float damage = getAttack() * getConfigFloat("nether_knight_swing");
                  ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.8f, 0, false);
                  addEvent(() -> targetShieldUse = false, 3);
              }
          }, 70);


          addEvent(() -> {
                      if(!this.isStunned()) {
                          targetShieldUse = true;
                          Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.8, 1.5, 0)));
                          DamageSource source = ModDamageSource.builder()
                                  .type(ModDamageSource.MOB)
                                  .directEntity(this)
                                  .build();
                          float damage = getAttack() * getConfigFloat("nether_knight_swing");
                          ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.4f, 0, false);
                          playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
                          addEvent(() -> targetShieldUse = false, 5);
                      }
          }, 80);

      addEvent(()-> this.setSwingvariant(false), 110);
      addEvent(()-> this.setFightMode(false), 110);
    };
    // Leap Attack
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

                  ModUtils.leapTowards(this, targetPos, (float) (0.55 * Math.sqrt(distance)), 0.9f);
                  this.setLeaping(true);
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

          addEvent(() -> {
              if(!this.isStunned()) {
                  targetShieldUse = true;
                  Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.0, 1.5, 0)));
                  DamageSource source = ModDamageSource.builder()
                          .type(ModDamageSource.MOB)
                          .directEntity(this)
                          .build();
                  float damage = getAttack() * getConfigFloat("nether_knight_swing");
                  ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                  playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
                  addEvent(() -> targetShieldUse = false, 5);
              }
          }, 33);


            addEvent(() -> {
                if(!this.isStunned()) {
                    world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
                }
            }, 52);


            addEvent(() -> {
                        if(!this.isStunned()) {
                            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.0, 0, 0)));
                            DamageSource source = ModDamageSource.builder()
                                    .type(ModDamageSource.MOB)
                                    .directEntity(this)
                                    .build();
                            float damage = getAttack() * getConfigFloat("nether_knight_slam");
                            ModUtils.handleAreaImpact(4.0f, (e) -> damage, this, offset, source, 1.0f, 1, false);

                            playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.5f, 1.0f / getRNG().nextFloat() * 0.4f + 0.4f);

                        }
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
                targetShieldUse = true;
                Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.2, 1.5, 0)));
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .build();
                float damage = getAttack() * getConfigFloat("nether_knight_swing");
                ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
                addEvent(() -> targetShieldUse = false, 5);
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
                ModUtils.leapTowards(this, grabPos, 1.5f, 0.2f);
            }, 20);
        }, 20);
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
            targetShieldUse = true;
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.0, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .disablesShields().build();
            float damage = getAttack() * getConfigFloat("nether_knight_stab");
            ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.1f, 0, false);
            addEvent(()-> targetShieldUse = false, 5);
        }, 52);

        addEvent(() -> this.setStabMode(false), 70);
        addEvent(() -> this.setFightMode(false), 75);
    };

    public boolean setBlockToFire(BlockPos pos) {
        return world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }

    @Override
    public Entity[] getParts() {
        return this.hitboxParts;
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

    public boolean isBlocking() {
        EntityLivingBase target = this.getAttackTarget();


        if(target != null) {
            ItemStack itemStack = target.getHeldItem(target.getActiveHand());
            if (itemStack.getItem() instanceof ItemShield) {
                double distanceSQ = this.getDistance(target.posX, target.posY, target.posZ);
                double distance = Math.sqrt(distanceSQ);
                if(targetShieldUse && distance < 3) {
                    this.stunCounter++;
                    this.stunMeter();
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    @Override
    public void setPitch(Vec3d look) {
        float prevLook = this.getPitch();
        float newLook = (float) ModUtils.toPitch(look);
        float deltaLook = 5;
        float clampedLook = MathHelper.clamp(newLook, prevLook - deltaLook, prevLook + deltaLook);
        this.dataManager.set(LOOK, clampedLook);

    }

    @Override
    public float getPitch() {
        return this.dataManager == null ? 0 : this.dataManager.get(LOOK);
    }
}
