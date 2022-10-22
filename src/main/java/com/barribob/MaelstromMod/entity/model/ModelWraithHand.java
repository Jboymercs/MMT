package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityWraithHand;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelWraithHand extends AnimatedGeoModel<EntityWraithHand> {
    @Override
    public ResourceLocation getModelLocation(EntityWraithHand entityWraithHand) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/azure_wraith/wraith_hand.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWraithHand entityWraithHand) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/wraithHand.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWraithHand entityWraithHand) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.wraithHand.json");
    }
}
