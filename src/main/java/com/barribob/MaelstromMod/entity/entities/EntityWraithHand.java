package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.init.MMAnimations;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class EntityWraithHand extends EntityLeveledMob implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    public EntityWraithHand(World worldIn) {
        super(worldIn);
        this.setSize(0.3f, 0.8f);
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionX = 0;
        this.motionZ = 0;
        List<EntityLivingBase> targets = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox(), e -> !e.getIsInvulnerable() && (!(e instanceof EntityAzureWraith)));

        if (!targets.isEmpty()) {
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(0.7));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(getElement())
                    .build();
            float damage = 4.0f;
            ModUtils.handleAreaImpact(0.4f, (e) -> damage, this, pos, source, 0.1F, 0, false );

            if (ticksExisted == 1) {
                playSound(SoundEvents.EVOCATION_FANGS_ATTACK, 1.0f, 1.0f / (getRNG().nextFloat() * 0.04f + 0.8f));
            }
            if (ticksExisted > 13) {
                this.setDead();
            }
        }



    }

    public void setPosition(BlockPos pos) {
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "hand_grab", 0, this::predicateHand));
    }
    private <E extends IAnimatable>PlayState predicateHand(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idleHand", false));
        return PlayState.CONTINUE;
    }
}
