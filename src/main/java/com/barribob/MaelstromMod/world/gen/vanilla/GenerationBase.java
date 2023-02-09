package com.barribob.MaelstromMod.world.gen.vanilla;

import com.barribob.MaelstromMod.util.GenUtils;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GenerationBase {

    protected boolean fixedY = false;
    protected String name;
    protected int weight = 0;
    protected int chance = 0;
    protected boolean treatWaterAsAir;
    protected int yOffsetMin = 0;
    protected int yOffsetMax = 0;



    @Override
    public String toString() {
        return this.name;
    }



    public void generate(World worldIn, int chunkX, int chunkZ, Random random) {
        this.generate(worldIn, chunkX, chunkZ, random);
    }

    public int getYForPos(World world, int chunkX, int chunkZ, Random rand) {
        int y = 0;
        if(!this.fixedY) {

        }
        else {
            y = GenUtils.randomBetween(yOffsetMin, yOffsetMax, rand);
        }
        return y;
    }
}
