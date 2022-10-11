package com.barribob.MaelstromMod.entity.render.geo;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.render.LayerGenericGlow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.function.Function;

public class RenderAbstractGeoEntity<T extends EntityLeveledMob & IAnimatable> extends GeoEntityRenderer<T> {
    public final Function<T, ResourceLocation> TEXTURE_LOCATION;
    public final Function<T, ResourceLocation> MODEL_LOCATION;


    public RenderAbstractGeoEntity(RenderManager renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);

        this.MODEL_LOCATION = modelProvider::getModelLocation;
        this.TEXTURE_LOCATION = modelProvider::getTextureLocation;

        this.addLayer(new LayerGenericGlow<T>(this, this.TEXTURE_LOCATION, this.MODEL_LOCATION));

    }


    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.TEXTURE_LOCATION.apply(entity);
    }



}
