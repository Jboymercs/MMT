package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityPhaser;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelPhaser extends AnimatedGeoModel<EntityPhaser> {
    @Override
    public ResourceLocation getModelLocation(EntityPhaser entityPhaser) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/phaser/phaser.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPhaser entityPhaser) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/overworld/phaser.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityPhaser entityPhaser) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.phaser.json");
    }
}
