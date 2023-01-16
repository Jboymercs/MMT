package com.barribob.MaelstromMod.items.tools;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemNetherSword extends ToolSword{




    public ItemNetherSword(String name, ToolMaterial material, float level) {
        super(name, material, level);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLivingBase, ItemStack itemStack) {
        if(!entityLivingBase.world.isRemote && entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;

        }

        return false;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> toolTip, ITooltipFlag flagIn) {
        super.addInformation(stack, world, toolTip, flagIn);
        toolTip.add(TextFormatting.RED + ModUtils.translateDesc("apostle_sword"));
    }
}
