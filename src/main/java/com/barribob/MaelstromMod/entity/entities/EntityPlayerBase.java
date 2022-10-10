package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAIFollowAttackers;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityPlayerBase extends EntityMaelstromMob implements IAnimatable, IAttack {
    /**
     * This will first serve as a test, then expand into being the base for Players and NPCS found in Maelstrom
     */
    private AnimationFactory factory = new AnimationFactory(this);


    protected static final DataParameter<Boolean> RUN = EntityDataManager.<Boolean>createKey(EntityPlayerBase.class, DataSerializers.BOOLEAN);

    public final String LEGS_WALK_ANIM = "walk";
    public final String LEGS_RUN_ANIM = "run";
    public final String ARMS_WALK_ANIM = "walkArms";
    public final String ARMS_RUN_ANIM = "runArms";
    public final String IDLE_ANIM = "idle";

    public final String ATTACK_ANIM = "SSattack1";

    public EntityPlayerBase(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.9f);

    }
    public void entityInit() {
        super.entityInit();

        this.dataManager.register(RUN, Boolean.valueOf(false));
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
            this.setfightMode(true);
            addEvent(() -> {
                Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec());
                this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                ModUtils.handleAreaImpact(3f, (e) -> this.getAttack(), this, pos, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.20f, 0, false);
            }, 8);
            addEvent(()-> EntityPlayerBase.super.setfightMode(false), 10);
        return 30;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }

    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.9f, 30, 3f, 0.1f));
    }


    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "player_walk", 0, this::predicatePlayerLegsHandler));
    animationData.addAnimationController(new AnimationController(this, "player_run", 0, this::predicatePlayerArmsHandler));
    animationData.addAnimationController(new AnimationController(this, "player_idle", 0, this::predicatePlayerIdle));
    }

    private <E extends IAnimatable> PlayState predicatePlayerIdle(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        return PlayState.CONTINUE;
    }
    private <E extends IAnimatable>PlayState predicatePlayerLegsHandler(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isRunning()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(LEGS_WALK_ANIM, true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isRunning()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(LEGS_RUN_ANIM, true));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;

    }

    private <E extends IAnimatable>PlayState predicatePlayerArmsHandler(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isfightMode() && !this.isRunning()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ARMS_WALK_ANIM, true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isRunning() && !this.isfightMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ARMS_RUN_ANIM, true));
            System.out.println("Playing Run Arms");
            return PlayState.CONTINUE;
        }
        if (this.isfightMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ATTACK_ANIM, false));
            return PlayState.CONTINUE;
        }

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }



    @Override
    protected void setEquipment() {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD, 1));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.getAttackTarget() != null) {
            this.setRunning(true);
        } else {
        this.setRunning(false);
        }
    }
    public void setRunning(boolean value) {
         this.dataManager.set(RUN, Boolean.valueOf(value));
    }
    public boolean isRunning() {
        return this.dataManager.get(RUN);
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


}
