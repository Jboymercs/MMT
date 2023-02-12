package com.barribob.MaelstromMod.world.gen.nether_fortress.tower;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeN;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeW;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndN;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndS;
import com.barribob.MaelstromMod.world.gen.nether_fortress.end.WorldGenEndW;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenNetherTowerW extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenNetherTowerW( int yOffset) {
        super("nether/west_tower", 0);
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        float value = ModRandom.getFloat(6);



        if(function.startsWith("south")) {
            BlockPos pos3 = pos.add(new BlockPos(-4,0,0));
            new WorldGenEndS(0).generate(world, world.rand, pos3);
        }
        if(function.startsWith("west")) {

            if(value > 1) {
                BlockPos pos2 = pos.add(new BlockPos(-4,0,-6));
                new WorldGenEndW(0).generate(world, world.rand, pos2);
            }
            if(value < 1) {
                BlockPos pos1 = pos.add(new BlockPos(-21, 0, -6));
                new WorldGenNetherBridgeW(0).generate(world, world.rand, pos1);
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
    }
}
