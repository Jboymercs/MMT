package com.barribob.MaelstromMod.world.gen.overworld;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenForestCastle extends WorldGenCastleBase{
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenForestCastle() {
        super("overworld/castle_bottom");
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);
    }


    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("castle_connect")) {
            BlockPos posUP = pos.add(new BlockPos(-2,1,-2));
           new WorldGenCastleTop().generateStructure(world, posUP, Rotation.NONE);
        }
    }
}
