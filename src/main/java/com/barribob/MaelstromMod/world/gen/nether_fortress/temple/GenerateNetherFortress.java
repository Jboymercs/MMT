package com.barribob.MaelstromMod.world.gen.nether_fortress.temple;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityNetherKnight;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridge;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeN;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeS;
import com.barribob.MaelstromMod.world.gen.nether_fortress.bridge.WorldGenNetherBridgeW;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenerateNetherFortress extends WorldGenNetherBase {



    /**
     * @param name The name of the structure to load in the nbt file
     */
    public GenerateNetherFortress(int yOffset) {

        super("nether/temple", 0);

    }

    /**
     * The Main boss room that will spawn the temple.
     * @param world
     * @param pos
     * @param rotation
     */
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("boss")) {
            world.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
            TileEntity tile = world.getTileEntity(pos);

            if(tile instanceof TileEntityMobSpawner) {
                // Spawns the Nether Knight boss
                ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().setData(new MobSpawnerLogic.MobSpawnData(ModEntities.getID(EntityNetherKnight.class), Element.NONE), 1, LevelHandler.INVASION, 8);
            }
        }
        if(function.startsWith("east")) {
            new WorldGenNetherBridge(0).generate(world, world.rand, pos);
        }
        if(function.startsWith("west")) {
            BlockPos pos1 = pos.add(new BlockPos(-21, 0, -6));
            new WorldGenNetherBridgeW(0).generate(world, world.rand, pos1);
        }
        if(function.startsWith("north")) {
            BlockPos pos2 = pos.add(new BlockPos(0, 0, -6));
            new WorldGenNetherBridgeN(0).generate(world, world.rand, pos2);
        }
        if(function.startsWith("south")) {
            BlockPos pos3 = pos.add(new BlockPos(-21, 0, 0));
            new WorldGenNetherBridgeS(0).generate(world, world.rand, pos3);
        }
    }








}
