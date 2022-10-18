package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureBeetle;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHunter;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromHunter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderMaelstromHunter extends GeoEntityRenderer<EntityMaelstromHunter> {




    public RenderMaelstromHunter(RenderManager renderManager) {
        super(renderManager, new ModelMaelstromHunter());
    }


    @Override
    public void doRender(EntityMaelstromHunter entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
