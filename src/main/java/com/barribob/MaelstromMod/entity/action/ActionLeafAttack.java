package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;

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
        Function<Vec3d, Runnable> missile = (offset) -> () -> {
            Projectile projectile = projectileSupplier.get();
            projectile.setTravelRange(40);

            ModUtils.throwProjectile(actor, target.getPositionEyes(1),
                    projectile,
                    6.0f,
                    velocity,
                    offset);

            actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, ModRandom.getFloat(0.2f) + 1.3f);
        };
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 8.5, 3))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 8.25, 2.5))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 8.25, 2.0))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 8.0, 1.5))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 8.0, 1))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 8.0, 0.5))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 7.25, 0))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 7.25, -0.5))), 0);
        actor.addEvent(missile.apply(ModUtils.getRelativeOffset(actor, new Vec3d(0, 7.5, -1))), 0);
    }
}
