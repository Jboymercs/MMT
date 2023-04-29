package com.barribob.MaelstromMod.world.gen.overworld;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenCastleTop extends WorldGenCastleBase{
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenCastleTop() {
        super("overworld/castle_top");
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("south_b")) {

        }
        if(function.startsWith("west_b")) {

        }
    }
}
