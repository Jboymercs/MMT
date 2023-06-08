package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/**
 * CrystalGolem - Barribob Created using Tabula 7.0.0
 */
public class ModelAzureGolem extends AnimatedGeoModel<EntityAzureGolem> {

    @Override
    public ResourceLocation getModelLocation(EntityAzureGolem o) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/azure_golem/azure_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAzureGolem object) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/azure_golem.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAzureGolem object) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.azure_golem.json");
    }
    @Override
    public void setLivingAnimations(EntityAzureGolem entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);


    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}
