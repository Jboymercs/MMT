package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;

import java.util.function.Function;
import java.util.function.Supplier;

public class ActionLeafAttack implements IAction{
    Supplier<Projectile> projectileSupplier;
    float velocity;

    public ActionLeafAttack(Supplier<Projectile> projectileSupplier, float velocity) {
        this.projectileSupplier = projectileSupplier;
        this.velocity = velocity;
    }
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        Vec3d targetPos = target.getPositionEyes(1);
        Vec3d fromTargetToActor = actor.getPositionVector().subtract(targetPos);

        Vec3d lineDirection = ModUtils.rotateVector2(
                        fromTargetToActor.crossProduct(ModUtils.Y_AXIS),
                        fromTargetToActor,
                        ModRandom.range(-45, 45))
                .normalize()
                .scale(4);

        Vec3d lineStart = targetPos.subtract(lineDirection);
        Vec3d lineEnd = targetPos.add(lineDirection);

        ModUtils.lineCallback(lineStart, lineEnd, 11, (pos, i) -> {
            Projectile projectile = projectileSupplier.get();
            projectile.setTravelRange(30);
            projectile.setNoGravity(true);
            ModUtils.throwProjectile(actor, pos, projectile, 0, velocity);
        });
    }
}
