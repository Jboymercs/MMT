package com.barribob.MaelstromMod.entity.model;// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.barribob.MaelstromMod.entity.entities.EntityKnightCrystal;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelKnightCrystal extends AnimatedGeoModel<EntityKnightCrystal> {

	@Override
	public ResourceLocation getModelLocation(EntityKnightCrystal entityKnightCrystal) {
		return new ResourceLocation(Reference.MOD_ID, "geo/entity/crystal/crystal.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntityKnightCrystal entityKnightCrystal) {
		return new ResourceLocation(Reference.MOD_ID, "textures/entity/knight_crystal.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EntityKnightCrystal entityKnightCrystal) {
		return new ResourceLocation(Reference.MOD_ID, "animations/animation.crystal_knight.json");
	}
}