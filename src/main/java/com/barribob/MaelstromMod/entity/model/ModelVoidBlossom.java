package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityVoidBlossom;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelVoidBlossom extends AnimatedGeoModel<EntityVoidBlossom> {

    @Override
    public ResourceLocation getModelLocation(EntityVoidBlossom entityVoidBlossom) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/blossom/blossom.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityVoidBlossom entityVoidBlossom) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/blossom.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityVoidBlossom entityVoidBlossom) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.blossom.json");
    }

    @Override
    public void setLivingAnimations(EntityVoidBlossom entity, Integer uniqueID,  AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("StemY");
        IBone spikes = this.getAnimationProcessor().getBone("Thorns");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 90));
        spikes.setRotationY(0f);

    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}
