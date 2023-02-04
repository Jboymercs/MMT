package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityAbberrant;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelAbberrant extends AnimatedGeoModel<EntityAbberrant> {

    @Override
    public ResourceLocation getModelLocation(EntityAbberrant entityAbberrant) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/abberrant/abberrant.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAbberrant entityAbberrant) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/overworld/abberrant.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAbberrant entityAbberrant) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.abberrant.json");
    }
}
