package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityDhav;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelDhav extends AnimatedGeoModel<EntityDhav> {

    @Override
    public ResourceLocation getModelLocation(EntityDhav entityDhav) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/dhvara/dhvara.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityDhav entityDhav) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/dhvara.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityDhav entityDhav) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.dhvara.json");
    }


    @Override
    public void setLivingAnimations(EntityDhav entity, Integer uniqueID) {
        super.setLivingAnimations(entity, uniqueID);
    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}
