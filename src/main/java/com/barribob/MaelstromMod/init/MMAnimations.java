package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.function.Function;

public final class MMAnimations {

    //God tier almighty of handling animation sensors, will extend from the MaelstromMob class to percieve certain actions
        public static final AnimationBuilder IDLE = (new AnimationBuilder()).addAnimation("idle", true);
        public static final AnimationBuilder SHOOT = (new AnimationBuilder()).addAnimation("shoot", false);
        public static final AnimationBuilder WALK = (new AnimationBuilder()).addAnimation("walk", true);
        public static final AnimationBuilder ATTACK_SIMPLE = (new AnimationBuilder()).addAnimation("attack_simple", false);

    public MMAnimations() {

    }

    // Idle
    public static <T extends Entity & IAnimatable>AnimationController<T> IdleController(T entity) {
        return new AnimationController<>(entity, "idle", 0.0f, (event) -> {
            event.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        });
    }

    //Walk
    public static <T extends Entity & IAnimatable>AnimationController<T>WalkController(T entity) {
        return new AnimationController<>(entity, "walk", 0.0F, (event) -> {
            if (event.isMoving()) {
                event.getController().setAnimation(WALK);
                return PlayState.CONTINUE;
            } else {
                return PlayState.STOP;
            }
        });
    }

    // Attack, Shoot
    public static <T extends EntityMaelstromMob & IAnimatable>AnimationController<T> AttackController(T entity, AnimationBuilder attackAnimation) {
        return new AnimationController<>(entity, "attack", 0.0F, (event) -> {
            if(entity.isShooting()) {
                event.getController().setAnimation(attackAnimation);
                return PlayState.CONTINUE;
            } else {
                event.getController().markNeedsReload();
                return PlayState.STOP;

            }
        });
    }





}


