package com.barribob.MaelstromMod.util.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Responsible for teleporting the player between dimensions
 */
public class Teleport extends Teleporter {
    private final WorldServer world;
    private double x, y, z;

    public Teleport(WorldServer world, double x, double y, double z) {
        super(world);
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        this.world.getBlockState(new BlockPos((int) x, (int) y, (int) z));
        entityIn.setPosition(x, y, z);
        entityIn.motionX = 0;
        entityIn.motionY = 0;
        entityIn.motionZ = 0;
    }

    public static void teleportToDimension(EntityPlayerMP player, int dimension, Teleporter teleporter) {
        player.changeDimension(dimension, teleporter);
    }
}
