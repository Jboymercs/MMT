package com.barribob.MaelstromMod.world.gen.overworld;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityNetherKnight;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityShadeKnight;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

public class WorldGenShadeRuins extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenShadeRuins(int chunkX, int chunkZ) {
        super("overworld/shade_spawn", -4);
    }
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("mob")) {
            world.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
            TileEntity tile = world.getTileEntity(pos);

            if(tile instanceof TileEntityMobSpawner) {
                // Spawns the Shade
                ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().setData(new MobSpawnerLogic.MobSpawnData(ModEntities.getID(EntityShadeKnight.class), Element.NONE), 1, LevelHandler.INVASION, 16);
            }
        }
    }
}
