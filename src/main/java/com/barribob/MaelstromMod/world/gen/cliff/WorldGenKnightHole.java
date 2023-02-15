package com.barribob.MaelstromMod.world.gen.cliff;

import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromKnight;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

public class WorldGenKnightHole extends WorldGenCliffLedge{
    public WorldGenKnightHole(String name, int yOffset) {
        super("cliff/knight_hole", -4);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return super.generate(worldIn, rand, position.add(new BlockPos(0, yOffset, 0)));
    }

    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {
        if(function.startsWith("boss")) {
            worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(tileEntity instanceof TileEntityMobSpawner) {
                ((com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner) tileEntity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityMaelstromKnight.class), 1, LevelHandler.CLIFF_OVERWORLD, 10);
            }
        }
    }
}
