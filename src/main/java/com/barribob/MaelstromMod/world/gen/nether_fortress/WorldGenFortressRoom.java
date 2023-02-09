package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.entity.entities.EntityCliffGolem;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityAbberrant;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenFortressRoom extends WorldGenNetherBase {
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenFortressRoom(String name) {
        super("nether/nf_entry", 0);

    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
    if(function.startsWith("mob")) {
        if (random.nextFloat() > 0.3) {
            world.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().setData(ModEntities.getID(EntityAbberrant.class), 2, LevelHandler.INVASION, 16);
            }

        }
        else {
            world.setBlockToAir(pos);
        }
    }
    }
}
