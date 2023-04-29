package com.barribob.MaelstromMod.world.gen.overworld;

import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenPillar extends WorldGenCastleBase{
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenPillar() {
        super("overworld/pillar_test");
    }

    @Override
    public boolean generate(World worldIn, Random random, BlockPos blockPos) {
        return super.generate(worldIn, random, blockPos);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("c")) {
            generatePillar(world, random, pos);
        }
        if(function.startsWith("tower_base")) {
            BlockPos pos1 = pos.add(new BlockPos(-5,0,-5));
            new WorldGenTowerBase().generate(world, random, pos1);
        }
    }

    public static void generatePillar(World worldIn, Random random, BlockPos pos) {
            worldIn.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
        if(!worldIn.getBlockState(pos.down()).isFullBlock()) {
            worldIn.setBlockState(pos.down(), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(2)).isFullBlock()) {
            worldIn.setBlockState(pos.down(2), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(3)).isFullBlock()) {
            worldIn.setBlockState(pos.down(3), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(4)).isFullBlock()) {
            worldIn.setBlockState(pos.down(4), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(5)).isFullBlock()) {
            worldIn.setBlockState(pos.down(5), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(6)).isFullBlock()) {
            worldIn.setBlockState(pos.down(6), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(7)).isFullBlock()) {
            worldIn.setBlockState(pos.down(7), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(8)).isFullBlock()) {
            worldIn.setBlockState(pos.down(8), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(9)).isFullBlock()) {
            worldIn.setBlockState(pos.down(9), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(10)).isFullBlock()) {
            worldIn.setBlockState(pos.down(10), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(11)).isFullBlock()) {
            worldIn.setBlockState(pos.down(11), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(12)).isFullBlock()) {
            worldIn.setBlockState(pos.down(12), Blocks.COBBLESTONE.getDefaultState());
        }
        if(!worldIn.getBlockState(pos.down(13)).isFullBlock()) {
            worldIn.setBlockState(pos.down(13), Blocks.COBBLESTONE.getDefaultState());
        }
    }



}
