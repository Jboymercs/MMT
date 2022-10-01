package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromNavigator;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelMaelstromNavigator extends AnimatedGeoModel<EntityMaelstromNavigator> {
    @Override
    public ResourceLocation getModelLocation(EntityMaelstromNavigator entityMaelstromNavigator) {
        return new ResourceLocation(Reference.MOD_ID , "geo/entity/navigator/navigator.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMaelstromNavigator entity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/navigator.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMaelstromNavigator entityMaelstromNavigator) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.navigator.json");
    }
}
