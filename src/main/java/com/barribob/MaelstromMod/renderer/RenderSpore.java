package com.barribob.MaelstromMod.renderer;

import com.barribob.MaelstromMod.items.AnimatedSporeItem;
import com.barribob.MaelstromMod.model.ModelSpore;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderSpore extends GeoItemRenderer<AnimatedSporeItem> {
    public RenderSpore() {
        super(new ModelSpore());
    }
}
