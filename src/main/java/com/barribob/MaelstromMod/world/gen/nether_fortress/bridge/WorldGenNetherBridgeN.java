package com.barribob.MaelstromMod.world.gen.nether_fortress.bridge;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndE;
import com.barribob.MaelstromMod.world.gen.nether_fortress.tower.WorldGenNetherTowerN;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenNetherBridgeN extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenNetherBridgeN( int yOffset) {
        super("nether/east_bridge", 0);
    }


    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.COUNTERCLOCKWISE_90);

    }
    boolean hasSpawned = false;

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        float value = ModRandom.getFloat(6);
        int generation = random.nextInt(6);


            if(function.startsWith("connect")) {
                if(generation == 0 && !hasSpawned) {
                    BlockPos pos2 = pos.add(new BlockPos(0, 0, -6));
                    new WorldGenNetherBridgeN(0).generate(world, world.rand, pos2);
                    hasSpawned = true;
                }
                if(generation != 0) {
                    BlockPos pos1 = pos.add(new BlockPos(-2,0,-10));
                    new WorldGenNetherTowerN(0).generate(world, world.rand, pos1);
                    hasSpawned = false;
                }

            }

    }
}
