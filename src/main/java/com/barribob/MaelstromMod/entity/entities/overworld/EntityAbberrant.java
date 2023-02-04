package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Consumer;

public class EntityAbberrant extends EntityLeveledMob implements IAnimatable {
    /**
     * The Locals of the Nether Dungeon, A specialist of it's era.
     */
    private AnimationFactory factory = new AnimationFactory(this);

    private final String MOVE_ANIM = "move";
    private final String IDLE_ANIM = "idle";
    private final String BLINK = "blink";
    private final String RANGED_ANIM = "castSpell";
    private final String STRIKE_ANIM = "simpleStrike";


    private Consumer<EntityLivingBase> prevAttacks;


    public EntityAbberrant(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
        this.isImmuneToExplosions();
        this.setSize(0.3f, 2.0f);
    }
    @Override
    public void entityInit() {
        super.entityInit();

    }
    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void initEntityAI() {
        super.entityInit();
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPigZombie>(this, EntityPigZombie.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "ab_idle", 0, this::predicateIdle));

    }

    private <E extends IAnimatable>PlayState predicateIdle(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(MOVE_ANIM, true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(BLINK, true));
     return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

        super.readEntityFromNBT(compound);
    }
}
