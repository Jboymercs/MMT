package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureBeetle;
import com.barribob.MaelstromMod.entity.entities.EntityAzureWraith;
import com.barribob.MaelstromMod.entity.model.ModelAzureWraith;
import com.barribob.MaelstromMod.entity.render.geo.RenderAbstractGeoEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RenderAzureWraith extends RenderAbstractGeoEntity<EntityAzureWraith> {



    public RenderAzureWraith(RenderManager renderManager) {
        super(renderManager, new ModelAzureWraith());


        this.shadowSize = 0.6f;
    }



    @Override
    public void doRender(EntityAzureWraith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
