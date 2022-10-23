package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.xml.crypto.Data;
import java.util.OptionalDouble;

public class EntityHunterMissile extends Entity implements IAnimatable {
    private EntityLeveledMob shootingEntity;
    protected static final DataParameter<Float> CLOSEST_TARGET_DISTANCE = EntityDataManager.createKey(EntityLeveledMob.class, DataSerializers.FLOAT);
    public static final float Explosion_Distance = (float) Main.mobsConfig.getDouble("maelstrom_hunter.missileDistance");
    public static final float Missile_LifeSpan = (float) Main.mobsConfig.getDouble("maelstrom_hunter.missileLifeSpan");

    private static final double explosionRadius = Math.pow(Explosion_Distance, 2);

    public EntityHunterMissile(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            if (shootingEntity == null) {
                this.setDead();
                return;
            }
            Vec3d pos = this.getPositionVector();
            OptionalDouble optionalDouble = ModUtils.getEntitiesInBox(this, ModUtils.makeBox(pos, pos).grow(20))
                    .stream()
                    .filter(EntityMaelstromMob.CAN_TARGET)
                    .mapToDouble((e) -> e.getDistanceSq(this))
                    .min();
            if (optionalDouble.isPresent()) {
                this.dataManager.set(CLOSEST_TARGET_DISTANCE, ((float) optionalDouble.getAsDouble()));
                if (optionalDouble.getAsDouble() < explosionRadius) {
                    //EXPLODE AND DIE
                }
            }





        }



    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
    @Override
    public boolean canBeCollidedWith() {return true;}

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
