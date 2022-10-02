package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityPlayerBase;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelPlayerBase extends AnimatedGeoModel<EntityPlayerBase> {
    @Override
    public ResourceLocation getModelLocation(EntityPlayerBase entityPlayerBase) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/player/player.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPlayerBase entity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/playerbase.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityPlayerBase entityPlayerBase) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.playerbase.json");
    }
}
