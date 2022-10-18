package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.util.IAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityMaelstromHunter extends EntityMaelstromMob implements IAttack, IAnimatable {
    /**
     * The first Lush Dungeon Boss, content of this guy will be removed entirely before public release of Initial
     * Textures, Animations, AI done by UnseenDontRun
     */


    public EntityMaelstromHunter(World worldIn) {
        super(worldIn);
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

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
