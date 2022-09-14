package com.barribob.MaelstromMod.world.gen.maelstrom_stronghold;

import com.barribob.MaelstromMod.entity.entities.EntityFloatingSkull;
import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.GenUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;
import com.barribob.MaelstromMod.world.gen.WorldGenMaelstrom;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

/**
 * The specific template used for generating the maelstrom fortress
 */
public class StrongholdTemplate extends ModStructureTemplate {
    public StrongholdTemplate() {
    }

    public StrongholdTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rotation, int distance, boolean overwriteIn) {
        super(manager, type, pos, distance, rotation, overwriteIn);
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {
        if (function.startsWith("chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockpos = pos.down();

            if (sbb.isVecInside(blockpos)) {
                TileEntity tileentity = worldIn.getTileEntity(blockpos);

                if (tileentity instanceof TileEntityChest) {
                    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.AZURE_FORTRESS, rand.nextLong());
                }
            }
        } else if (function.startsWith("void5x5")) {
            GenUtils.digBlockToVoid(15, pos.down(), worldIn);
            worldIn.setBlockToAir(pos);
        } else if (function.startsWith("void7x7")) {
            GenUtils.digBlockToVoid(40, pos.down(), worldIn);
            worldIn.setBlockToAir(pos);
        } else if (function.startsWith("maelstrom")) {
            if (rand.nextInt(3) == 0) {
                new WorldGenMaelstrom(ModBlocks.AZURE_MAELSTROM, ModBlocks.AZURE_MAELSTROM, null).generate(worldIn, rand, pos);
            }
            worldIn.setBlockToAir(pos);
        } else if (function.startsWith("random_chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockpos = pos.down();

            if (worldIn.rand.nextFloat() < 0.25f) {
                if (sbb.isVecInside(blockpos)) {
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);

                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest) tileentity).setLootTable(LootTableHandler.AZURE_FORTRESS, rand.nextLong());
                    }
                }
            } else {
                worldIn.setBlockToAir(blockpos);
            }
        } else if (function.startsWith("final_chest")) {
            worldIn.setBlockToAir(pos);
            BlockPos blockpos = pos.down();

            if (sbb.isVecInside(blockpos)) {
                TileEntity tileentity = worldIn.getTileEntity(blockpos);

                if (tileentity instanceof TileEntityChest) {
                    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.STRONGHOLD_KEY_CHEST, rand.nextLong());
                }
            }
        } else if (function.startsWith("enemy")) {
            worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
                        new MobSpawnData[]{
                                new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[]{Element.AZURE, Element.NONE}, new int[]{1, 4}, 1),
                                new MobSpawnData(ModEntities.getID(EntityHorror.class), Element.NONE),
                                new MobSpawnData(ModEntities.getID(EntityFloatingSkull.class), Element.NONE),
                        },
                        new int[]{1, 1, 1},
                        3,
                        LevelHandler.AZURE_ENDGAME,
                        16);
            }
        } else if (function.startsWith("boss")) {
            worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityMaelstromBeast.class), 1, LevelHandler.AZURE_ENDGAME, 16);
            }
        }
    }

    @Override
    public String templateLocation() {
        return "maelstrom_stronghold";
    }
}
