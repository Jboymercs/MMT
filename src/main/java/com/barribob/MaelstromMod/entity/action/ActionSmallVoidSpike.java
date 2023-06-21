package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.function.Function;
import java.util.function.Supplier;

public class ActionSmallVoidSpike implements IAction{
    Supplier<EntityLivingBase> spikeSupplier;

    public ActionSmallVoidSpike(Supplier<EntityLivingBase> spikeSupplier) {
        this.spikeSupplier = spikeSupplier;

    }
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {

        Function<Vec3d, Runnable> spikeVolley = (offset) -> () -> {

            EntityLivingBase entityLivingBase = spikeSupplier.get();

            actor.world.spawnEntity(entityLivingBase);

        };



    }


}
