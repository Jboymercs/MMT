package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.entity.entities.EntityAzureWraith;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromShielder;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityAbberrant;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityNetherKnight;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

/**
* The Nether Fortress that will hold the Fallen Nether Apostle, will also have Fiery Remnants and some decent loot to it.
 */
public class FortressTemplateK extends ModStructureTemplate {


    public FortressTemplateK(TemplateManager manager, String type, BlockPos pos, Rotation rotation, int distance, boolean overwriteIn) {
        super(manager, type, pos, distance, rotation, overwriteIn);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {
        // Spawner for a Regular Chest
        if (function.startsWith("chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockPos = pos.down();
            if (rand.nextFloat() < 0.3) {
                if (sbb.isVecInside(blockPos)) {
                    TileEntity tileEntity = worldIn.getTileEntity(blockPos);

                    if (tileEntity instanceof TileEntityChest) {
                        ((TileEntityChest) tileEntity).setLootTable(LootTableHandler.GOLDEN_RUINS, rand.nextLong()); // To be changed
                    }
                }
                else {
                    worldIn.setBlockToAir(blockPos);
                }
            }
        }
        //Special Loot Chest Midtier
        if (function.startsWith("special_chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockPos = pos.down();
            if (rand.nextFloat() < 0.3) {
                if (sbb.isVecInside(blockPos)) {
                    TileEntity tileEntity = worldIn.getTileEntity(blockPos);

                    if (tileEntity instanceof TileEntityChest) {
                        ((TileEntityChest) tileEntity).setLootTable(LootTableHandler.GOLDEN_RUINS, rand.nextLong()); // To be changed
                    }
                }
                else {
                    worldIn.setBlockToAir(blockPos);
                }
            }
        }
        // Spawner for Final Chest
        else if (function.startsWith("final_chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockPos = pos.down();
            if (rand.nextFloat() < 0.3) {
                if (sbb.isVecInside(blockPos)) {
                    TileEntity tileEntity = worldIn.getTileEntity(blockPos);

                    if (tileEntity instanceof TileEntityChest) {
                        ((TileEntityChest) tileEntity).setLootTable(LootTableHandler.GOLDEN_RUINS, rand.nextLong()); // to be changed
                    }
                }
                else {
                    worldIn.setBlockToAir(blockPos);
                }
            }
        }
        // Mob Spawner for Fiery Remnants
        else if (function.startsWith("mob")) {
            if (rand.nextFloat() > 0.3) {
                worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
                TileEntity tile = worldIn.getTileEntity(pos);

                if (tile instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().setData(
                            new MobSpawnData[]{
                                    new MobSpawnData(ModEntities.getID(EntityAbberrant.class), Element.NONE, 1), // Abberrant


                            },
                            new int[]{1, 1},
                            2,
                            LevelHandler.INVASION,
                            16);
                }

            }
            else {
                worldIn.setBlockToAir(pos);
            }
        }
        // Mob Spawner for Nether Knight
        else if (function.startsWith("boss")) {
            worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
            TileEntity tile = worldIn.getTileEntity(pos);

            if(tile instanceof TileEntityMobSpawner) {
                // Spawns the Nether Knight boss
                ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().setData(new MobSpawnData(ModEntities.getID(EntityNetherKnight.class), Element.NONE), 1, LevelHandler.INVASION, 8);
            }
        }

    }



    @Override
    public String templateLocation() {
        return "nether";
    }
}
