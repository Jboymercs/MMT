package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
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

    public final String LEGS_WALK_ANIM = "walk";
    public final String LEGS_RUN_ANIM = "run";
    public final String ARMS_WALK_ANIM = "walkArms";
    public final String ARMS_RUN_ANIM = "runArms";
    public final String IDLE_ANIM = "idle";

    public EntityPlayerBase(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.9f);
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        return 0;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "player_idle", 0, this::predicatePlayerIdleHandler));
    animationData.addAnimationController(new AnimationController(this, "player_run", 0, this::predicatePlayerArmsHandler));
    }
    private <E extends IAnimatable>PlayState predicatePlayerIdleHandler(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(LEGS_WALK_ANIM, true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable>PlayState predicatePlayerArmsHandler(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ARMS_WALK_ANIM, true));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
