package com.barribob.MaelstromMod.entity.entities.overworld;


import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;



public class EntityReplacedZombie extends EntityLeveledMob implements IAnimatable {

    public EntityReplacedZombie(World worldIn) {
        super(worldIn);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
