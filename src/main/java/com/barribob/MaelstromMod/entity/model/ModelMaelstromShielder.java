package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromShielder;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelMaelstromShielder extends AnimatedGeoModel<EntityMaelstromShielder> {
    @Override
    public ResourceLocation getModelLocation(EntityMaelstromShielder entityMaelstromShielder) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/shielder/shielder.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMaelstromShielder entity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/shielder.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMaelstromShielder entityMaelstromShielder) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.shielder.json");
    }
}
