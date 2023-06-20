package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureWraith;
import com.barribob.MaelstromMod.entity.model.ModelVoidBlossom;
import com.barribob.MaelstromMod.entity.model.ModelVoidSpike;
import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import com.barribob.MaelstromMod.entity.render.geo.RenderAbstractGeoEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RenderVoidSpike extends RenderAbstractGeoEntity<EntityVoidSpike> {

    public RenderVoidSpike(RenderManager renderManager) {
        super(renderManager, new ModelVoidSpike());
    }

    @Override
    public void doRender(EntityVoidSpike entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
