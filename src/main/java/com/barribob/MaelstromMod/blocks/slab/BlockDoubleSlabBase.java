package com.barribob.MaelstromMod.blocks.slab;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDoubleSlabBase extends BlockSlabBase{

    public BlockDoubleSlabBase(String name, Material material, CreativeTabs tabs, BlockSlab half, float hardness, float resistance, SoundType soundType) {
        super(name, material, half);
        this.setCreativeTab(tabs);
        setResistance(resistance);
        setSoundType(soundType);
        setHardness(hardness);
    }
    @Override
    public boolean isDouble() {
        return true;
    }


}
