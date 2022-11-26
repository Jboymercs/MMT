package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMonolithFireball;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.renderer.ITarget;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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
    private final String WALK_ANIM = "walk";
    private final String IDLE_ANIM = "idle";
    private final String SIMPLE_STRIKE = "swing";
    private final String STAB_ATTACK = "stab";
    private final String AOE_ATTACK = "";
    private final String SUMMON_METOERS = "summon";

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

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth()/ this.getMaxHealth());

        if (this.isSwingMode()) {
            this.motionX = 0;
            this.motionZ = 0;
            this.motionY = 0;
        }
        if (!this.isFightMode()) {
            this.moveTowards();
        }
    }


    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 60, 10F, 0.1F));
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
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(simpleSwing, stabAttack, summonFire));
            double[] weights = {
                1 / distance,
                1 / distance,
                    (distance > 5) ? distance * 0.08 : 0
            };

            prevAttacks = ModRandom.choice(attacks, rand, weights).next();
            prevAttacks.accept(target);
        }
        return (prevAttacks == summonFire) ? 140 : 10 ;
    }

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
                Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.7, 1.5, 0)));
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .build();
                float damage = getAttack() * getConfigFloat("nether_knight_swing");
                ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
            }, 18);
            addEvent(() -> {
                Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.5, 0)));
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .build();
                float damage = getAttack() * getConfigFloat("nether_knight_swing");
                ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.2f, 0, false);
                playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
            }, 25);


        addEvent(() -> this.setSwingMode(false), 40);
        addEvent(() -> this.setFightMode(false), 40);
    };

    // Stab Attack
    private final Consumer<EntityLivingBase> stabAttack = (target) -> {
        this.setFightMode(true);
        this.setStabMode(true);
        Vec3d grabPos = target.getPositionVector();


        addEvent(() -> {
            ModUtils.leapTowards(this, grabPos, 0.8f, 0.2f);
        }, 18);
        addEvent(() -> {

            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.5, 1.5, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .disablesShields().build();
            float damage = getAttack() * getConfigFloat("nether_knight_stab");
            ModUtils.handleAreaImpact(1.3f, (e) -> damage, this, offset, source, 0.1f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP , 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 23);

        addEvent(() -> this.setStabMode(false), 40);
        addEvent(() -> this.setFightMode(false), 40);
    };




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

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
        return false;
    }
}
