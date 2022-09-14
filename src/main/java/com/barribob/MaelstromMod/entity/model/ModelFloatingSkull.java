// Made with Blockbench 4.3.0
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports
package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.util.IPitch;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFloatingSkull extends ModelBBAnimated {
	private final ModelRenderer Main;
	private final ModelRenderer eye_r_r1;
	private final ModelRenderer eye_l_r1;
	private final ModelRenderer talisman;
	private final ModelRenderer talisman_r1;
	private final ModelRenderer jaw;
	private final ModelRenderer mandible_r_r1;
	/**
	 * Floating Skull - GDrayn
	 * Created using Blockbench
	 */
	public ModelFloatingSkull() {
		textureWidth = 64;
		textureHeight = 64;

		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, 16.0F, 0.0F);
		Main.cubeList.add(new ModelBox(Main, 7, 0, -3.0F, -4.0F, -5.0F, 6, 2, 1, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 5, 3, -4.0F, -2.0F, -5.0F, 8, 1, 1, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 2, 6, -5.0F, -1.0F, -5.0F, 1, 2, 2, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 20, 6, 4.0F, -1.0F, -5.0F, 1, 2, 2, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 6, 5, -3.0F, -1.0F, -5.0F, 6, 1, 2, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 10, 8, -1.0F, 0.0F, -5.0F, 2, 1, 2, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 0, 10, -4.0F, 1.0F, -5.0F, 3, 1, 4, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 14, 10, 1.0F, 1.0F, -5.0F, 3, 1, 4, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 4, 15, -3.0F, 2.0F, -5.0F, 6, 2, 4, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 34, 0, -3.0F, -5.0F, -4.0F, 6, 1, 8, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 32, 9, -4.0F, -4.0F, -4.0F, 8, 3, 8, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 33, 20, -4.0F, -1.0F, -3.0F, 8, 2, 7, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 37, 29, -3.0F, 1.0F, -1.0F, 6, 1, 5, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 41, 37, -3.0F, -4.0F, 4.0F, 6, 5, 1, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 13, 22, -0.5F, 3.75F, -4.75F, 1, 1, 0, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 11, 22, -2.5F, 3.75F, -4.75F, 1, 1, 0, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 9, 21, -2.5F, 3.75F, -4.75F, 0, 1, 1, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 7, 21, -2.5F, 3.75F, -2.75F, 0, 1, 1, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 15, 22, 1.5F, 3.75F, -4.75F, 1, 1, 0, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 17, 21, 2.5F, 3.75F, -4.75F, 0, 1, 1, 0.0F, false));
		Main.cubeList.add(new ModelBox(Main, 19, 21, 2.5F, 3.75F, -2.75F, 0, 1, 1, 0.0F, false));

		eye_r_r1 = new ModelRenderer(this);
		eye_r_r1.setRotationPoint(2.5F, 0.5F, -3.5F);
		Main.addChild(eye_r_r1);
		setRotationAngle(eye_r_r1, 0.0F, 0.0F, 0.0F);
		eye_r_r1.cubeList.add(new ModelBox(eye_r_r1, 15, 41, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		eye_l_r1 = new ModelRenderer(this);
		eye_l_r1.setRotationPoint(-2.5F, 0.5F, -3.5F);
		Main.addChild(eye_l_r1);
		setRotationAngle(eye_l_r1, 0.0F, 0.0F, 0.0F);
		eye_l_r1.cubeList.add(new ModelBox(eye_l_r1, 9, 41, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		talisman = new ModelRenderer(this);
		talisman.setRotationPoint(0.0F, -4.0F, -5.5F);
		Main.addChild(talisman);
		

		talisman_r1 = new ModelRenderer(this);
		talisman_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		talisman.addChild(talisman_r1);
		setRotationAngle(talisman_r1, 0.0F, 0.0F, 0.7854F);
		talisman_r1.cubeList.add(new ModelBox(talisman_r1, 11, 38, -1.0F, -1.0F, -0.5F, 2, 2, 1, 0.0F, false));

		jaw = new ModelRenderer(this);
		jaw.setRotationPoint(0.0F, 4.0F, -1.0F);
		Main.addChild(jaw);
		jaw.cubeList.add(new ModelBox(jaw, 12, 35, -1.5F, 1.25F, -3.5F, 1, 1, 0, 0.0F, false));
		jaw.cubeList.add(new ModelBox(jaw, 10, 34, -1.5F, 1.25F, -3.5F, 0, 1, 1, 0.0F, false));
		jaw.cubeList.add(new ModelBox(jaw, 8, 34, -1.5F, 1.25F, -1.5F, 0, 1, 1, 0.0F, false));
		jaw.cubeList.add(new ModelBox(jaw, 14, 35, 0.5F, 1.25F, -3.5F, 1, 1, 0, 0.0F, false));
		jaw.cubeList.add(new ModelBox(jaw, 16, 34, 1.5F, 1.25F, -3.5F, 0, 1, 1, 0.0F, false));
		jaw.cubeList.add(new ModelBox(jaw, 18, 34, 1.5F, 1.25F, -1.5F, 0, 1, 1, 0.0F, false));

		mandible_r_r1 = new ModelRenderer(this);
		mandible_r_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		jaw.addChild(mandible_r_r1);
		setRotationAngle(mandible_r_r1, 0.0F, 0.0F, 0.0F);
		mandible_r_r1.cubeList.add(new ModelBox(mandible_r_r1, 18, 27, 1.0F, -2.0F, 0.0F, 1, 5, 1, 0.0F, false));
		mandible_r_r1.cubeList.add(new ModelBox(mandible_r_r1, 6, 27, -2.0F, -2.0F, 0.0F, 1, 5, 1, 0.0F, false));
		mandible_r_r1.cubeList.add(new ModelBox(mandible_r_r1, 6, 29, -2.0F, 2.0F, -4.0F, 4, 1, 4, 0.0F, false));
	}



	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}