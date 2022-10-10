package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityPlayerDialouge;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelPlayerDialog extends AnimatedGeoModel<EntityPlayerDialouge> {
    @Override
    public ResourceLocation getModelLocation(EntityPlayerDialouge entityPlayerDialouge) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/player/player.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPlayerDialouge entityPlayerDialouge) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/playerbase.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityPlayerDialouge entityPlayerDialouge) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.playerbase.json");
    }
}
