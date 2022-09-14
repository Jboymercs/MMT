package com.barribob.MaelstromMod.world.biome;

import com.barribob.MaelstromMod.blocks.BlockModBush;
import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.gen.foliage.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The biome for the azure dimension.
 */
public class BiomeAzure extends Biome {

    public BiomeAzure() {

        super(new BiomeProperties("azure_dungeon").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainDisabled().setWaterColor(10252253));
    }


    //Do not decorate dimension
    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {

    }
    @Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
        return new ArrayList<SpawnListEntry>(); // Don't spawn any mobs
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos) {
        double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
        return d0 < -0.1D ? 3109474 : 2837034;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos) {
        return 2837034;
    }


}
