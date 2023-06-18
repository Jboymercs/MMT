package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityDhav;
import com.barribob.MaelstromMod.entity.model.ModelDhav;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderDhav extends GeoEntityRenderer<EntityDhav> {

    public RenderDhav(RenderManager renderManager) {
        super(renderManager, new ModelDhav());
        this.shadowSize = 0.8f;
    }
}
