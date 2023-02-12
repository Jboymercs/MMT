package com.barribob.MaelstromMod.world.gen.nether_fortress.tower;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridge;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeN;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeW;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndE;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndN;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndW;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenNetherTowerN extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenNetherTowerN( int yOffset) {
        super("nether/north_tower", 0);
    }
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        float value = ModRandom.getFloat(6);
        if(function.startsWith("east")) {

            if(value < 6) {
                new WorldGenEndE(0).generate(world, world.rand, pos);
            }
            if(value > 6) {
                new WorldGenNetherBridge(0).generate(world, world.rand, pos);
            }

        }
        if(function.startsWith("north")) {
            if(value > 1) {
                BlockPos pos1 = pos.add(new BlockPos(0,0,-6));
                new WorldGenEndN(0).generate(world, world.rand, pos1);
            }
            if(value < 1) {
                BlockPos pos2 = pos.add(new BlockPos(0, 0, -6));
                new WorldGenNetherBridgeN(0).generate(world, world.rand, pos2);
            }

        }
        if(function.startsWith("west")) {
                BlockPos pos2 = pos.add(new BlockPos(-4,0,-6));
                new WorldGenEndW(0).generate(world, world.rand, pos2);
        }
    }
}
