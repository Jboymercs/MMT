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
import net.minecraft.util.DamageSource;
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
import java.util.function.Consumer;

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

    //BASIC MOVEMENTS PHASE 1
    public static String ONE_WALK = "walk";
    public static String ONE_IDLE = "idle";

    //DEATH AND SUMMON
    public static String SUMMON_ANIM = "summon";


    private AnimationFactory hunterFactory = new AnimationFactory(this);


    public EntityMaelstromHunter(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.9f);
        this.healthScaledAttackFactor = 0.3;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth()/ this.getMaxHealth());
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 40, 15, 0.1f));
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        //Reference The Knight's code to see how this works
        if (!this.isfightMode()) {
            float HealthChange = this.getHealth()/ this.getMaxHealth();
            double distance = Math.sqrt(distanceSq);
            double teleportTimer =  0.1;


            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(simpleStrike, leapAttack, simpleStrikeRanged, teleportShoot, DashAttack));
            double [] weights = {
                    (distance < 4) ? 1/distance : 0, // Phase One Simple Strike
                    (distance < 7 && distance > 4) ? 1/distance : 0, // Phase One Leap Attack
                    (distance < 4) ? 1/distance : 0, // Phase One Simple Range Attack
                    (distance < 5 && prevAttack != teleportShoot) ? 1/distance : 0, // Phase One Teleport Shoot
                    (distance < 15 && distance > 7) ? distance * 0.08 : 0 // Phase One Dash Distance Attack









            };
            prevAttack = ModRandom.choice(attacks, rand, weights).next();
            prevAttack.accept(target);
        }

        return 40;
    }

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
    };

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

    }

    private <E extends IAnimatable>PlayState predicateHunter(AnimationEvent<E> event) {
        //checking to see if it is in the middle of Death or Summon Anim
        if (!this.isSummonAnim() && !this.isDeath()) {
            //Checking to see if it's in combat animation
            if (event.isMoving() &&  !this.isfightMode()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ONE_WALK, true));

            }
            else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ONE_IDLE, true));
            }

        }


        return PlayState.CONTINUE;
    }


    private <E extends IAnimatable>PlayState predicatePhaseOne(AnimationEvent<E> event) {
        if (!this.isDeath() && !this.isSummonAnim()) {
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



        }

        event.getController().markNeedsReload();
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
