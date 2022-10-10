package com.barribob.MaelstromMod.entity.entities;

import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityAzureBeetle extends EntityLeveledMob implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);



    public EntityAzureBeetle(World worldIn) {
        super(worldIn);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "beetle_idle", 0, this::predicateBeetle));

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
