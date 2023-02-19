package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityShadeKnight extends EntityLeveledMob implements IAnimatable, IAttack {

    private AnimationFactory factory = new AnimationFactory(this);
    public EntityShadeKnight(World worldIn) {
        super(worldIn);
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        return 0;
    }

    @Override
    public void entityInit() {
        super.entityInit();

    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
    }

    @Override
    public void registerControllers(AnimationData animationData) {

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
