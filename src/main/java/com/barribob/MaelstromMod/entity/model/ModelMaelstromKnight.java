package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromKnight;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelMaelstromKnight extends AnimatedGeoModel<EntityMaelstromKnight> {

    public String[] TEXTURES;
    @Override
    public ResourceLocation getModelLocation(EntityMaelstromKnight entityMaelstromKnight) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/knight/maelstromknight.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMaelstromKnight entity) {
        if (entity.getElement().id == 0) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/maelstromknight_azure.png");
        }
        if (entity.getElement().id == 1) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/maelstromknight_golden.png");
        }
        if (entity.getElement().id == 2) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/maelstromknight_crimson.png");
        }


        return new ResourceLocation(Reference.MOD_ID, "textures/entity/maelstromknight.png");
    }


    @Override
    public ResourceLocation getAnimationFileLocation(EntityMaelstromKnight entityMaelstromKnight) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.maelstromknight.json");
    }
}
