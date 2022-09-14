package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.init.MMAnimations;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;


public class EntityKnightCrystal extends EntityMaelstromMob implements IAnimatable {
    public EntityKnightCrystal(World worldIn) {
        super(worldIn);
        this.noClip = true;
        this.setSize(0.8F, 1.2F);
    }

    @SideOnly(Side.CLIENT)
    private AnimationFactory factory = new AnimationFactory(this);



    @Override
    protected void entityInit() {
        super.entityInit();

    }



    public void setPosition(BlockPos pos) {
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());



    }

    public void onUpdate() {
        super.onUpdate();
        motionX = 0;
        motionZ = 0;
        this.motionY = 0.04;
        List<EntityLivingBase> targets = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox(), e -> !e.getIsInvulnerable() || (!(e instanceof EntityMaelstromMob)));
        if (!world.isRemote) {

            if (!targets.isEmpty()) {
                Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1));
                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.MOB)
                        .directEntity(this)
                        .element(getElement())
                        .build();
                float damage = 1.0f;
                ModUtils.handleAreaImpact(0.5f, (e) -> damage, this, pos, source, 0.1F, 0, false );
            }
        }
        if (ticksExisted > 15) {
            this.setDead();
        }


    }



    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(MMAnimations.IdleController(this));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }
}
