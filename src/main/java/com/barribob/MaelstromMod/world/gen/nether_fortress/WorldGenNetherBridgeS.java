package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenNetherBridgeS extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenNetherBridgeS( int yOffset) {
        super("nether/east_bridge", 0);
    }

    public boolean genSuccess;
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.CLOCKWISE_90);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        int generation = random.nextInt(3);

        if(generation == 0) {
            if(function.startsWith("connect")) {
                genSuccess = true;
                BlockPos pos3 = pos.add(new BlockPos(-21, 0, 0));
                new WorldGenNetherBridgeS(0).generate(world, world.rand, pos3);
            } else {
                genSuccess = true;
                new WorldGenEndE(0).generate(world, world.rand, pos);
            }



        }
        if(!genSuccess) {
            if(function.startsWith("connect")) {

            }
        }
    }
}
