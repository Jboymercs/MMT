package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.overworld.EntityNetherKnight;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelNetherKnight extends AnimatedGeoModel<EntityNetherKnight> {

    @Override
    public ResourceLocation getModelLocation(EntityNetherKnight entityNetherKnight) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/nether_knight/nether_knight.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityNetherKnight entityNetherKnight) {

        if(entityNetherKnight.hitOpener) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/overworld/nether_knight.png");
        } else  {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/overworld/nether_knight_1.png");
        }



    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityNetherKnight entityNetherKnight) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.nether_knight.json");
    }
}
