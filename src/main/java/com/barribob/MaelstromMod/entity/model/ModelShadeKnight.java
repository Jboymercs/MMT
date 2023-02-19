package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityShadeKnight;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelShadeKnight extends AnimatedGeoModel<EntityShadeKnight> {

    @Override
    public ResourceLocation getModelLocation(EntityShadeKnight entityShadeKnight) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/rot/shademo.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShadeKnight entityShadeKnight) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/overworld/rot.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShadeKnight entityShadeKnight) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.rotshade.json");
    }
}
