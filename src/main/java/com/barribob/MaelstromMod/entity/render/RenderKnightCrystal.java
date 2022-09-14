package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityChaosKnight;
import com.barribob.MaelstromMod.entity.entities.EntityKnightCrystal;
import com.barribob.MaelstromMod.entity.model.ModelKnightCrystal;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.RenderUtils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderKnightCrystal extends GeoEntityRenderer<EntityKnightCrystal> {


    public RenderKnightCrystal(RenderManager renderManager) {
        super(renderManager, new ModelKnightCrystal());
        this.shadowSize = 0.0F;
    }
}
