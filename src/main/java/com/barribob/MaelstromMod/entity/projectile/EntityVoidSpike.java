package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityVoidSpike extends EntityLeveledMob implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityVoidSpike(World worldIn) {
        super(worldIn);
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
