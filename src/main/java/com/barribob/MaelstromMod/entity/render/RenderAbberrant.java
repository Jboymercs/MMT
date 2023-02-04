package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityAbberrant;
import com.barribob.MaelstromMod.entity.entities.overworld.EntityNetherKnight;
import com.barribob.MaelstromMod.entity.model.ModelAbberrant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderAbberrant extends GeoEntityRenderer<EntityAbberrant> {


    public RenderAbberrant(RenderManager renderManager) {
        super(renderManager, new ModelAbberrant());
    }

    @Override
    public void doRender(EntityAbberrant entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }

}
