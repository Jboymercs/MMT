package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityFireRing;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelFireRing extends AnimatedGeoModel<EntityFireRing> {

    @Override
    public ResourceLocation getModelLocation(EntityFireRing entityFireRing) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/fire_ring/fire_ring.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFireRing entityFireRing) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/overworld/fire_ring.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityFireRing entityFireRing) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.fire_ring.json");
    }


}
