package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHunter;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromKnight;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromKnight;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderMaelstromKnight extends GeoEntityRenderer<EntityMaelstromKnight> {
    public String[] TEXTURES;

    public RenderMaelstromKnight(RenderManager renderManager) {
        super(renderManager, new ModelMaelstromKnight());
        this.shadowSize = 1.0F;
    }

    @Override
    public void doRender(EntityMaelstromKnight entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
