package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

import javax.annotation.Nonnull;
import java.util.OptionalDouble;

public class EntityHunterMissile extends Entity implements IAnimatable {
    private EntityLeveledMob shootingEntity = null;
    public static String MISSILE = "missile_move";
    public static String SUMMON_MISSILE = "summon";
    private AnimationFactory factory = new AnimationFactory(this);

    protected static final DataParameter<Float> CLOSEST_TARGET_DISTANCE = EntityDataManager.createKey(EntityLeveledMob.class, DataSerializers.FLOAT);

    protected static final DataParameter<Boolean> SUMMON = EntityDataManager.createKey(EntityHunterMissile.class, DataSerializers.BOOLEAN);

    public static final float Explosion_Distance = (float) Main.mobsConfig.getDouble("maelstrom_hunter.missileDistance");
    public static final float Missile_LifeSpan = (float) Main.mobsConfig.getDouble("maelstrom_hunter.missileLifeSpan");
    public static final float activationDistance = Explosion_Distance * 4;


    public static final double activationDistanceSq = Math.pow(activationDistance, 2);
    private static final double explosionRadius = Math.pow(Explosion_Distance, 2);

    public EntityHunterMissile(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);

    }

    @Override
    public void entityInit() {

        this.dataManager.register(SUMMON, Boolean.valueOf(true));
    }
    public void setSummon(boolean value) {
        this.dataManager.set(SUMMON, Boolean.valueOf(value));
    }
    public boolean isSummon() {
        return this.dataManager.get(SUMMON);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            if (shootingEntity == null) {
                this.setDead();
                return;
            }
            Vec3d pos = this.getPositionVector();
            OptionalDouble optionalDouble = ModUtils.getEntitiesInBox(this, ModUtils.makeBox(pos, pos).grow(20))
                    .stream()
                    .filter(EntityMaelstromMob.CAN_TARGET)
                    .mapToDouble((e) -> e.getDistanceSq(this))
                    .min();
            if (optionalDouble.isPresent()) {
                this.dataManager.set(CLOSEST_TARGET_DISTANCE, ((float) optionalDouble.getAsDouble()));
                if (optionalDouble.getAsDouble() < explosionRadius) {
                    explodeAndDespawn();
                    //EXPLODE AND DIE
                }
            }
                else {
                    this.dataManager.set(CLOSEST_TARGET_DISTANCE, Float.POSITIVE_INFINITY);
                }

            boolean randomExplosionCounter = ticksExisted > Missile_LifeSpan && rand.nextInt(50) == 0;
            if (randomExplosionCounter && !isDead) explodeAndDespawn(); // EXPLODE AND DIE

            else if (getTargetDistanceSq() < activationDistanceSq) {
                this.moveTowards();
                double distance = Math.sqrt(getTargetDistanceSq());
                double distanceToExplode = (activationDistance - distance) /
                        (activationDistance - explosionRadius);
                Vec3d velocity = ModRandom.randVec().normalize().scale(Explosion_Distance);
            }




        }



    }
    public void explodeAndDespawn() {
        this.setDead();
        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.EXPLOSION)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .element(shootingEntity.getElement())
                .stoppedByArmorNotShields().build();

        ModUtils.handleAreaImpact(Explosion_Distance, e -> shootingEntity.getAttack(), this.shootingEntity, getPositionVector(), source, 1, 0, false);
        world.newExplosion(shootingEntity, posX, posY, posZ, 1, false, ModUtils.mobGriefing(world, shootingEntity));
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f + ModRandom.getFloat(0.2f));
    }

    @Override
    protected void readEntityFromNBT( @Nonnull NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT( @Nonnull NBTTagCompound compound) {

    }
        public final boolean canBeAttackedWithItem() {
            return true;
        }
    @Override
    public boolean canBeCollidedWith() {return true;}

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "missile_summon", 0, this::predicateMissile));
    }
    //Set up for animations
    private <E extends IAnimatable>PlayState predicateMissile (AnimationEvent<E> event) {


            //Idle and Moving Anim
            event.getController().setAnimation(new AnimationBuilder().addAnimation(MISSILE, true));

     return PlayState.CONTINUE;
    }

    public void moveTowards() {
        double speed = 0.4;
        if (activationDistance > Explosion_Distance) {
            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving && ((EntityLiving) this.shootingEntity).getAttackTarget() != null) {
                ModUtils.homeToPosition(this, speed, ((EntityLiving) this.shootingEntity).getAttackTarget().getPositionEyes(1));
            }
        }
    }

    private float getTargetDistanceSq() {return dataManager.get(CLOSEST_TARGET_DISTANCE);}

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

        @Override
        public boolean attackEntityFrom(DamageSource source, float amount) {
            if(EntityMaelstromMob.CAN_TARGET.apply(source.getTrueSource()) && !world.isRemote && !isDead) {
                explodeAndDespawn();
            }
            return super.attackEntityFrom(source, amount);
        }


}
