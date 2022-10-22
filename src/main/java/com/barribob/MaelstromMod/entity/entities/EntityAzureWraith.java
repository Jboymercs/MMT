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

    private void spawnDeathHands(double pos2, double pos4, double pos6, double pos8, double pos10, double pos12 ) {
        BlockPos upState = new BlockPos(pos2, pos8, pos4);
        boolean indicator = false;
        double d0 = 0.0D;
        while(true) {
            if (!EntityAzureWraith.this.world.isBlockNormalCube(upState, true) && EntityAzureWraith.this.world.isBlockNormalCube(upState.down(), true)) {
                if (!EntityAzureWraith.this.world.isAirBlock(upState)) {
                    IBlockState blockState = EntityAzureWraith.this.world.getBlockState(upState);
                    AxisAlignedBB assignedDes = blockState.getCollisionBoundingBox(EntityAzureWraith.this.world, upState);

                    if (assignedDes != null) {
                        d0 = assignedDes.maxY;
                    }
                }
                indicator = true;
                break;
            }
            upState = upState.down();

            if (upState.getY() < MathHelper.floor(pos6) -1) {
                break;
            }
        }
        if (indicator) {
            //PROJECTILE
            Vec3d spawnPos = new Vec3d(pos2, upState.getY() + d0, pos4);

        }
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
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .build();
                float damage = 5.0f;
                ModUtils.handleAreaImpact(2.0f, (e) -> damage, this, offset, source, 0.3f, 0, false);
                    float f = (float) MathHelper.atan2(target.posZ - EntityAzureWraith.this.posZ, target.posX - EntityAzureWraith.this.posX);
                for (int i = 3; i < 24;  ++i) {
                    addEvent(() -> {
                    double d0 = 1.5D * (double)(1 + 1);
                        double p3 = this.posX + (double)MathHelper.cos(f) * d0;
                        double p4 = this.posZ + (double)MathHelper.sin(f) * d0;
                        EntityKnightCrystal crystal = new EntityKnightCrystal(this.world);

                        BlockPos seePos = new BlockPos(p3 + ModRandom.getFloat(2), this.posY, p4 + ModRandom.getFloat(2));
                        crystal.setPosition(seePos);
                        this.world.spawnEntity(crystal);
                }, i);
                }
            }, 25);



        addEvent(() -> this.setOverstrike(false), 50);
    };
    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0F, 50, 9F, 0.1f));
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
                    (distance < 4) ? 1 / distance : 1.4 / distance //Projectile Scythe Swipe, starts at 8 distance but will be greater until equal too at less that 4
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
    //This will handle the Idle when no other actions are going on
    private <E extends IAnimatable>PlayState predicateWraith(AnimationEvent<E> event) {

        if (event.isMoving() && !this.isStriking() && !this.isOverStriking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WRAITH_WALK, true));

        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WRAITH_IDLE, true));
        }

        return PlayState.CONTINUE;
    }
    //The Moving was put in here due to the moving animation stopping every so often
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
        if (this.isStriking() || this.isOverStriking()) {
            motionZ = 0;
            motionX = 0;
        }
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
