package com.barribob.MaelstromMod.world.gen.overworld;

import com.barribob.MaelstromMod.util.ModRandom;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenCastlePillar extends WorldGenCastleBase{

    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenCastlePillar() {
        super("overworld/castle_pillar");
    }
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        int maxHeight = 100;
        int minheight = 50;
        int randomOffset = ModRandom.range(3, 10);
        int y = findSurface(world, pos, randomOffset, maxHeight, minheight);

        BlockPos pos1 = new BlockPos(pos.getX(), y, pos.getZ());

        System.out.println("Spawned at" + pos1.toString());
        super.generateStructure(world, pos1, Rotation.NONE);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if (function.startsWith("c")) {
            generatePillar(world, random, pos);
        }
        if(function.startsWith("load_castle")) {
            new WorldGenForestCastle().generate(world, random, pos);
        }
        if(function.startsWith("tower_south")) {
            int maxHeight = 100;
            int minHeight = 50;
            int randomOffest = ModRandom.range(3, 10);
            int y = findSurface(world, pos, randomOffest, maxHeight, minHeight);
            BlockPos southPos = pos.add(new BlockPos(-5,y,19));
            new WorldGenPillar().generate(world, random, southPos);
            BlockPos bridgePosition = southPos.add(new BlockPos(-3, 18, 3));

        }
        if(function.startsWith("tower_west")) {
            int maxHeight = 100;
            int minHeight = 50;
            int randomOffset = ModRandom.range(3, 10);
            int y = findSurface(world, pos, randomOffset, maxHeight, minHeight);
            BlockPos westPos = pos.add(new BlockPos(-34,y,0));
            new WorldGenPillar().generate(world, random, westPos);

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
