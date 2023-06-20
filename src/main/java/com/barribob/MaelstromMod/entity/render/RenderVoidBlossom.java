package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityVoidBlossom;
import com.barribob.MaelstromMod.entity.entities.EntityWraithHand;
import com.barribob.MaelstromMod.entity.model.ModelVoidBlossom;
import com.barribob.MaelstromMod.entity.model.ModelWraithHand;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderVoidBlossom extends GeoEntityRenderer<EntityVoidBlossom> {
    public RenderVoidBlossom(RenderManager renderManager) {
        super(renderManager, new ModelVoidBlossom());
    }
    @Override
    public void doRender(EntityVoidBlossom entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
