package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityBossSpawner;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockKnightStatue extends BlockDisappearingSpawner{

    public BlockKnightStatue(String name) {
        super(name, Material.ROCK);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBossSpawner();
    }

}
