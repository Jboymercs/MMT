package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityHunterMissile;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelHunterMissile extends AnimatedGeoModel<EntityHunterMissile> {

    @Override
    public ResourceLocation getModelLocation(EntityHunterMissile entityHunterMissile) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/hunter/hunter_missile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityHunterMissile entityHunterMissile) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/hunter_missile.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityHunterMissile entityHunterMissile) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.missile.json");
    }
}
