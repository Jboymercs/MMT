package com.barribob.MaelstromMod.world.gen.overworld;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTowerExtension extends WorldGenCastleBase{
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenTowerExtension() {
        super("overworld/tower_extend");
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);
    }
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("tower_connect")) {
        BlockPos pos1 = pos.add(new BlockPos(-3,0,-3));
        new WorldGenTowerTop2().generate(world, random, pos1);
        }
        if(function.startsWith("b_connect")) {

        }
    }
}
