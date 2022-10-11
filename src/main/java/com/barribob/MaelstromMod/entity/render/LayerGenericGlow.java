package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureBeetle;
import com.barribob.MaelstromMod.entity.model.ModelAzureBeetle;
import com.barribob.MaelstromMod.entity.render.geo.AbstractMMRender;
import com.barribob.MaelstromMod.entity.util.EmissiveLighting;
import com.barribob.MaelstromMod.util.Reference;
import net.java.games.input.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.function.Function;

public class LayerGenericGlow<T extends EntityLiving & IAnimatable> extends AbstractMMRender<T> {


    public LayerGenericGlow(GeoEntityRenderer<T> entityRendererIn, Function<T, ResourceLocation> resouceLocation, Function<T, ResourceLocation> modelLocation) {
        super(entityRendererIn, resouceLocation, modelLocation);
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }


    @Override
    public void render(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, Color renderColor) {
        EmissiveLighting.preEmissiveTextureRendering();

        this.geoRendererInstance.bindTexture((this.funcGetCurrentTexture.apply(entitylivingbaseIn)));

        this.reRenderCurrentModelInRenderer(entitylivingbaseIn, partialTicks, renderColor);

        EmissiveLighting.postEmissiveTextureRendering();
    }
}
