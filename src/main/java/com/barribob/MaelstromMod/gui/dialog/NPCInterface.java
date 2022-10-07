package com.barribob.MaelstromMod.gui.dialog;

import com.barribob.MaelstromMod.entity.entities.EntityPlayerDialouge;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public abstract class NPCInterface extends GuiScreen {
    public EntityPlayerSP player;
    public boolean drawBackground;
    public String title;
    public ResourceLocation background;
    public boolean close;
    public int Xsize;
    public int Ysize;
    public int mouseX;
    public int mouseY;
    private GuiButton buttonPressed;
    public EntityPlayerDialouge npc;
    public float bgScale;

    public void setBackground(String texture) {
        this.background = new ResourceLocation(Reference.MOD_ID, "textures/gui" + texture);
    }

    public NPCInterface(EntityPlayerDialouge npc) {
        this.Xsize = 960;
        this.Ysize = 540;
        this.npc = npc;
        this.drawBackground = false;
        this.close = true;
        this.background = null;
        this.bgScale = 1.0F;

    }

}
