package com.barribob.MaelstromMod.world.dimension.nether;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionNether extends WorldProvider {


    @Override
    public DimensionType getDimensionType() {
        return null;
    }

    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorNether(world, world.getSeed(), true, "");
    }

}
