package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityAzureWraith;
import com.barribob.MaelstromMod.entity.entities.EntityWraithHand;
import com.barribob.MaelstromMod.entity.model.ModelWraithHand;
import com.barribob.MaelstromMod.entity.render.geo.RenderAbstractGeoEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RenderWraithHand extends RenderAbstractGeoEntity<EntityWraithHand> {



    public RenderWraithHand(RenderManager renderManager) {
        super(renderManager, new ModelWraithHand());
    }


}
