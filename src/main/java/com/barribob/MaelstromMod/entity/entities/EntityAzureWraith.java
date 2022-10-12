package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.util.IAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityAzureWraith extends EntityLeveledMob implements IAnimatable, IAttack {
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityAzureWraith(World worldIn) {
        super(worldIn);
    }


    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        return 0;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
