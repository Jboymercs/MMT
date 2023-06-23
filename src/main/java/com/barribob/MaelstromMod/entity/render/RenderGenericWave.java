package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.model.ModelGenericWave;
import com.barribob.MaelstromMod.entity.projectile.EmtityGenericWave;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderGenericWave extends GeoEntityRenderer<EmtityGenericWave> {
    public RenderGenericWave(RenderManager renderManager) {

        super(renderManager, new ModelGenericWave());
    }
}
