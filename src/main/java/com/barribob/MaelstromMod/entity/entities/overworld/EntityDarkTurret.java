package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityDarkTurret extends EntityLeveledMob implements IAttack, IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    public boolean hasPlayed;

    public EntityDarkTurret(World worldIn) {
        super(worldIn);
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        return 0;
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        stateTimer();
    }

    public void stateTimer() {
        if(handleState() && !hasPlayed) {
            //Play Rise Animation

        }
        if(!handleState() && !hasPlayed) {
            //Play Fall Animation after the timer
            addEvent(()-> {}, 100);
            hasPlayed = true;
        }
    }

    public boolean handleState() {
        EntityLivingBase target = this.getAttackTarget();
        if(target != null) {
            //Intiates the Rise Animation
            hasPlayed = false;
            return true;
        }
        //Sent to a timer that will eventually bring it back down if returned false

            return false;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "turret_idle_handler", 0, this::PredicateIdle));
    animationData.addAnimationController(new AnimationController(this, "turret_states", 0, this::PredicateStates));
    animationData.addAnimationController(new AnimationController(this, "turret_attacks", 0, this::PredicateInitiate));
    }

    //Handles Idle Animations
    private <E extends IAnimatable>PlayState PredicateIdle(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }
    //Handles State Animations
    private<E extends IAnimatable>PlayState PredicateStates(AnimationEvent<E> event) {

        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
    //Handles Attack Animations
    private<E extends IAnimatable>PlayState PredicateInitiate(AnimationEvent<E> event) {
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
