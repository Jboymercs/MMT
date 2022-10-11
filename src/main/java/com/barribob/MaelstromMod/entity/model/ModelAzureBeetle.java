package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityAzureBeetle;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelAzureBeetle extends AnimatedGeoModel<EntityAzureBeetle> {
    @Override
    public ResourceLocation getModelLocation(EntityAzureBeetle entityAzureBeetle) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/beetle/azure_beetle.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAzureBeetle entity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/azure_beetle.png");


    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAzureBeetle entityAzureBeetle) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.azure_beetle.json");
    }
}
