package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureBeetle;
import com.barribob.MaelstromMod.entity.model.ModelAzureBeetle;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderAzureBeetle extends GeoEntityRenderer<EntityAzureBeetle> {


    public RenderAzureBeetle(RenderManager renderManager) {
        super(renderManager, new ModelAzureBeetle());
        this.shadowSize = 0.4F;
    }
}
