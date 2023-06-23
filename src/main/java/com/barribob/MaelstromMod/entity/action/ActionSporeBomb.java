package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EmtityGenericWave;
import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class ActionSporeBomb implements IActionProjectile{

    @Override
    public void performAction(Projectile actor, EntityLivingBase target) {
        ModUtils.circleCallback(2, 16, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
        ModUtils.circleCallback(3, 20, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
        ModUtils.circleCallback(4, 24, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
        ModUtils.circleCallback(5, 28, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
        ModUtils.circleCallback(6, 32, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
        ModUtils.circleCallback(7, 36, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
        ModUtils.circleCallback(8, 40, (pos)-> {
            pos = new Vec3d(pos.x, 0, pos.y).add(actor.getPositionVector());
            EmtityGenericWave spike = new EmtityGenericWave(actor.world);
            spike.setPosition(pos.x, pos.y, pos.z);
            actor.world.spawnEntity(spike);
        });
    }
}
