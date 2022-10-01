package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromNavigator;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromNavigator;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderMaelstromNavigator extends GeoEntityRenderer<EntityMaelstromNavigator> {

    public RenderMaelstromNavigator(RenderManager renderManager) {
        super(renderManager, new ModelMaelstromNavigator());
        this.shadowSize = 0.8f;
    }
}
