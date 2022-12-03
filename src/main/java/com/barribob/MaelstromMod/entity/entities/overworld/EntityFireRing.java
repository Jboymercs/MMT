package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileFireball;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Consumer;

/**
 * The Fire Rings the Nether boss summons.
 */

public class EntityFireRing extends EntityLeveledMob implements IAnimatable, IAttack {
    private AnimationFactory ringFactory = new AnimationFactory(this);

    private static final DataParameter<Boolean> LIFE_ANIM = EntityDataManager.createKey(EntityFireRing.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DEATH_ANIM = EntityDataManager.createKey(EntityFireRing.class, DataSerializers.BOOLEAN);

    public final String IDLE_ANIMATION_ALL = "idle";
    public final String SUMMON_ANIM = "summon";
    public final String UN_SUMMON_ANIM = "death";

    private Consumer<EntityLivingBase> prevAttacks;


    public EntityFireRing(World worldIn) {
        super(worldIn);
        this.setSize(1.4f, 1.4f);
        this.setNoGravity(true);
        this.setImmovable(true);

    }

    @Override
    protected void initAnimation() {
        this.setLifeAnim(true);
        if(this.isLifeAnim()) {

            addEvent(() -> this.setLifeAnim(false), 26);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float lifeTime = 600;

        if(this.ticksExisted > lifeTime) {
            this.deathStatus();
        }
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(LIFE_ANIM, Boolean.valueOf(false));
        this.dataManager.register(DEATH_ANIM, Boolean.valueOf(false));

    }

    public void setLifeAnim(boolean value) {
        this.dataManager.set(LIFE_ANIM, Boolean.valueOf(value));
    }

    public boolean isLifeAnim() {
        return this.dataManager.get(LIFE_ANIM);
    }

    public void setDeathAnim(boolean value) {
        this.dataManager.set(DEATH_ANIM, Boolean.valueOf(value));
    }

    public boolean isDeathAnim() {
        return this.dataManager.get(DEATH_ANIM);
    }

    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 0.0f, 60, 16, 0.0f));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
    }

    private final Consumer<EntityLivingBase> shootFireball = (target) -> {
        float damage = this.getAttack();
        ProjectileFireball projectile = new ProjectileFireball(world, this, damage, null);

        Vec3d pos = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 0, 0.2)));
        Vec3d targetPos = target.getPositionVector();
        Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.4);
        projectile.setPosition(pos.x, pos.y, pos.z);
        projectile.setTravelRange(18f);
        projectile.shoot(targetPos.x, targetPos.y, targetPos.z, 0.0f, 0);
        ModUtils.setEntityVelocity(projectile, velocity);
        this.getLookVec();
        world.spawnEntity(projectile);

    };

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {

        if(!this.isDeathAnim() && !this.isLifeAnim()) {
            prevAttacks = shootFireball;
            prevAttacks.accept(target);
        }
        return 60;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "fire_ring_idle", 0, this::predicateIdle));
    animationData.addAnimationController(new AnimationController(this, "summon_n_death", 0, this::predicateDS));

    }

    private <E extends IAnimatable>PlayState predicateIdle(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIMATION_ALL, true));

        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable>PlayState predicateDS(AnimationEvent<E> event) {
        if(this.isLifeAnim()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SUMMON_ANIM, false));
            return PlayState.CONTINUE;
        }

        if (this.isDeathAnim()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(UN_SUMMON_ANIM, false));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    public void deathStatus() {
        this.setDeathAnim(true);

        addEvent(this::setDead, 24);
        addEvent(() -> this.setDeathAnim(false), 24);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }

    @Override
    public void onDeath(DamageSource cause) {
        this.setHealth(0.0001f);
        this.setDeathAnim(true);
        if(this.isDeathAnim()) {

            addEvent(() -> this.setDeathAnim(false), 24);
            addEvent(this::setDead, 24);

        }
        super.onDeath(cause);
    }

    @Override
    public AnimationFactory getFactory() {
        return ringFactory;
    }
}
