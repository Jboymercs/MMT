package com.barribob.MaelstromMod.util;

import com.barribob.MaelstromMod.config.ModConfig;
import net.minecraft.world.World;

public class SpawnUtil {

    public static boolean isDay(World worldIn) {
        return worldIn.getWorldTime() <= 12000;
    }


}
