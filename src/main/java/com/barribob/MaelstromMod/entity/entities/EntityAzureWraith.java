package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class EntityAzureWraith extends EntityLeveledMob implements IAnimatable, IAttack {

    private static final DataParameter<Boolean> STRIKING = EntityDataManager.<Boolean>createKey(EntityLeveledMob.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> OVERSTRIKE = EntityDataManager.<Boolean>createKey(EntityLeveledMob.class, DataSerializers.BOOLEAN);
    private AnimationFactory factory = new AnimationFactory(this);
    //This guy wont implement the Element system due to no relation to the Maelstrom and therefore will have set health, MovementSpeed, Etc.
    private final String WRAITH_IDLE = "idle";
    private final String WRAITH_WALK = "walk";
    private final String WRAITH_ATTACK = "strike";
    private final String WRAITH_PROJ_ATTACK = "overStrike";

    private Consumer<EntityLivingBase> prevAttack;
    public EntityAzureWraith(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.0f);
    }



    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(STRIKING, Boolean.valueOf(false));
        this.dataManager.register(OVERSTRIKE, Boolean.valueOf(false));
    }

    public void setStriking(boolean value) {
        this.dataManager.set(STRIKING, Boolean.valueOf(value));
    }
    public void setOverstrike(boolean value) {
        this.dataManager.set(OVERSTRIKE, Boolean.valueOf(value));
    }

    public boolean isStriking() {
        return this.dataManager.get(STRIKING);
    }

    public boolean isOverStriking() {
        return this.dataManager.get(OVERSTRIKE);
    }



    //Circle Strike Method
    private final Consumer<EntityLivingBase> circleStrike = (target) -> {
        this.setStriking(true);
        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1, 0)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .disablesShields().build();
            float damage = 7.0f;
            ModUtils.handleAreaImpact(2.0f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (getRNG().nextFloat()) * 0.4f + 0.8f);
        }, 26);


        addEvent(() -> this.setStriking(false), 40);
    };
    //Over head Strike
    private final Consumer<EntityLivingBase> overStrike = (target) -> {
        this.setOverstrike(true);

            addEvent(() -> {
                Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 1, 0)));
                Vec3d myPos = getPositionVector();
                Vec3d targetPos = target.getPositionVector();
                Vec3d dir = targetPos.subtract(myPos).normalize();
                AtomicReference<Vec3d> spawnPos = new AtomicReference<>(myPos);
                int spawn1 = 2;

                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .build();
                float damage = 5.0f;
                ModUtils.handleAreaImpact(2.0f, (e) -> damage, this, offset, source, 0.3f, 0, false);

                        ModUtils.lineCallback(myPos.add(dir), myPos.add(dir.scale(spawn1)), spawn1 * 2, (pos, r) -> {

                            spawnPos.set(pos);
                        });
                        Vec3d initPos = spawnPos.get();
                        EntityWraithHand crystal = new EntityWraithHand(this.world);
                        BlockPos seePos = new BlockPos(initPos.x, initPos.y, initPos.z);
                        crystal.setPosition(seePos);
                        this.world.spawnEntity(crystal);

            }, 25);

            addEvent(() -> {
                Vec3d myPos = getPositionVector();
                Vec3d targetPos = target.getPositionVector();
                Vec3d dir = targetPos.subtract(myPos).normalize();
                AtomicReference<Vec3d> spawnPos = new AtomicReference<>(myPos);
                int spawn2 = 4;

                ModUtils.lineCallback(myPos.add(dir), myPos.add(dir.scale(spawn2)), spawn2 * 2, (pos, r) -> {

                    spawnPos.set(pos);
                });
                Vec3d initPos = spawnPos.get();
                EntityWraithHand crystal = new EntityWraithHand(this.world);
                BlockPos seePos = new BlockPos(initPos.x, initPos.y, initPos.z);
                crystal.setPosition(seePos);
                this.world.spawnEntity(crystal);
            }, 29);

        addEvent(() -> {
            Vec3d myPos = getPositionVector();
            Vec3d targetPos = getAttackTarget().getPositionVector();
            Vec3d dir = targetPos.subtract(myPos).normalize();
            AtomicReference<Vec3d> spawnPos = new AtomicReference<>(myPos);
            int spawn3 = 6;

            ModUtils.lineCallback(myPos.add(dir), myPos.add(dir.scale(spawn3)), spawn3 * 2, (pos, r) -> {

                spawnPos.set(pos);
            });
            Vec3d initPos = spawnPos.get();
            EntityWraithHand crystal = new EntityWraithHand(this.world);
            BlockPos seePos = new BlockPos(initPos.x, initPos.y, initPos.z);
            crystal.setPosition(seePos);
            this.world.spawnEntity(crystal);
        }, 33);

        addEvent(() -> {
            Vec3d myPos = getPositionVector();
            Vec3d targetPos = getAttackTarget().getPositionVector();
            Vec3d dir = targetPos.subtract(myPos).normalize();
            AtomicReference<Vec3d> spawnPos = new AtomicReference<>(myPos);
            int spawn4 = 8;
            ModUtils.lineCallback(myPos.add(dir), myPos.add(dir.scale(spawn4)), spawn4 * 2, (pos, r) -> {

                spawnPos.set(pos);
            });
            Vec3d initPos = spawnPos.get();
            EntityWraithHand crystal = new EntityWraithHand(this.world);
            BlockPos seePos = new BlockPos(initPos.x, initPos.y, initPos.z);
            crystal.setPosition(seePos);
            this.world.spawnEntity(crystal);
        }, 38);



        addEvent(() -> this.setOverstrike(false), 50);
    };
    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0F, 50, 9F, 0.5f));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        if (!this.isOverStriking() && !this.isStriking()) {


            double distance = Math.sqrt(distanceSq);

            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(circleStrike, overStrike));
            double[] weights = {
                    // All the values to determine between the two attack
                      1 / distance, // Simple Strike for the Wraith
                    (distance > 3) ? distance * 0.08 : 0 //Projectile Scythe Swipe, starts at 8 distance but will be greater until equal too at less that 4
            };


            prevAttack = ModRandom.choice(attacks, rand, weights).next();
            prevAttack.accept(target);
        }
        return 50;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "azure_wraith_idle", 0, this::predicateWraith));
        animationData.addAnimationController(new AnimationController(this, "azure_wraith_attack_Move", 0, this::predicateWraithAttack));
    }

    private <E extends IAnimatable>PlayState predicateWraith(AnimationEvent<E> event) {
        if (!this.isStriking() && !this.isOverStriking()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(WRAITH_WALK, true));

            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(WRAITH_IDLE, true));
            }
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicateWraithAttack(AnimationEvent<E> event) {
        if (this.isStriking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WRAITH_ATTACK, false));
            return PlayState.CONTINUE;
        }
        if (this.isOverStriking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WRAITH_PROJ_ATTACK, false));
            return PlayState.CONTINUE;
        }


        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();

    }
    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsHandler.ENTITY_WRAITH_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsHandler.ENTITY_WRAITH_HURT;
    }


}
