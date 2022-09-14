// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports
package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Maelstrom Cauldron - GDrayn
 * Created using Blockbench
 */

public class ModelHorror extends AnimatedGeoModel<EntityHorror> {


	@Override
	public ResourceLocation getModelLocation(EntityHorror o) {
		return new ResourceLocation(Reference.MOD_ID, "geo/entity/horror/horror.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntityHorror object) {
		return new ResourceLocation(Reference.MOD_ID, "textures/entity/horror.png");
	}



	@Override
	public ResourceLocation getAnimationFileLocation(EntityHorror object) {
		return new ResourceLocation(Reference.MOD_ID, "animations/animation.horror.json");
	}
}
