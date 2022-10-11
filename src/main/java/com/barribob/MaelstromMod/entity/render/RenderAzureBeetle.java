package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureBeetle;
import com.barribob.MaelstromMod.entity.model.ModelAzureBeetle;
import com.barribob.MaelstromMod.entity.render.geo.RenderAbstractGeoEntity;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderAzureBeetle extends RenderAbstractGeoEntity<EntityAzureBeetle> {


        public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/azure_beetle_bulb.png");
        public static final ResourceLocation MODEL = new ResourceLocation(Reference.MOD_ID, "geo/entity/beetle/azure_beetle.geo.json");

    public RenderAzureBeetle(RenderManager renderManager) {
        super(renderManager, new ModelAzureBeetle());
        this.addLayer(new LayerGenericGlow<EntityAzureBeetle>(this, this.TEXTURE_LOCATION, this.MODEL_LOCATION));




        this.shadowSize = 0.4F;
    }
    @Override
    public void doRender(EntityAzureBeetle entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }




}
