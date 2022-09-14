package com.barribob.MaelstromMod.world.gen.maelstrom_castle;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

/**
 * The world generator for the maelstrom castle, which just offsets it a bit, and replaces certain data blocks
 */
public class WorldGenMaelstromCastle extends WorldGenStructure {
    public WorldGenMaelstromCastle(String name) {
        super(name);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
        if (function.startsWith("chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockpos = pos.down();

            TileEntity tileentity = worldIn.getTileEntity(blockpos);

            if (tileentity instanceof TileEntityChest) {
                ((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, rand.nextLong());
            }
        } else if (function.startsWith("portal")) {
            worldIn.setBlockState(pos, ModBlocks.AZURE_PORTAL.getDefaultState(), 2);
        } else if (function.startsWith("enemy")) {
            worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityShade.class), 1, 1.0f, 16);
            }

        }
    }
}
