package com.barribob.MaelstromMod.entity.entities;


import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHomingFlame;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Textures, Models, Animations done by UnseenDontRun
 */
public class EntityMaelstromKnight extends EntityMaelstromMob implements IAnimatable, IAttack, IElement {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_10));


    //The Attack & Action Animations
    public static final String SIMPLE_ATTACK = "attack_simple";

    public static final String CIRCLE_SPIN = "circle_spin";

    public static final String EVASION = "KnightEvade";

    public static final String TELEPORT_TOO = "teleportToo";

    public static final String SUMMON_CRYSTALS = "spellCast1";

    public static final String SLASH_ATTACK = "KnightSlash";

    public static final String OVERHEADLEAP = "OverArc";

    public static final String WALKANIM = "mk_walk";

    public static final String IDLEANIM = "idle";

    public static final String DEATHANIM = "death";

    public static final String SUMMONBOSS = "summon";


    private Consumer<EntityLivingBase> prevAttack;


    public EntityMaelstromKnight(World worldIn) {
        super(worldIn);
        this.setSize(1.0f, 2.9f);
        this.healthScaledAttackFactor = 0.1;
    }
    //Calls the Summon Animation upon being spawned
    @Override
    protected void initAnimation() {
        this.setSummonanim(true);
        if (this.isSummonAnim()) {
            this.motionX = 0;
            this.motionZ =0;
            addEvent(() -> EntityMaelstromKnight.super.setSummonanim(false), 40);
        }
    }



    //Animation Factory

   private AnimationFactory factory = new AnimationFactory(this);

    // SIMPLE STRIKE
    private final Consumer<EntityLivingBase> SimpleStrike = (target) -> {
        AtomicBoolean HasStriked = new AtomicBoolean(false);
                //Launches Animation if the attack is in Range to start it
                this.processAnimationsUpdates(SIMPLE_ATTACK);
          this.setBlockedbyanimation(true);
                this.setfightMode(true);
                this.setStriking(true);
            //Timed events where damage will be dealt at various points in the animation
        if (!HasStriked.get()) {
                addEvent(() -> {
                    Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.5, 1, 0)));
                    DamageSource source = ModDamageSource.builder()
                            .type(ModDamageSource.MOB)
                            .directEntity(this)
                            .element(getElement()).build();
                    float damage = getAttack() * getConfigFloat("mk_strike_damage");
                    ModUtils.handleAreaImpact(2.5f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                    playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                    HasStriked.set(true);

                }, 10); }
                addEvent(() -> EntityMaelstromKnight.super.setStriking(false), 19);
                addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 25);
        addEvent(() -> EntityMaelstromKnight.super.setBlockedbyanimation(false), 25);
                  addEvent(() -> HasStriked.set(false), 40);




    };
    //CIRCLESPIN
    private final Consumer<EntityLivingBase> circleSpin = (target) -> {
            this.processAnimationsUpdates(CIRCLE_SPIN);
            this.setSpecial1(true);
            this.setfightMode(true);
        AtomicBoolean HasSpinned = new AtomicBoolean(false);
            playSound(SoundsHandler.ENTITY_KNIGHT_SPIN, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));



            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec());
            if (!HasSpinned.get())
               addEvent(() -> {
                   for (int tick = 3; tick < 10; tick +=3) {
                       addEvent(() -> {
                       float damage = getAttack() * getConfigFloat("mk_circle_spin");
                       DamageSource source = ModDamageSource.builder()
                               .type(ModDamageSource.MOB)
                               .directEntity(this)
                               .element(getElement())
                               .disablesShields().build();
                       ModUtils.handleAreaImpact(4.0f, (e) -> damage, this, pos, source, 0.5F, 0, false);

                       HasSpinned.set(true);
                   }, tick);
                   }
            }, 24);
            addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 45);
            addEvent(() -> EntityMaelstromKnight.super.setSpecial1(false), 40);
        addEvent(() -> HasSpinned.set(false), 45);


    };
    //TELEPORT TOO
    private final Consumer<EntityLivingBase> TeleportToo = (target) -> {
            this.setfightMode(true);
            this.setTeleport(true);
            this.processAnimationsUpdates(TELEPORT_TOO);
            AtomicBoolean HasTeleported = new AtomicBoolean(false);
            addEvent(() -> {
                playSound(SoundsHandler.ENTITY_KNIGHT_CAST, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
            }, 5);
            addEvent(()-> {

                if (!HasTeleported.get()) {
                    double d1 = target.posX;
                    double d2 = target.posY;
                    double d3 = target.posZ;
                    this.teleportTarget(d1, d2, d3);
                    playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                    HasTeleported.set(true);
                }
            }, 24);
            addEvent(() ->{
                Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec());
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .element(getElement())
                        .disablesShields().build();
                float damage = getAttack() * getConfigFloat("mk_kick_damage");
            ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, pos, source, 0.2F, 0, false );
            }, 32);
            addEvent(() -> EntityMaelstromKnight.super.setTeleport(false), 40);
            addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 45);
            addEvent(() -> HasTeleported.set(false), 40);
    };
    //SUMMON CRYSTALS
    private final Consumer<EntityLivingBase> SummonCrystals = (target) -> {
        this.setfightMode(true);
        this.setSpellCasting(true);
        this.processAnimationsUpdates(SUMMON_CRYSTALS);
        playSound(SoundsHandler.ENTITY_KNIGHT_SUMMONCRYSTALS, 1.2F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
        addEvent(() -> {
            if (target.isEntityAlive()) {
                Vec3d targetPos = getAttackTarget().getPositionVector();
                BlockPos pos1 = new BlockPos(targetPos.x + ModRandom.getFloat(2), targetPos.y, targetPos.z + ModRandom.getFloat(2));
                EntityKnightCrystal crystal = new EntityKnightCrystal(this.world);
                crystal.setPosition(pos1);
                this.world.spawnEntity(crystal);
                world.setEntityState(this, ModUtils.PARTICLE_BYTE);
            }
        }, 15);
        addEvent(() -> {
            if (target.isEntityAlive()) {
                Vec3d targetPos = getAttackTarget().getPositionVector();
                BlockPos pos1 = new BlockPos(targetPos.x + ModRandom.getFloat(2), targetPos.y, targetPos.z + ModRandom.getFloat(2));
                EntityKnightCrystal crystal = new EntityKnightCrystal(this.world);
                crystal.setPosition(pos1);
                this.world.spawnEntity(crystal);
                world.setEntityState(this, ModUtils.PARTICLE_BYTE);
            }
        }, 25);
        addEvent(() -> {
            if (target.isEntityAlive()) {
                Vec3d targetPos = getAttackTarget().getPositionVector();
                BlockPos pos1 = new BlockPos(targetPos.x + ModRandom.getFloat(2), targetPos.y, targetPos.z + ModRandom.getFloat(2));
                EntityKnightCrystal crystal = new EntityKnightCrystal(this.world);
                crystal.setPosition(pos1);
                this.world.spawnEntity(crystal);
                world.setEntityState(this, ModUtils.PARTICLE_BYTE);
            }
        }, 35);

        addEvent(() -> EntityMaelstromKnight.super.setSpellCasting(false), 32);
        addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 40);
    };
    //EVADE
    private final Consumer<EntityLivingBase> EvadeTactic = (target) -> {
            this.setEvading(true);
            this.setfightMode(true);
            this.processAnimationsUpdates(EVASION);
      addEvent(() -> {
            playSound(SoundsHandler.ENTITY_KNIGHT_EVADE, 1.3F, 1.7F);
            //Jump away from Target
          Vec3d away = this.getPositionVector().subtract(target.getPositionVector()).normalize();
          ModUtils.leapTowards(this, away, 1.5f, 0.4f);
      }, 9);
        addEvent(() -> EntityMaelstromKnight.super.setEvading(false), 40);
        addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 45);
    };

    private final Consumer<EntityLivingBase> KnightSlash = (target) -> {
        this.setfightMode(true);
        this.setSlashing(true);
        playSound(SoundsHandler.ENTITY_KNIGHT_CAST1, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      addEvent(() -> {

          for (int tick = 5; tick < 20; tick += 5) {
              addEvent(() -> {
                  //Slash Projectile Shoot 3!
                  ProjectileHomingFlame slash1 = new ProjectileHomingFlame(world, this, getAttack() * getConfigFloat("mk_projectile"));
                  Vec3d pos = this.getPositionVector();
                  slash1.setPosition(pos.x + ModRandom.getFloat(3), pos.y + 5.6D, pos.z + ModRandom.getFloat(3));
                  slash1.setTravelRange(20);
                  slash1.setNoGravity(true);
                  world.spawnEntity(slash1);
                  ModUtils.throwProjectileNoSpawn(target.getPositionEyes(1), slash1, 0, 0.3f);
                  world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);

              }, tick);
          }
      }, 12);
      addEvent(()-> EntityMaelstromKnight.super.setSlashing(false), 30);
      addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 45);
    };

    private final Consumer<EntityLivingBase> OverArcSwing = (target) -> {
            this.setfightMode(true);
            this.setArcleap(true);
            playSound(SoundsHandler.ENTITY_KNIGHT_SLAM, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
            addEvent(() -> {
                ModUtils.leapTowards(this, target.getPositionVector(), 0.6f, 0.2f);

            }, 12);
            addEvent(() -> {
                Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(3, 1, 0)));
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.EXPLOSION)
                        .directEntity(this)
                        .element(getElement())
                        .disablesShields().build();
                float damage = getAttack() * getConfigFloat("mk_leap_damage");
                ModUtils.handleAreaImpact(3.0f, (e) -> damage, this, offset, source, 0.2F, 0, false );
                this.world.newExplosion(this, offset.x, offset.y -1, offset.z, (float) getMobConfig().getDouble("mk_explosion"), false, true);

            }, 24);

            addEvent(() -> EntityMaelstromKnight.super.setArcleap(false), 40);
            addEvent(() -> EntityMaelstromKnight.super.setfightMode(false), 45);
    };

    //Applies Entity Attributes and Base Info on the Entity

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.2f, 50, 15, 0.2f));

    }

    public void teleportTarget(double x, double y, double z) {
        this.setPosition(x - Math.abs(1), y, z - Math.abs(1));

    }




    //Will call simple animations, use of the attack animations has it's own controller for attacks.
    @Override
    public void registerControllers(AnimationData animationData) {
        //Attacks for the Knight (SimpleAttack, CircleSpin, TeleportToo, Evasion, SummonCrystals)
        animationData.addAnimationController(new AnimationController(this, "knight_attacks", 0, this::predicateKnight));
        // Basic Animations Walk & Idle, Summon, Death
        animationData.addAnimationController(new AnimationController(this, "knight_basic", 0, this::predicateIdle));

        animationData.addAnimationController(new AnimationController(this, "knight_S_D", 0, this::predicateDeathSummon));

    }

    private <E extends  IAnimatable> PlayState predicateIdle(AnimationEvent<E> event) {

        if (!this.isSummonAnim()) {

            // Walking Animation
            if (event.isMoving() && !isfightMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(WALKANIM, true));
            }
            //Idle Animation
            else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLEANIM, true));
            }

        }
            return PlayState.CONTINUE;

    }


   private <E extends IAnimatable> PlayState predicateKnight(AnimationEvent<E> event) {

        if (!this.isDeath()) {
            //Simple Strike
            if (this.isStriking()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation(SIMPLE_ATTACK, false));
                return PlayState.CONTINUE;
            }
            // Circle Spin Partial Phase 1
            if (this.isSpecial1()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation(CIRCLE_SPIN, false));
                return PlayState.CONTINUE;
            }
            // Teleport Too
            if (this.isTeleport()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation(TELEPORT_TOO, false));

                return PlayState.CONTINUE;
            }
            // Summon Crystals Phase 2
            if (this.isSpellCasting()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_CRYSTALS, false));
                return PlayState.CONTINUE;
            }
            // Evasion Phase 2
            if (this.isEvading()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation(EVASION, false));
                return PlayState.CONTINUE;
            }
            // OverArcLeap Phase 3
            if (this.isArcLeaping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(OVERHEADLEAP, false));
                return PlayState.CONTINUE;
            }
            // Slash Shoot Phase 3
            if (this.isSlashing()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation(SLASH_ATTACK, false));
                return PlayState.CONTINUE;
            }
        }
            event.getController().markNeedsReload();
    return PlayState.STOP;
    }

 private <E extends IAnimatable> PlayState predicateDeathSummon(AnimationEvent<E> event) {
        //Death Animation
     if (this.isDeath()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation(DEATHANIM, false));
         return PlayState.CONTINUE;
     }
     //Summon Animation
     if (this.isSummonAnim()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMONBOSS, false));
         return PlayState.CONTINUE;
     }

        return PlayState.STOP;
 }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void attackEntityWithRangedAttack(@Nonnull EntityLivingBase target, float distanceFactor) {

    }



    private String currentAnimationPlaying;

    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth()/ this.getMaxHealth());

        if (this.isfightMode() && !this.isEvading() && !this.isStriking() && !this.isArcLeaping()) {
            this.motionZ = 0;
            this.motionX = 0;
        }

    }



    public void processAnimationsUpdates(String animationId) {
       this.currentAnimationPlaying = animationId;
       switch (animationId) {
            // Simple Attack Animation
            case SIMPLE_ATTACK:
                this.setShooting(true);


                break;
            // Circle Spin Animation
           case CIRCLE_SPIN:
            this.setSpecial1(true);


                break;
          //Evasion Animation
           case EVASION:
            this.setLeaping(true);
               break;

           //Teleport Too Animation
           case TELEPORT_TOO:
            this.setTeleport(true);
               break;
           //Summon Crystals
           case SUMMON_CRYSTALS:
               this.setSpellCasting(true);
               break;
           // Slash Projectile Move
           case SLASH_ATTACK:
               this.setSlashing(true);
               break;
           // Over Arc Leap Attack
           case OVERHEADLEAP:
               this.setArcleap(true);
               break;
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.THIRD_PARTICLE_BYTE) {
            ModUtils.circleCallback(2, 50, (pos) -> {
                pos = new Vec3d(pos.x, 0, pos.y);
                ParticleManager.spawnDust(world, pos.add(this.getPositionVector()).add(ModUtils.yVec(5.6)), ModColors.YELLOW, pos.normalize().scale(0.3).add(ModUtils.yVec(0.1)), ModRandom.range(10, 15));
            } );
        }
        if (id == ModUtils.PARTICLE_BYTE) {
            ModUtils.circleCallback(1, 15, (pos) -> {
                pos = new Vec3d(pos.x, 0, pos.y);
                ParticleManager.spawnDust(world, pos.add(this.getPositionVector()).add(ModUtils.yVec(0.3)), ModColors.MAELSTROM, pos.normalize().scale(0.1).add(ModUtils.yVec(0.1)), ModRandom.range(1, 3));
            });
        }
        super.handleStatusUpdate(id);
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceFactor, boolean strafingBackwards) {
            //It will register these booleans before iniating another attack, basically if they are all false, it will start another attack. A fail safe to keep anything from bugging out.
        if (!isfightMode() && !isLeaping() && !isSummonAnim() && !isDeath()) {

            float HealthChange = this.getHealth() / this.getMaxHealth();
            double distance = Math.sqrt(distanceFactor);


            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(SimpleStrike, circleSpin, TeleportToo, EvadeTactic, SummonCrystals, KnightSlash, OverArcSwing));
            double[] weights = {
                    // All the variables to determine distance, health, and what previous attack was entered if you don't want a animation to be repeated with it being a power move
                    1 / distance, //Simple Attack {PHASE 1 START}
                    HealthChange < .9 ? 1/ distance : 0, // Circle Spin 90% Health {PARTIAL PHASE 1}
                    (distance > 5) ? distance * 0.08 : 0, // Teleport Too (Ideal is to increase the chance of the Knight teleporting to you the further you are away)
                    HealthChange < 0.7  ? 1 / distance : 0, //Evasion Tactic, A simple leap away from the Target {PHASE 2 START}
                    HealthChange < 0.65 ? distance * 0.08 : 0, //SummonCrystals 65% Health
                    HealthChange < 0.5 ? distance * 0.08 : 0,   //!Jump and Projectile Shoot {PHASE 3 START}
                    HealthChange < 0.5 && distance < 6 && prevAttack != OverArcSwing ? 2 : 0  //OverArcLeap Attack
                                                            //Death Calling, End current attack
            };
            prevAttack = ModRandom.choice(attacks, rand, weights).next();
            prevAttack.accept(target);
        }
            // The ratio in how long before it chooses another attack, the shortest timer being the simple_attack, while all the other animations are 2.0 - 2.5 seconds long
            return (isBlockedbyAnimation()) ? 25 : 48;

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }

        super.readEntityFromNBT(compound);
    }
    // Calls the death Animation upon Health being 0.0
    @Override
    public void onDeath(DamageSource cause) {
        this.setHealth(0.0001f);
        this.setDeath(true);
        playSound(SoundsHandler.ENTITY_KNIGHT_DEATH, 1.5F, 1.0F);
        if (this.isDeath()) {
            this.motionX = 0;
            this.motionZ = 0;
            this.motionY = 0;
            addEvent(() -> EntityMaelstromKnight.super.setDeath(false), 60);
            addEvent(() -> EntityMaelstromKnight.super.setDead(), 60);
        }
        super.onDeath(cause);
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
       super.addTrackingPlayer(player);
       this.bossInfo.addPlayer(player);
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {return SoundsHandler.ENTITY_KNIGHT_HURT;}
    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
    private Vec3d DashDir;

    public Optional<Vec3d> getTarget() {
        return Optional.ofNullable(this.DashDir);
    }


    @Override
    protected boolean canDespawn() {
        return false;
    }

}
