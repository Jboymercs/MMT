package com.barribob.MaelstromMod.model;

import com.barribob.MaelstromMod.items.AnimatedSporeItem;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSpore extends AnimatedGeoModel<AnimatedSporeItem> {
    @Override
    public ResourceLocation getModelLocation(AnimatedSporeItem animatedSporeItem) {
        return new ResourceLocation(Reference.MOD_ID, "geo/item/spore.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedSporeItem animatedSporeItem) {
        return new ResourceLocation(Reference.MOD_ID, "textures/items/spore.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedSporeItem animatedSporeItem) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.spore.json");
    }
}
