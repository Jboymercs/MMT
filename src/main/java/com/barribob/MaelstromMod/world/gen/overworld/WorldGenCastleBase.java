package com.barribob.MaelstromMod.world.gen.overworld;

import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Map;
import java.util.Random;

public class WorldGenCastleBase extends WorldGenStructure {
    StructureBoundingBox boundingBox;
    int randOffset;
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenCastleBase(String name) {
        super(name);
    }

    @Override
    public boolean generate(World worldIn, Random random, BlockPos blockPos) {
        return super.generate(worldIn, random, blockPos);
    }

    public static int findSurface(World world, BlockPos pos, int randomOffset, int maxHeight, int minHeight) {
        IBlockState topBlock = world.getBiome(pos).topBlock;
        IBlockState fillerBlock = world.getBiome(pos).fillerBlock;
        IBlockState stone = Blocks.STONE.getDefaultState();
        for(int y = maxHeight - pos.getY(); y > minHeight - pos.getY(); y--) {
            if(!(world.getBlockState(pos.add(0, y, 0)).isFullBlock())) continue;
            IBlockState firstFullBlock = world.getBlockState(pos.add(0, y, 0));
            if(firstFullBlock != topBlock && firstFullBlock != fillerBlock && firstFullBlock != stone) break;
            return y + randomOffset;
        }
        System.out.println("Failed to find Gen Height");
        return -1;
    }

    public static void fillInMaterial(World world, BlockPos pos, int maxDepth) {
        for(int y = pos.getY(); y > maxDepth; y++) {
            BlockPos DPos = pos.add(0, -y, 0);
            if(!world.getBlockState(DPos).isFullBlock()) {
                System.out.println("Placing Blocks");
                world.setBlockState(DPos, Blocks.COBBLESTONE.getDefaultState());
            } else {
                break;
            }
          }
        }



}
