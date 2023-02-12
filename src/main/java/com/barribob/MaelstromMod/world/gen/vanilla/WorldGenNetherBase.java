package com.barribob.MaelstromMod.world.gen.vanilla;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

public class WorldGenNetherBase extends WorldGenStructure {

    StructureBoundingBox boundingBox;

    int yOffset;
    /**
     * @param name The name of the structure to load in the nbt file
     */
    public WorldGenNetherBase(String name, int yOffset) {
        super(name);
        this.yOffset = yOffset;
    }

    @Override
    public boolean generate(World worldIn, Random random, BlockPos blockPos) {
        return super.generate(worldIn, random, blockPos.add(new BlockPos(0, yOffset, 0)));
    }


    @Override
    public int getYGenHeight(World world, int x, int z) {
        return ModUtils.calculateGenerationHeight(world, x, z);
    }

    protected void updateBoundingBox() {
        this.boundingBox = WorldGenNetherBase.settings.getBoundingBox();

    }
}
