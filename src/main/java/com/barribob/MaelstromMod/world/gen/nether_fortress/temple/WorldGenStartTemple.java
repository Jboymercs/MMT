package com.barribob.MaelstromMod.world.gen.nether_fortress.temple;

import com.barribob.MaelstromMod.world.gen.vanilla.WorldGenNetherBase;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenStartTemple extends WorldGenNetherBase {
    /**
     * @param name    The name of the structure to load in the nbt file
     * @param yOffset
     */
    public WorldGenStartTemple( int chunkX, int chunkZ) {
        super("nether/pillar_start", -32);
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation) {
        super.generateStructure(world, pos, Rotation.NONE);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("temple")) {
            BlockPos pos1 = pos.add(new BlockPos(-25, 0, -25));
            new GenerateNetherFortress(0).generate(world, world.rand, pos1);
        }
    }
}
