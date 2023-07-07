package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityAbstractVoidBlossom extends EntityLeveledMob implements IEntityMultiPart {


    private static final DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SPIKE_ATTACK = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SPIKE_WAVE = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEAF_ATTACK = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SPORE_ATTACK = EntityDataManager.createKey(EntityVoidBlossom.class, DataSerializers.BOOLEAN);

    private final MultiPartEntityPart[] hitboxParts;

    private final MultiPartEntityPart model = new MultiPartEntityPart(this, "model", 0.0f, 0.0f);

    public EntityAbstractVoidBlossom(World worldIn) {
        super(worldIn);

        this.hitboxParts = new MultiPartEntityPart[]{model};
    }

    @Override
    public void entityInit() {
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(SPIKE_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SPIKE_WAVE, Boolean.valueOf(false));
        this.dataManager.register(LEAF_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SPORE_ATTACK, Boolean.valueOf(false));
        super.entityInit();
    }

    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public void setSpikeAttack(boolean value) {this.dataManager.set(SPIKE_ATTACK, Boolean.valueOf(value));}
    public void setSpikeWave(boolean value) {this.dataManager.set(SPIKE_WAVE, Boolean.valueOf(value));}
    public void setLeafAttack(boolean value) {this.dataManager.set(LEAF_ATTACK, Boolean.valueOf(value));}
    public void setSporeAttack(boolean value) {this.dataManager.set(SPORE_ATTACK, Boolean.valueOf(value));}

    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public boolean isSpikeAttack() {return this.dataManager.get(SPIKE_ATTACK);}
    public boolean isSpikeWave() {return this.dataManager.get(SPIKE_WAVE);}
    public boolean isLeafAttack() {return this.dataManager.get(LEAF_ATTACK);}
    public boolean isSporeAttack() {return this.dataManager.get(SPORE_ATTACK);}


    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        Vec3d[] avec3d = new Vec3d[this.hitboxParts.length];
        for (int j = 0; j < this.hitboxParts.length; ++j) {
            avec3d[j] = new Vec3d(this.hitboxParts[j].posX, this.hitboxParts[j].posY, this.hitboxParts[j].posZ);
        }



        Vec3d knightPos = this.getPositionVector();
        ModUtils.setEntityPosition(model, knightPos);

        for (int l = 0; l < this.hitboxParts.length; ++l) {
            this.hitboxParts[l].prevPosX = avec3d[l].x;
            this.hitboxParts[l].prevPosY = avec3d[l].y;
            this.hitboxParts[l].prevPosZ = avec3d[l].z;
        }
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

}
