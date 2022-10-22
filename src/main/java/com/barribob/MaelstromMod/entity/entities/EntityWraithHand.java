package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.init.MMAnimations;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityWraithHand extends EntityLeveledMob implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    public EntityWraithHand(World worldIn) {
        super(worldIn);
        this.setSize(0.3f, 0.3f);
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();

    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "hand_grab", 0, this::predicateHand));
    }
    private <E extends IAnimatable>PlayState predicateHand(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idleHand", false));
        return PlayState.CONTINUE;
    }
}
