package com.barribob.MaelstromMod.entity.model;// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKnightSlash extends ModelBase {
	private final ModelRenderer bone;


	public ModelKnightSlash() {
		textureWidth = 16;
		textureHeight = 16;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone.addChild(bone);
		setRotationAngle(bone, 0.0F, 3.1416F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, -16, 0, -8.0F, 0.0F, -8.0F, 16, 0, 16, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}