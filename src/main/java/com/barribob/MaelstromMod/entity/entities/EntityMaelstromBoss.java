package com.barribob.MaelstromMod.entity.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityMaelstromBoss extends EntityLeveledMob{

    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityLeveledMob.class, DataSerializers.BOOLEAN);

    public EntityMaelstromBoss(World worldIn) {
        super(worldIn);
    }

    protected void initEntityAI() {
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
    }

    protected void GatherBossInfo() {

    }

    @Override
   protected void entityInit() {
        super.entityInit();
       this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
   }


    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }


}
