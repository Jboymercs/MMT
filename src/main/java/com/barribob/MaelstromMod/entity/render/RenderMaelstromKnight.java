package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromKnight;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromKnight;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderMaelstromKnight extends GeoEntityRenderer<EntityMaelstromKnight> {
    public String[] TEXTURES;

    public RenderMaelstromKnight(RenderManager renderManager) {
        super(renderManager, new ModelMaelstromKnight());
        this.shadowSize = 1.0F;
    }
}
