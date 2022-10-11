package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityAzureBeetle extends EntityLeveledMob implements IAnimatable {

    public static final DataParameter<Boolean> FLYING = EntityDataManager.<Boolean>createKey(EntityLeveledMob.class, DataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);
    public final String IDLE_ANIM = "idle";
    public final String WALK_ANIM = "walk";
    public final String FLY_ANIM = "fly";


    public EntityAzureBeetle(World worldIn) {
        super(worldIn);
        this.setSize(0.3f, 0.8f);

        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
    }
    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FLYING, Boolean.valueOf(false));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "beetle_idle", 0, this::predicateBeetle));

    }
    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(5, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0f, 0.2f, 0.4f));


    }
    @Override
    public void onUpdate () {
      super.onUpdate();
      this.setNoGravity(true);
      this.noClip = false;

    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);

    }

    private <E extends IAnimatable>PlayState predicateBeetle(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isFlying() && this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WALK_ANIM, true));
            return PlayState.CONTINUE;
        }
        if (!this.onGround || this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(FLY_ANIM, true));
            return PlayState.CONTINUE;
        }
        if(!this.isFlying() || this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }


    @Override
    public void travel(float strafe, float vertical, float forward) {

        ModUtils.aerialTravel(this, strafe, vertical, forward);
    }
    public void setFlying() {
        boolean value;
        if (this.onGround) {
            value = false;
            this.dataManager.set(FLYING, Boolean.valueOf(value));

        }
        value = true;
        this.dataManager.set(FLYING, Boolean.valueOf(value));

    }

    public boolean isFlying() {
        return this.dataManager.get(FLYING);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
