package com.barribob.MaelstromMod.world.gen.nether_fortress.stronghold;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import com.barribob.MaelstromMod.world.gen.nether_fortress.randomRoom.WorldGenBigRoom;
import com.barribob.MaelstromMod.world.gen.nether_fortress.randomRoom.WorldGenSmallRoom;
import com.barribob.MaelstromMod.world.gen.nether_fortress.randomRoom.WorldGenSmallRoom2;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenStronghold extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenStronghold(int yOffset) {
        super("nether/stronghold", 0);
    }
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }
    WorldGenSmallRoom S1;
    WorldGenSmallRoom2 S2;

    /**
     * The big stronghold which may hold a chance to spawn the boss room
     * @param function
     * @param pos
     * @param world
     * @param random
     */

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("room_small")) {
            float value1 = random.nextInt(2);
        if(value1 == 0) {
            new WorldGenSmallRoom(0).generate(world, world.rand, pos);
        }
        if(value1 == 1) {
            new WorldGenSmallRoom2(0).generate(world, world.rand, pos);
        }



        }
        if(function.startsWith("room_big")){
            new WorldGenBigRoom(0).generate(world, world.rand, pos);
        }
    }

}
