package com.barribob.MaelstromMod.world.dimension.nether;

import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.nether_fortress.MapGenNetherFortress;
import com.barribob.MaelstromMod.world.gen.nether_fortress.NetherFortress;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorNether extends WorldChunkGenerator {

    public static final int STRUCTURE_SPACING_CHUNKS = 10;
    public static final int FORTRESS_NUMBER = 0;

    public ChunkGeneratorNether(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions, Blocks.NETHERRACK, Blocks.LAVA, null);
        MapGenStructure[] structures = {new MapGenNetherFortress(STRUCTURE_SPACING_CHUNKS, FORTRESS_NUMBER, 5, this)};
        this.structures = structures;

    }





    protected void generateCaves(int x, int z, ChunkPrimer cp) {

    }


    protected void generateFeatures(BlockPos pos, Biome biome) {

    }
}
