package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityDhav extends EntityMaelstromMob implements IAnimatable, IAttack {
    private AnimationFactory factory = new AnimationFactory(this);
    private Consumer<EntityLivingBase> prevAttack;
    private final String ANIM_IDLE = "idle";
    private final String ANIM_WALKING_ARMS = "walkingArms";
    private final String ANIM_WALKING_LEGS = "walkingLegs";
    private final String ANIM_STAPLE_STRIKE = "strike";
    private final String ANIM_SUMMON = "summon";

    protected static final DataParameter<Boolean> SWING_ATTACK = EntityDataManager.createKey(EntityDhav.class, DataSerializers.BOOLEAN);


    /**
     * A mini-boss found in the Golden Valley, there to test the Player's speed and agility
     * @param worldIn
     */
    public EntityDhav(World worldIn) {
        super(worldIn);
        this.setSummonanim(true);
        this.setImmovable(true);
        addEvent(()-> this.setSummonanim(false), 40);
        addEvent(()-> this.setImmovable(false), 40);
    }
    @Override
    public void entityInit() {
        super.entityInit();

        this.dataManager.register(SWING_ATTACK, Boolean.valueOf(false));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 40, 3F, 0.3F));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));

    }


    public boolean isSwingAttack() {return this.dataManager.get(SWING_ATTACK);}
    public void setSwingAttack(boolean value) {this.dataManager.set(SWING_ATTACK, Boolean.valueOf(value));}


    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        float HealthChange = this.getHealth() / this.getMaxHealth();
        double distance = Math.sqrt(distanceSq);
        if(!this.isfightMode()) {
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(SimpleStrike));
            double[] weights = {
                    (distance < 4) ? 1/distance : 0 //Simple Strike

            };
            prevAttack = ModRandom.choice(attacks, rand, weights).next();
            prevAttack.accept(target);
        }
        return 40;
    }

    //Simple Swing
    private Consumer<EntityLivingBase> SimpleStrike = (target)-> {
      this.setfightMode(true);
      this.setSwingAttack(true);
      this.setImmovable(true);
        addEvent(()-> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2.0,1.3,0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
            float damage = this.getAttack();
            ModUtils.handleAreaImpact(1.5f, (e) -> damage, this, offset, source, 0.3f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 0.5f / getRNG().nextFloat() * 0.4f + 0.4f);
        }, 18);
      addEvent(()-> {
        this.setfightMode(false);
        this.setSwingAttack(false);
        this.setImmovable(false);
      },30);
    };

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {

    }

    @Override
    public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(new AnimationController(this, "d_idle", 0, this::predicateIdle));
    animationData.addAnimationController(new AnimationController(this, "d_attack", 0, this::predicateAttack));
    animationData.addAnimationController(new AnimationController(this, "d_arms", 0, this::predicateArms));
    }

    private <E extends IAnimatable>PlayState predicateIdle(AnimationEvent<E> event) {
        if(!this.isfightMode() && !this.isSummonAnim()) {
            if (!(event.getLimbSwingAmount() > -0.10F && event.getLimbSwingAmount() < 0.10F)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_WALKING_LEGS, true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE, true));
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    private <E extends IAnimatable>PlayState predicateAttack(AnimationEvent<E> event) {
        if(!this.isSummonAnim()) {
            if (this.isSwingAttack()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_STAPLE_STRIKE, false));
                return PlayState.CONTINUE;
            }
        }
        if(this.isSummonAnim()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_SUMMON, false));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState predicateArms(AnimationEvent<E> event) {
        if(!this.isfightMode() && !this.isSummonAnim()) {
            if(!(event.getLimbSwingAmount() > -0.10F && event.getLimbSwingAmount() < 0.10F)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_WALKING_ARMS, true));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

        super.readEntityFromNBT(compound);
    }
}
