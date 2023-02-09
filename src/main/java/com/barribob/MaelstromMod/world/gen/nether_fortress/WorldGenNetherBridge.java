package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldGenNetherBridge extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenNetherBridge( int yOffset) {
        super("nether/east_bridge", 0);
    }


    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

    public boolean genSuccess;

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        int generation = random.nextInt(3);

        if(generation == 0) {
            if(function.startsWith("connect")) {
                genSuccess = true;
                new WorldGenNetherBridge(0).generate(world, world.rand, pos);
            } else {
                genSuccess = true;
                new WorldGenEndE(0).generate(world, world.rand, pos);
            }



        }
        if(!genSuccess) {
            if(function.startsWith("connect")) {
                BlockPos pos1 = pos.add(new BlockPos(0, 0, -2));
                new WorldGenNetherTowerE(0).generate(world, world.rand, pos1);
            }
        }




    }

}
