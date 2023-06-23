package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.projectile.EmtityGenericWave;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelGenericWave extends AnimatedGeoModel<EmtityGenericWave> {
    @Override
    public ResourceLocation getModelLocation(EmtityGenericWave emtityGenericWave) {
        return new ResourceLocation(Reference.MOD_ID, "geo/entity/wave/wave.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EmtityGenericWave emtityGenericWave) {
        if(emtityGenericWave.ticksExisted > 1 && emtityGenericWave.ticksExisted < 4) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave1.png");
        }
        if(emtityGenericWave.ticksExisted > 4 && emtityGenericWave.ticksExisted < 8) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave2.png");
        }
        if(emtityGenericWave.ticksExisted > 8 && emtityGenericWave.ticksExisted < 12) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave3.png");
        }

        if(emtityGenericWave.ticksExisted > 12 && emtityGenericWave.ticksExisted < 16) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave4.png");
        }

        if(emtityGenericWave.ticksExisted > 16 && emtityGenericWave.ticksExisted < 20) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave5.png");
        }
        if(emtityGenericWave.ticksExisted > 20 && emtityGenericWave.ticksExisted < 25) {
            return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave2.png");
        }
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/wave/wave.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EmtityGenericWave emtityGenericWave) {
        return new ResourceLocation(Reference.MOD_ID, "animations/animation.wave.json");
    }
}
