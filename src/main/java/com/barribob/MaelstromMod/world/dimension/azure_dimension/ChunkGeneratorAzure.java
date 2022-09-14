package com.barribob.MaelstromMod.world.dimension.azure_dimension;

import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.WorldGenMaelstrom;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.MapGenMaelstromStronghold;
import com.barribob.MaelstromMod.world.gen.mineshaft.MapGenAzureMineshaft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorAzure implements IChunkGenerator {

    private final Random rand;
    private final World world;
    public ChunkGeneratorAzure(World worldIn,long seed, boolean mapfeaturesEnabled, String generationOptions) {
        this.world = worldIn;
        this.rand = new Random(seed);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        return null;
    }

    @Override
    public void populate(int x, int z) {

    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}