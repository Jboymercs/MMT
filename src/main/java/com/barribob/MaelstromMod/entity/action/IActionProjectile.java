package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import net.minecraft.entity.EntityLivingBase;

public interface IActionProjectile {
    void performAction(Projectile actor, EntityLivingBase target);

    IAction NONE = (actor, target) -> {
    };
}
