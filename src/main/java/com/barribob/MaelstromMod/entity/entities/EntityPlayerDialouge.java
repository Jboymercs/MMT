package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.world.IWorldHoverObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityPlayerDialouge extends EntityPlayerBase implements IWorldHoverObject {
    /**
     * Specific Base to handle Dialog Npcs
     *
     */
    public EntityPlayerDialouge(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }
    @Override
    public boolean canBeLeashedTo(EntityPlayer entityPlayer) {
        return false;
    }
    @Override
    public void onUpdate() {


        super.onUpdate();
    }
    @Override
    public ITextComponent getHoverText(World world, RayTraceResult rayTraceResult) {
        return new TextComponentTranslation("gui.mm.hover.npc", this.getName());



    }

}
