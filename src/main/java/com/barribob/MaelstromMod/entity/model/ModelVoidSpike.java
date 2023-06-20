package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelVoidSpike extends AnimatedGeoModel<EntityVoidSpike> {

    @Override
    public ResourceLocation getModelLocation(EntityVoidSpike entityVoidSpike) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/spike/spike.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityVoidSpike entityVoidSpike) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityVoidSpike entityVoidSpike) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.spike.json");
    }
}
