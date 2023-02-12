package com.barribob.MaelstromMod.world.gen.nether_fortress.bridge;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndE;
import com.barribob.MaelstromMod.world.gen.nether_fortress.tower.WorldGenNetherTowerS;
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


    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.CLOCKWISE_90);

    }
    boolean hasSpawned = false;

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {

        float value = ModRandom.getFloat(6);
        int generation = random.nextInt(6);


            if(function.startsWith("connect")) {
                if(generation == 0 && !hasSpawned) {
                    BlockPos pos3 = pos.add(new BlockPos(-21, 0, 0));
                    new WorldGenNetherBridgeS(0).generate(world, world.rand, pos3);
                    hasSpawned = true;
                }
                if(generation != 0) {
                    BlockPos blockPos = pos.add(new BlockPos(-8, 0, 0));
                    new WorldGenNetherTowerS(0).generate(world, world.rand, blockPos);
                    hasSpawned = false;
                }

            }

    }
}
