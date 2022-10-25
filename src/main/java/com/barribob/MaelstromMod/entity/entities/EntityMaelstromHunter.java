package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileChaosFireball;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.function.Consumer;

/**
 * This is a very big class, with multiple parts to it, I will try to break it down the best I can
 */
public class EntityMaelstromHunter extends EntityMaelstromMob implements IAttack, IAnimatable {
    /**
     * The first Lush Dungeon Boss, content of this guy will be removed entirely before public release of Initial
     * Textures, Animations, AI done by UnseenDontRun
     */
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.NOTCHED_6));

    private Consumer<EntityLivingBase> prevAttack;

    /**
     * Dev's note : This boss is meant to act as a 1 v 1 against the player, and changing how it operates as phases progress
     * This will be the Break or Fight moment for the player being faced with a boss that is quick and almost at times unpredictable.
     */
    //PHASE 1
    public static String SIMPLE_ATTACK = "oneSimpleStrike";
    public static String SIMPLE_ATTACK_RANGED = "oneSimpleStrikeShoot";
    public static String LEAP_ATTACK = "oneLeapAttack";
    public static String DASH_ATTACK = "oneDash";
    public static String AOE_ATTACK = "oneOverAttack";
    public static String RANGE_TELEPORT = "oneTeleport";

    //BASIC MOVEMENTS ALL PHASES
    public static String ONE_WALK = "walk";
    public static String ONE_IDLE = "idle";

    public static String TWO_WALK = "twoWalk";
    public static String TWO_IDLE = "twoIdle";

    //DEATH AND SUMMON
    public static String SUMMON_ANIM = "summon";

    //PHASE 2

    public static String PHASE_TWO_CHANGE = "PhaseTwo";
    public static String TWO_SWING = "twoSwing";
    public static String TWO_MISSILES = "twoMissile";
    public static String TWO_SWING_SHOOT = "twoSwingShoot";
    public static String TWO_GLIDE = "twoGlide";
    public static String TWO_LEAP = "twoLeap";

    // PHASE 3
    public static String PHASE_THREE_CHANGE = "PhaseThree";

    protected static final DataParameter<Boolean> AOEATTACK = EntityDataManager.createKey(EntityMaelstromHunter.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> PHASEONE = EntityDataManager.createKey(EntityMaelstromHunter.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> PHASETWO = EntityDataManager.createKey(EntityMaelstromHunter.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> PHASETHREE = EntityDataManager.createKey(EntityMaelstromHunter.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> PHASE_CHANGE = EntityDataManager.createKey(EntityMaelstromHunter.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> TENTACLE_WHIP1 = EntityDataManager.createKey(EntityMaelstromHunter.class, DataSerializers.BOOLEAN);


    private AnimationFactory hunterFactory = new AnimationFactory(this);
    @Override
    protected void initAnimation() {
        this.setSummonanim(true);
        if (this.isSummonAnim()) {
            motionX = 0;
            motionZ = 0;
        }

        addEvent(() -> EntityMaelstromHunter.super.setSummonanim(false), 40);



        super.initAnimation();
    }

    public EntityMaelstromHunter(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.9f);
        this.healthScaledAttackFactor = 0.3;
        this.setPhaseone(true);
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth()/ this.getMaxHealth());
        this.phaseHandler();

        if (this.isPhaseChanging()) {

            this.motionZ = 0;
            this.motionX = 0;

        }
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 20, 15, 0.1f));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(AOEATTACK, Boolean.valueOf(false));
        this.dataManager.register(PHASEONE, Boolean.valueOf(false));
        this.dataManager.register(PHASETWO, Boolean.valueOf(false));
        this.dataManager.register(PHASETHREE, Boolean.valueOf(false));
        this.dataManager.register(PHASE_CHANGE, Boolean.valueOf(false));
        this.dataManager.register(TENTACLE_WHIP1, Boolean.valueOf(false));
    }

    //Data Manager Booleans
    public void setAoeAttack(boolean value) {
        this.dataManager.set(AOEATTACK, Boolean.valueOf(value));
    }
    public boolean isAoeAttack() {
        return this.dataManager.get(AOEATTACK);
    }
    public void setPhaseone(boolean value) {
        this.dataManager.set(PHASEONE, Boolean.valueOf(value));
    }
    public boolean isPhaseOne() {
        return this.dataManager.get(PHASEONE);
    }
    public void setPhasetwo(boolean value) {
        this.dataManager.set(PHASETWO, Boolean.valueOf(value));
    }
    public boolean isPhaseTwo() {
        return this.dataManager.get(PHASETWO);
    }
    public void setPhasethree(boolean value) {
        this.dataManager.set(PHASETHREE, Boolean.valueOf(value));
    }
    public boolean isPhaseThree() {
        return this.dataManager.get(PHASETHREE);
    }

    public void setPhaseChange(boolean value) {
        this.dataManager.set(PHASE_CHANGE, Boolean.valueOf(value));
    }

    public boolean isPhaseChanging() {
        return this.dataManager.get(PHASE_CHANGE);
    }
    public void setTentacleWhip1(Boolean value) {
        this.dataManager.set(TENTACLE_WHIP1, Boolean.valueOf(value));
    }
    public boolean isTentacleWhip1() {
        return this.dataManager.get(TENTACLE_WHIP1);
    }
    //Data Manager Booleans End

    public void phaseHandler() {
        float currentHealth = this.getHealth() / this.getMaxHealth();
        //The Phase Handler, will handle between phase changes, with Phase 2 starting at below 20%
        // Phase 3 starting at below 10%


        if (this.isPhaseOne() && currentHealth < 0.2) {
                this.heal(this.getMaxHealth());
                this.setPhasetwo(true);
                this.setPhaseChange(true);
                this.setPhaseone(false);
                System.out.println("set to Phase 2");
                addEvent(() -> {
                this.setPhaseChange(false);
                }, 75);
        }
        if (this.isPhaseTwo() && currentHealth < 0.1) {
            this.heal(this.getMaxHealth());
            this.setPhasethree(true);
            this.setPhaseChange(true);
            this.setPhasetwo(false);
            System.out.println("set to Phase 3");
            addEvent(() -> {
                this.setPhaseChange(false);
            }, 60);
        }
    }


    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        //Reference The Knight's code to see how this works
        if (!this.isfightMode() && !this.isSummonAnim() && !this.isPhaseChanging()) {
            float HealthChange = this.getHealth()/ this.getMaxHealth();
            double distance = Math.sqrt(distanceSq);

            //Phase One Attacks Handler
            if (this.isPhaseOne()) {
                List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(simpleStrike, leapAttack, simpleStrikeRanged, teleportShoot, DashAttack, AoeAttack));
                double[] weights = {
                        (distance < 4) ? 1 / distance : 0, // Phase One Simple Strike
                        (distance < 7 && distance > 4) ? 1 / distance : 0, // Phase One Leap Attack
                        (distance < 4) ? 1 / distance : 0, // Phase One Simple Range Attack
                        (distance < 5 && prevAttack != teleportShoot) ? 1 / distance : 0, // Phase One Teleport Shoot
                        (distance < 15 && distance > 7) ? distance * 0.08 : 0, // Phase One Dash Distance Attack
                        (HealthChange < 0.5 && distance < 6 && prevAttack != AoeAttack) ? 1 / distance : 0 //Phase One AOE Attack


                };
                prevAttack = ModRandom.choice(attacks, rand, weights).next();
                prevAttack.accept(target);
            }

            //Phase Two Attacks Handler
            if (this.isPhaseTwo()) {
                List<Consumer<EntityLivingBase>> attacks2 = new ArrayList<>(Arrays.asList(tentacleSwing, bladeDash, fastSwingShoot, whipCharge, summonMissiles));
                double[] weights2 = {
                        (distance < 5) ? 1 /distance : 0, // Phase Two Simple Swing
                        (distance > 7) ? distance * 0.08 : 0, // Phase Two Leap Distance Attack
                        (distance < 5) ? 1/distance : 0, // Phase Two Simple Swing + Shoot Twice
                        (distance < 7 && distance > 3) ? 1/distance : 0, // Phase Two Dash Towards
                        (distance > 7) ? distance * 0.08 : 0 // Phase Two Summon Missiles

                };
                prevAttack = ModRandom.choice(attacks2, rand, weights2).next();
                prevAttack.accept(target);
            }

            //Phase Three Attacks Handler
            if (this.isPhaseThree()) {
                List<Consumer<EntityLivingBase>> attacks3 = new ArrayList<>(Arrays.asList());
                double[] weights3 = {

                };
                prevAttack = ModRandom.choice(attacks3, rand, weights3).next();
                prevAttack.accept(target);
            }
        }

        return isBlockedbyAnimation() ? 45 : 20;
    }

    private final Consumer<EntityLivingBase> summonMissiles = (target) -> {
        this.setfightMode(true);
        this.setAoeAttack(true);
        addEvent(() -> {
            if (target.isEntityAlive()) {
                Vec3d summonPos = this.getPositionVector();
                BlockPos posToo = new BlockPos(summonPos.x + 2, summonPos.y + 5, summonPos.z);
                EntityHunterMissile missile1 = new EntityHunterMissile(this.world);
                missile1.setPosition(posToo.getX(), posToo.getY(), posToo.getZ());
                this.world.spawnEntity(missile1);

            }

            if (target.isEntityAlive()) {
                Vec3d summonPos = this.getPositionVector();
                BlockPos posToo = new BlockPos(summonPos.x - 2, summonPos.y + 5, summonPos.z);
                EntityHunterMissile missile1 = new EntityHunterMissile(this.world);
                missile1.setPosition(posToo.getX(), posToo.getY(), posToo.getZ());
                this.world.spawnEntity(missile1);

            }
        }, 19);
        addEvent(() -> this.setAoeAttack(false), 40);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
    };
    // Phase Two Abilities
    private final Consumer<EntityLivingBase> whipCharge = (target) -> {
        this.setfightMode(true);
        this.setArcleap(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 1.2f, 0.2f);
        }, 7);

        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_tentacle");
            ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.8f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 20);

        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 33);
        addEvent(() -> EntityMaelstromHunter.super.setArcleap(false), 33);
    };
    private final Consumer<EntityLivingBase> fastSwingShoot = (target) -> {
        this.setfightMode(true);
        this.setSlashing(true);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(4, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_tentacle");
            ModUtils.handleAreaImpact(1f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 12);

        addEvent(() -> {
            float pelletCount = 10;
            for (int i = 0; i < pelletCount; i++) {
                float damage = this.getAttack() * getConfigFloat("hunter_shotgun");
                ProjectileBullet bullet = new ProjectileBullet(world, this, damage, null);

                Vec3d pos = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 1.3, 0)));
                Vec3d targetPos = new Vec3d(ModRandom.getFloat(3), ModRandom.getFloat(3), ModRandom.getFloat(3)).add(target.getPositionVector());
                Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.8);
                bullet.setPosition(pos.x, pos.y, pos.z);
                bullet.setTravelRange(5f);
                bullet.shoot(targetPos.x, targetPos.y, targetPos.z, 0.0f, 0);
                ModUtils.setEntityVelocity(bullet, velocity);
                world.spawnEntity(bullet);
            }
            playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8F);
        }, 20);

        addEvent(()-> {
            float pelletCount = 10;
            for (int i = 0; i < pelletCount; i++) {
                float damage = this.getAttack() * getConfigFloat("hunter_shotgun");
                ProjectileBullet bullet = new ProjectileBullet(world, this, damage, null);

                Vec3d pos = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 1.3, 0)));
                Vec3d targetPos = new Vec3d(ModRandom.getFloat(3), ModRandom.getFloat(3), ModRandom.getFloat(3)).add(target.getPositionVector());
                Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.8);
                bullet.setPosition(pos.x, pos.y, pos.z);
                bullet.setTravelRange(5f);
                bullet.shoot(targetPos.x, targetPos.y, targetPos.z, 0.0f, 0);
                ModUtils.setEntityVelocity(bullet, velocity);
                world.spawnEntity(bullet);
            }
            playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8F);
        }, 28);

        addEvent(() -> EntityMaelstromHunter.super.setSlashing(false), 40);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
    };
    private final Consumer<EntityLivingBase> bladeDash = (target) -> {
        this.setfightMode(true);
        this.setEvading(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 1.5f, 0.6f);
        }, 10);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_blade");
            ModUtils.handleAreaImpact(0.8f, (e) -> damage, this, offset, source, 0.7f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 26);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
        addEvent(() -> EntityMaelstromHunter.super.setEvading(false), 40);
    };
    private final Consumer<EntityLivingBase> tentacleSwing = (target) -> {
        this.setfightMode(true);
        this.setTentacleWhip1(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 0.3f, 0.1f);
        }, 20);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(3, 1.0, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_tentacle");
            ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.7f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 25);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
        addEvent(() -> this.setTentacleWhip1(false), 40);
    };
    //Phase One Abilities
    private final Consumer<EntityLivingBase> AoeAttack = (target) -> {
      this.setfightMode(true);
      this.setAoeAttack(true);
        addEvent(() -> {
        ModUtils.leapTowards(this, target.getPositionVector(), 0.5f, 0.2f);
        }, 20);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.5, 1.0, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_swordSlam");
            ModUtils.handleAreaImpact(3f, (e) -> damage, this, offset, source, 0.6f, 0, false);
            playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);

        }, 28);


      addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
      addEvent(() -> this.setAoeAttack(false), 40);
    };

    private final Consumer<EntityLivingBase> DashAttack = (target) -> {
        this.setfightMode(true);
        this.setEvading(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 1.8f, 0.4f);

        }, 13);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_strike");
            ModUtils.handleAreaImpact(3f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 28);

        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
        addEvent(() -> EntityMaelstromHunter.super.setEvading(false), 40);
    };



    private final Consumer<EntityLivingBase> simpleStrikeRanged = (target) -> {
        this.setfightMode(true);
        this.setSlashing(true);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_strike");
            ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);

        }, 15);

        addEvent(() -> {
            float pelletCount = 10;
            for (int i = 0; i < pelletCount; i++) {
                float damage = this.getAttack() * getConfigFloat("hunter_shotgun");
                ProjectileBullet bullet = new ProjectileBullet(world, this, damage, null);

                Vec3d pos = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 1.2, 0)));
                Vec3d targetPos = new Vec3d(ModRandom.getFloat(3), ModRandom.getFloat(3), ModRandom.getFloat(3)).add(target.getPositionVector());
                Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.8);
                bullet.setPosition(pos.x, pos.y, pos.z);
                bullet.setTravelRange(5f);
                bullet.shoot(targetPos.x, targetPos.y, targetPos.z, 0.0f, 0);
                ModUtils.setEntityVelocity(bullet, velocity);
                world.spawnEntity(bullet);
            }
            playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8F);
        }, 24);


        addEvent(() -> EntityMaelstromHunter.super.setSlashing(false), 37);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 37);
    };

    private final Consumer<EntityLivingBase> simpleStrike = (target) -> {
        this.setfightMode(true);
        this.setStriking(true);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_strike");
            ModUtils.handleAreaImpact(2f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);

        }, 15);
        addEvent(() -> EntityMaelstromHunter.super.setStriking(false), 25);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 25);
    };

    private final Consumer<EntityLivingBase> leapAttack = (target) -> {
        this.setfightMode(true);
        this.setArcleap(true);
        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 0.9f, 0.2f);
        }, 19);

        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2, 1, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement()).build();
            float damage = getAttack() * getConfigFloat("hunter_strike");
            ModUtils.handleAreaImpact(1f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 26);


        addEvent(() -> EntityMaelstromHunter.super.setArcleap(false), 40);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 40);
    };

    private final Consumer<EntityLivingBase> teleportShoot = (target) -> {
        this.setfightMode(true);
        this.setTeleport(true);
        this.setBlockedbyanimation(true);


        addEvent(() -> {
            float pelletCount = 10;
            for (int i = 0; i < pelletCount; i++) {
                float damage = this.getAttack() * getConfigFloat("hunter_shotgun");
                ProjectileBullet bullet = new ProjectileBullet(world, this, damage, null);

                Vec3d pos = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 1.2, 0)));
                Vec3d targetPos = new Vec3d(ModRandom.getFloat(3), ModRandom.getFloat(3), ModRandom.getFloat(3)).add(target.getPositionVector());
                Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.8);
                bullet.setPosition(pos.x, pos.y, pos.z);
                bullet.setTravelRange(5f);
                bullet.shoot(targetPos.x, targetPos.y, targetPos.z, 0.0f, 0);
                ModUtils.setEntityVelocity(bullet, velocity);
                world.spawnEntity(bullet);
            }
            playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8F);
        }, 11);

        addEvent(() -> {
            double d1 = this.posX;
            double d2 = this.posY;
            double d3 = this.posZ;
            this.teleportRandom(d1, d2, d3);

        }, 31);


        addEvent(() -> EntityMaelstromHunter.super.setTeleport(false), 60);
        addEvent(() -> EntityMaelstromHunter.super.setfightMode(false), 60);
        addEvent(() -> EntityMaelstromHunter.super.setBlockedbyanimation(false), 60);
    };
    //Phase 1 Abilities End

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }



    public void teleportRandom(double x, double y, double z) {
        this.setPosition(x + ModRandom.getFloat(5), y, z + ModRandom.getFloat(5));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "hunter_phaseOne", 0, this::predicatePhaseOne));
        animationData.addAnimationController(new AnimationController(this, "hunter_movement", 0, this::predicateHunter));
        animationData.addAnimationController(new AnimationController(this, "hunter_summonDeath", 0, this::predicateSummonDeath));
        animationData.addAnimationController(new AnimationController(this, "hunter_phases", 0, this::predicatePhaseChange));
        animationData.addAnimationController(new AnimationController(this, "hunter_phaseTwo", 0, this::predicatePhaseTwo));
        animationData.addAnimationController(new AnimationController(this, "hunter_movement2", 0, this::predicateHunter2));

    }

    private <E extends IAnimatable> PlayState predicateHunter2(AnimationEvent<E> event) {
        if (!this.isPhaseChanging()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_WALK, true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_IDLE, true));

            }
        }
        //Determines PlayState
        if (this.isPhaseThree()) {
            return PlayState.STOP;
        }
        if (this.isPhaseOne()) {
            return PlayState.STOP;
        }
        if (this.isfightMode()) {
            event.getController().markNeedsReload();
            return PlayState.STOP;
        }
        else {
            return PlayState.CONTINUE;
        }
    }
    private <E extends IAnimatable>PlayState predicateHunter(AnimationEvent<E> event) {
        //checking to see if it's in SummonAnim
        if (!this.isSummonAnim() && !this.isPhaseChanging()) {
            if (this.isPhaseOne()) {
                //Checking to see if it's in combat animation
                if (event.isMoving() && !this.isfightMode()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(ONE_WALK, true));

                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(ONE_IDLE, true));

                }

            }
        }
        //Determines PlayState
        if (this.isPhaseTwo()) {
            return PlayState.STOP;
        }
        else {
            return PlayState.CONTINUE;
        }
    }
    private <E extends IAnimatable> PlayState predicatePhaseChange(AnimationEvent<E> event) {
        if (this.isPhaseChanging()) {
            if (this.isPhaseTwo()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(PHASE_TWO_CHANGE, false));
                return PlayState.CONTINUE;
            }
            if(this.isPhaseThree()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(PHASE_THREE_CHANGE, false));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }


    private <E extends IAnimatable>PlayState predicatePhaseOne(AnimationEvent<E> event) {
        if (!this.isPhaseChanging()) {
            if (this.isPhaseOne()) {
                if (this.isStriking()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(SIMPLE_ATTACK, false));
                    return PlayState.CONTINUE;
                }
                if (this.isArcLeaping()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(LEAP_ATTACK, false));
                    return PlayState.CONTINUE;
                }
                if (this.isTeleport()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(RANGE_TELEPORT, false));
                    return PlayState.CONTINUE;
                }
                if (this.isSlashing()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(SIMPLE_ATTACK_RANGED, false));
                    return PlayState.CONTINUE;
                }
                if (this.isEvading()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(DASH_ATTACK, false));
                    return PlayState.CONTINUE;
                }
                if (this.isAoeAttack()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(AOE_ATTACK, false));
                    return PlayState.CONTINUE;
                }

            }

        }

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
    private <E extends IAnimatable> PlayState predicatePhaseTwo(AnimationEvent<E> event) {
        if (this.isPhaseTwo() && !this.isPhaseChanging()) {
            if (this.isTentacleWhip1()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_SWING, false));
                return PlayState.CONTINUE;
            }
            if (this.isEvading()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_LEAP, false));
                return PlayState.CONTINUE;
            }
            if (this.isArcLeaping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_GLIDE, false));
                return PlayState.CONTINUE;
            }
            if (this.isSlashing()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_SWING_SHOOT, false));
                return PlayState.CONTINUE;
            }
            if (this.isAoeAttack()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(TWO_MISSILES, false));
                return PlayState.CONTINUE;
            }




        }

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState predicateSummonDeath(AnimationEvent<E> event) {
        if (this.isSummonAnim()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_ANIM, false));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return hunterFactory;
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

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
}
