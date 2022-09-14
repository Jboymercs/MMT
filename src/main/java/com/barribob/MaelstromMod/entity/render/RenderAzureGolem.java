package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.model.ModelAzureGolem;
import com.barribob.MaelstromMod.entity.model.ModelCliffGolem;
import com.barribob.MaelstromMod.entity.model.ModelHorror;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderAzureGolem extends GeoEntityRenderer<EntityAzureGolem> {


    /**
     * Model, Texture, and Animations done by GDrayn
     */
    public RenderAzureGolem(RenderManager renderManager) {
        super(renderManager, new ModelAzureGolem());
        this.shadowSize = 1.0F;
    }

}
