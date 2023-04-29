package com.barribob.MaelstromMod.world.gen.overworld;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTowerBase extends WorldGenCastleBase{
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenTowerBase() {
        super("overworld/tower_base");
    }
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("tower_connect")) {
            new WorldGenTowerExtension().generate(world, random, pos);
        }
    }
}
