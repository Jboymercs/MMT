package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.packets.MessageDirectionForRender;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class LaserAction implements IGauntletAction{
    private final EntityLeveledMob entity;
    private boolean isShootingLazer;
    double maxLaserDistance;
    private final float laserExplosionSize;
    int beamLag;
    private final byte stopLaserByte;
    private final Consumer<Vec3d> onLaserImpact;

    public LaserAction(EntityLeveledMob entity, byte stopLaserByte, Consumer<Vec3d> onLaserImpact) {
        this.entity = entity;
        maxLaserDistance = entity.getMobConfig().getDouble("max_laser_distance");
        beamLag = entity.getMobConfig().getInt("beam_lag");
        laserExplosionSize = entity.getMobConfig().getInt("laser_explosion_size");
        this.stopLaserByte = stopLaserByte;
        this.onLaserImpact = onLaserImpact;
    }

    @Override
    public void doAction() {
        ModBBAnimations.animation(entity, "gauntlet.lazer_eye", false);
        entity.playSound(SoundsHandler.ENTITY_GAUNTLET_LAZER_CHARGE, 2.0f, ModRandom.getFloat(0.2f) + 1.5f);
        int chargeUpTime = 25;
        int laserEndTime = 60;
        for (int i = 0; i < chargeUpTime; i++) {
            entity.addEvent(() -> entity.world.setEntityState(entity, ModUtils.PARTICLE_BYTE), i);
        }
        entity.addEvent(() -> isShootingLazer = true, chargeUpTime);
        entity.addEvent(() -> {
            isShootingLazer = false;
            // Have to add delay because there will be 5 more ticks of lazers
            entity.addEvent(() -> entity.world.setEntityState(entity, stopLaserByte), beamLag + 1);
        }, laserEndTime);
    }

    @Override
    public void update() {
        if (this.isShootingLazer) {
            if (entity.getAttackTarget() != null) {
                Vec3d laserShootPos = entity.getAttackTarget().getPositionVector();
                entity.addEvent(() -> {

                    // Extend shooting beyond the target position up to 40 blocks
                    Vec3d laserDirection = laserShootPos.subtract(entity.getPositionEyes(1)).normalize();
                    Vec3d lazerPos = laserShootPos.add(laserDirection.scale(maxLaserDistance));
                    // Ray trace both blocks and entities
                    RayTraceResult raytraceresult = entity.world.rayTraceBlocks(entity.getPositionEyes(1), lazerPos, false, true, false);
                    if (raytraceresult != null) {
                        lazerPos = onLaserImpact(raytraceresult);
                    }

                    for (Entity target : ModUtils.findEntitiesInLine(entity.getPositionEyes(1), lazerPos, entity.world, entity)) {
                        DamageSource source = ModDamageSource.builder()
                                .directEntity(entity)
                                .stoppedByArmorNotShields()
                                .element(entity.getElement())
                                .type(ModDamageSource.MAGIC)
                                .build();
                        target.attackEntityFrom(source, entity.getAttack() * entity.getConfigFloat("laser_damage"));
                    }

                    ModUtils.addEntityVelocity(entity, laserDirection.scale(-0.03f));

                    Main.network.sendToAllTracking(new MessageDirectionForRender(entity, lazerPos), entity);
                }, beamLag);
            } else {
                // Prevent the gauntlet from instantly locking onto other targets with the lazer.
                isShootingLazer = false;
                entity.addEvent(() -> entity.world.setEntityState(entity, stopLaserByte), beamLag + 1);
            }
        }
    }

    private Vec3d onLaserImpact(RayTraceResult raytraceresult) {
        Vec3d hitPos = raytraceresult.hitVec;
        if(laserExplosionSize > 0) {
            entity.world.createExplosion(entity, hitPos.x, hitPos.y, hitPos.z, laserExplosionSize, ModUtils.mobGriefing(entity.world, entity));
        }

        onLaserImpact.accept(hitPos);

        if(entity.ticksExisted % 2 == 0) {
            ModUtils.destroyBlocksInAABB(ModUtils.vecBox(hitPos, hitPos).grow(0.1), entity.world, entity);
        }
        return hitPos;
    }

    @Override
    public int attackLength() {
        return 60;
    }
}
