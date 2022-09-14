package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.model.ModelHorror;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderHorror extends GeoEntityRenderer<EntityHorror>

        /**
         * Texture, Model, and Animations done by GDrayn
         */

{
    public RenderHorror(RenderManager renderManager) {
        super(renderManager, new ModelHorror());
        this.shadowSize = 1.0F;
    }
}
