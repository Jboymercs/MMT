package com.barribob.MaelstromMod.proxy;

import com.barribob.MaelstromMod.blocks.BlockLeavesBase;
import com.barribob.MaelstromMod.gui.Guis;
import com.barribob.MaelstromMod.util.handlers.RenderHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void setFancyGraphics(BlockLeavesBase block, boolean isFancy) {
        block.setFancyGraphics(isFancy);
    }

    @Override
    public void setCustomState(Block block, IStateMapper mapper) {
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    /**
     * Initializations for client only stuff like rendering
     */
    @Override
    public void init() {
        if (!Minecraft.getMinecraft().getFramebuffer().isStencilEnabled()) {
            Minecraft.getMinecraft().getFramebuffer().enableStencil();
        }
        //Registers Maelstrom Base Entities
        RenderHandler.registerEntityRenderers();

        //Registers Geckolib Entities
        RenderHandler.registerGeoEntityRenders();
        super.init();
    }


    @Override
    public void openGui(String id, Object... args) {
        Minecraft.getMinecraft().displayGuiScreen(Guis.getGui(id, args));
    }

    @Override
    public void closeGui(EntityPlayer p) {
        if(p.equals(Minecraft.getMinecraft().player))
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
    }
}
