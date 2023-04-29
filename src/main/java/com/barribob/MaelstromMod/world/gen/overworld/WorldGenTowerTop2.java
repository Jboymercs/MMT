package com.barribob.MaelstromMod.world.gen.overworld;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTowerTop2 extends WorldGenCastleBase{
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenTowerTop2() {
        super("overworld/tower_top_2");
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);
    }
}
