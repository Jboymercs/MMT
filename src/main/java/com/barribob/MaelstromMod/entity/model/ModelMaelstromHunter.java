package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHunter;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelMaelstromHunter extends AnimatedGeoModel<EntityMaelstromHunter> {
    @Override
    public ResourceLocation getModelLocation(EntityMaelstromHunter entityMaelstromHunter) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/hunter/hunter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMaelstromHunter entityMaelstromHunter) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/hunter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMaelstromHunter entityMaelstromHunter) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.hunter.json");
    }



}
