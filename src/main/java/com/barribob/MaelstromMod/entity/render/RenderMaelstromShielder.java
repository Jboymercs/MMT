package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromShielder;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromShielder;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderMaelstromShielder extends GeoEntityRenderer<EntityMaelstromShielder> {

    public RenderMaelstromShielder(RenderManager renderManager) {
        super(renderManager, new ModelMaelstromShielder());
        this.shadowSize = 0.8f;
    }
}
