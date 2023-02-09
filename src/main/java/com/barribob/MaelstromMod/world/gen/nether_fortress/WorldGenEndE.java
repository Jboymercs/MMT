package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenEndE extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    //End points, only found on towers
    public WorldGenEndE(int yOffset) {
        super("nether/east_end", 0);
    }
    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }
}
