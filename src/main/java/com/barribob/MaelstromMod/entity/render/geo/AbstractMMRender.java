package com.barribob.MaelstromMod.entity.render.geo;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import software.bernie.geckolib3.core.util.Color;
import java.util.function.Function;

public abstract  class AbstractMMRender<T extends EntityLivingBase & IAnimatable> extends GeoLayerRenderer<T> {

    protected final Function<T, ResourceLocation> funcGetCurrentTexture;
    protected final Function<T, ResourceLocation> funcGetCurrentModel;

    protected GeoEntityRenderer<T> geoRendererInstance;

    public AbstractMMRender(GeoEntityRenderer<T> renderer, Function<T, ResourceLocation> funcGetCurrentTexture, Function<T, ResourceLocation> funcGetCurrentModel) {
        super(renderer);
        this.geoRendererInstance = renderer;
        this.funcGetCurrentTexture = funcGetCurrentTexture;
        this.funcGetCurrentModel = funcGetCurrentModel;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    protected void reRenderCurrentModelInRenderer(T entity, float partialTicks, Color renderColor) {
        this.entityRenderer.render(
                this.getEntityModel().getModel(this.funcGetCurrentModel.apply(entity)),
                entity,
                partialTicks,
                (float) renderColor.getRed() / 255f,
                (float) renderColor.getBlue() / 255f,
                (float) renderColor.getGreen() / 255f,
                (float) renderColor.getAlpha() / 255
        );
    }
}
