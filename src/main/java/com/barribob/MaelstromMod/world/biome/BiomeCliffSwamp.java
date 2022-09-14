package com.barribob.MaelstromMod.world.biome;

import com.barribob.MaelstromMod.blocks.BlockModBush;
import com.barribob.MaelstromMod.entity.entities.EntityCliffFly;
import com.barribob.MaelstromMod.entity.entities.EntitySwampCrawler;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenModFoliage;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Biome for the lower section of the cliff dimension
 */
public class BiomeCliffSwamp extends BiomeDifferentStone {
    public final static IBlockState log = ModBlocks.SWAMP_LOG.getDefaultState();
    public final static IBlockState leaf = ModBlocks.SWAMP_LEAVES.getDefaultState();

    public BiomeCliffSwamp() {
        super(new BiomeProperties("Cliff Swamp").setBaseHeight(-1.5F).setHeightVariation(0.1F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(4864285), Blocks.GRASS,
                Blocks.DIRT);

        this.decorator.treesPerChunk = 8;
        this.decorator.flowersPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.mushroomsPerChunk = 8;
        this.decorator.bigMushroomsPerChunk = 1;
        this.decorator.reedsPerChunk = 10;
        this.decorator.clayPerChunk = 1;
        this.decorator.waterlilyPerChunk = 4;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.grassPerChunk = 8;

        this.spawnableMonsterList.add(new SpawnListEntry(EntitySwampCrawler.class, 10, 1, 5));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCliffFly.class, 1, 1, 1));
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        WorldGenAbstractTree jungleTree = new WorldGenTrees(false, 4 + rand.nextInt(7), log, leaf, true);
        WorldGenAbstractTree bigJungleTree = new WorldGenMegaJungle(false, 8, 18, log, leaf);
        WorldGenAbstractTree swampTree = new WorldGenSwampTree(true);

        if (rand.nextFloat() > 0.96) {
            return bigJungleTree;
        } else if (rand.nextFloat() > 0.96) {
            return jungleTree;
        } else if (rand.nextFloat() > 0.8) {
            if (rand.nextInt() == 2) {
                new WorldGenShrub(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState());
            }
            return new WorldGenShrub(log, leaf);
        } else if (rand.nextFloat() > 0.8) {
            return new WorldGenBigTree(false);
        } else if (rand.nextFloat() > 0.8) {
            return new WorldGenCanopyTree(false);
        }
        return swampTree;
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        if (rand.nextInt(3) == 0) {
            return new WorldGenModFoliage(new BlockModBush[]{(BlockModBush) ModBlocks.FIRE_GRASS}, 128);
        }
        return rand.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

        for (int i = 0; i < 7; ++i) {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
        }
        super.decorate(worldIn, rand, pos);
    }

    @Override
    public void generateTopBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Block stoneBlock) {
        double d0 = GRASS_COLOR_NOISE.getValue(x * 0.25D, z * 0.25D);

        if (d0 > 0.0D) {
            int i = x & 15;
            int j = z & 15;

            for (int k = 255; k >= 0; --k) {
                if (chunkPrimerIn.getBlockState(j, k, i).getMaterial() != Material.AIR) {
                    if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER) {
                        chunkPrimerIn.setBlockState(j, k, i, WATER);

                        if (d0 < 0.12D) {
                            chunkPrimerIn.setBlockState(j, k + 1, i, Blocks.WATERLILY.getDefaultState());
                        }
                    }

                    break;
                }
            }
        }

        super.generateTopBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal, stoneBlock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos) {
        double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
        return d0 < -0.1D ? 4605755 : 5325610;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos) {
        return 6975545;
    }
}
