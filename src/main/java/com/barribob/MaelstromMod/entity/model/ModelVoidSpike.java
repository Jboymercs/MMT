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
        if(entityVoidSpike.ticksExisted > 1 && entityVoidSpike.ticksExisted < 4) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike.png");
        }
        if(entityVoidSpike.ticksExisted > 4 && entityVoidSpike.ticksExisted < 8) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike2.png");
        }
        if(entityVoidSpike.ticksExisted > 8 && entityVoidSpike.ticksExisted < 12) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike3.png");
        }

        if(entityVoidSpike.ticksExisted > 12 && entityVoidSpike.ticksExisted < 16) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike4.png");
        }

        if(entityVoidSpike.ticksExisted > 16 && entityVoidSpike.ticksExisted < 20) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike5.png");
        }
        if(entityVoidSpike.ticksExisted > 20 && entityVoidSpike.ticksExisted < 25) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike6.png");
        }
        if(entityVoidSpike.ticksExisted > 25 && entityVoidSpike.ticksExisted < 28) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike7.png");
        }
        if(entityVoidSpike.ticksExisted > 28 && entityVoidSpike.ticksExisted < 32) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike8.png");
        }
        if(entityVoidSpike.ticksExisted > 32) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike9.png");
        }
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/spike/spike9.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityVoidSpike entityVoidSpike) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.spike.json");
    }
}
