package com.barribob.MaelstromMod.blocks.portal;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.teleporter.DimensionalTeleporter;
import com.barribob.MaelstromMod.util.teleporter.ToNexusTeleporter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

import java.util.List;

/**
 * The portal block for the azure dimension
 */
public class BlockAzurePortal extends BlockPortal {
    public BlockAzurePortal(String name) {
        super(name, ModConfig.world.fracture_dimension_id, ModConfig.world.nexus_dimension_id);
        this.setBlockUnbreakable();
        this.setLightLevel(0.5f);
        this.setLightOpacity(0);
    }

    @Override
    protected Teleporter getEntranceTeleporter(World world) {
        return new DimensionalTeleporter(world.getMinecraftServer().getWorld(ModConfig.world.fracture_dimension_id), ModBlocks.LIGHT_AZURE_STONE, ModBlocks.AZURE_PORTAL);
    }

    @Override
    protected Teleporter getExitTeleporter(World world) {
        return new ToNexusTeleporter(world.getMinecraftServer().getWorld(ModConfig.world.nexus_dimension_id), new BlockPos(113, 129, 161));
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(ModUtils.translateDesc("nexus_only_portal"));
        super.addInformation(stack, player, tooltip, advanced);
    }
}