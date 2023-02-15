package com.barribob.MaelstromMod.world.gen.nether_fortress.randomRoom;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldGenSmallRoom extends WorldGenNetherBase {
    Random random;


    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenSmallRoom(int yOffset) {
        super("nether/room_s1", 0);
    }


    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

}
