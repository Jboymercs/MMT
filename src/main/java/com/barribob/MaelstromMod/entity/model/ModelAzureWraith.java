package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityAzureWraith;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelAzureWraith extends AnimatedGeoModel<EntityAzureWraith> {

    @Override
    public ResourceLocation getModelLocation(EntityAzureWraith entityAzureWraith) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/azure_wraith/azure_wraith.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAzureWraith entityAzureWraith) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/azure_wraith.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAzureWraith entityAzureWraith) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.azure_wraith.json");
    }
}
