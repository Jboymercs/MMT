package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityPlayerBase;
import com.barribob.MaelstromMod.entity.model.ModelPlayerBase;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderPlayerBase extends GeoEntityRenderer<EntityPlayerBase> {

    public RenderPlayerBase(RenderManager renderManager) {
        super(renderManager, new ModelPlayerBase());
        this.shadowSize = 0.8f;
    }
}
