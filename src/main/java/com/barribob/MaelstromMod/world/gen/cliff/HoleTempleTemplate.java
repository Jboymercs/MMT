package com.barribob.MaelstromMod.world.gen.cliff;

import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

/**
 * The specific template used for generating the maelstrom fortress
 */
public class HoleTempleTemplate extends ModStructureTemplate {
    public HoleTempleTemplate() {
    }

    public HoleTempleTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rotation, int distance, boolean overwriteIn) {
        super(manager, type, pos, distance, rotation, overwriteIn);
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {
        worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityBeast.class), 1, LevelHandler.CLIFF_OVERWORLD, 20);
        }
    }

    @Override
    public String templateLocation() {
        return "cliff";
    }
}
