package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockSlabBase extends BlockSlab implements IHasModel {

    protected BlockSlabBase(Material material) {
        super(material);
    }
    private static final int HALF_META_BIT = 8;
    public static final PropertyBool VARIANT_PROPERTY = PropertyBool.create("variants");

    public BlockSlabBase(String name, Material material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

    }

    public BlockSlabBase(String name, Material material, float hardness, float resistence, SoundType soundType) {
        this(name, material);
        setHardness(hardness);
        setResistance(resistence);
        setSoundType(soundType);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return this.getUnlocalizedName();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT_PROPERTY;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return false;
    }


    @Override
    public int getMetaFromState(IBlockState state) {
        return this.isDouble() ? 0 : state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
    }
    @Override
    protected final BlockStateContainer createBlockState() {
        if (isDouble())
            return new BlockStateContainer.Builder(this).add(VARIANT_PROPERTY).build();
        else
            return new BlockStateContainer.Builder(this).add(VARIANT_PROPERTY, HALF).build();
    }

    @Override
    public final IBlockState getStateFromMeta(final int meta) {
        IBlockState blockState = getDefaultState().withProperty(VARIANT_PROPERTY, false);
        if (!isDouble()) {
            EnumBlockHalf value = EnumBlockHalf.BOTTOM;
            if ((meta & HALF_META_BIT) != 0)
                value = EnumBlockHalf.TOP;

            blockState = blockState.withProperty(HALF, value);
        }

        return blockState;
    }


}
