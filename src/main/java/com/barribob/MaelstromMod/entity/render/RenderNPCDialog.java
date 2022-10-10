package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityPlayerDialouge;
import com.barribob.MaelstromMod.entity.model.ModelPlayerBase;
import com.barribob.MaelstromMod.entity.model.ModelPlayerDialog;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderNPCDialog extends GeoEntityRenderer<EntityPlayerDialouge> {

    public RenderNPCDialog(RenderManager renderManager) {
        super(renderManager, new ModelPlayerDialog());
        this.shadowSize = 0.8f;

    }
}
