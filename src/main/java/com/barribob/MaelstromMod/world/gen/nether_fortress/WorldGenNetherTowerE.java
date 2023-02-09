package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenNetherTowerE extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenNetherTowerE(int yOffset) {
        super("nether/east_tower", 0);
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }
}
