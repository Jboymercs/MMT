package com.barribob.MaelstromMod.entity.entities.overworld;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileAbberrantAttack;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
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

public class EntityAbberrant extends EntityLeveledMob implements IAnimatable, IAttack {
    /**
     * The Locals of the Nether Dungeon, A specialist of it's era.
     */
    private AnimationFactory factory = new AnimationFactory(this);

    private final String MOVE_ANIM = "move";
    private final String IDLE_ANIM = "idle";
    private final String BLINK = "blink";
    private final String RANGED_ANIM = "castSpell";
    private final String STRIKE_ANIM = "simpleStrike";
    private final String DASH_ANIM = "dashAway";
    private final String SPIN_ANIM = "spin";

    private static DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> SIMPLE_STRIKE = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> RANGED = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);

    private static DataParameter<Boolean> DASH = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);
    private static DataParameter<Boolean> SPIN_ATTACK = EntityDataManager.createKey(EntityAbberrant.class, DataSerializers.BOOLEAN);


    private Consumer<EntityLivingBase> prevAttacks;

    public boolean meleeMode;
    public boolean rangedMode;




    public EntityAbberrant(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
        this.isImmuneToExplosions();
        this.setSize(0.3f, 2.0f);
        this.rangedMode = true;
    }
    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(SIMPLE_STRIKE, Boolean.valueOf(false));
        this.dataManager.register(RANGED, Boolean.valueOf(false));
        this.dataManager.register(DASH, Boolean.valueOf(false));
        this.dataManager.register(SPIN_ATTACK, Boolean.valueOf(false));

    }

    public void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));
    }
    public void setSimpleStrike(boolean value) {
        this.dataManager.set(SIMPLE_STRIKE, Boolean.valueOf(value));
    }
    public void setRanged(boolean value) {
        this.dataManager.set(RANGED, Boolean.valueOf(value));
    }
    public void setDash(boolean value) {
        this.dataManager.set(DASH, Boolean.valueOf(value));
    }
    public void setSpinAttack(boolean value) {
        this.dataManager.set(SPIN_ATTACK, Boolean.valueOf(value));
    }
    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }
    public boolean isSimpleStrike() {
        return this.dataManager.get(SIMPLE_STRIKE);
    }
    public boolean isRanged() {
        return this.dataManager.get(RANGED);
    }
    public boolean isDash() {
        return this.dataManager.get(DASH);
    }
    public boolean isSpinAttack() {
        return this.dataManager.get(SPIN_ATTACK);
    }
    @Override
    public void onUpdate() {
        super.onUpdate();


    }
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(rangedMode) {
            if(rand.nextInt(300) == 0) {
                System.out.println("Changed to Melee");
                meleeMode = true;
                rangedMode = false;
            }
        }
        if(meleeMode) {
            if(rand.nextInt(300) == 0) {
                System.out.println("Changed to Ranged");
                rangedMode = true;
                meleeMode = false;
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        EntityLivingBase target = this.getAttackTarget();
        if(target != null && !world.isRemote) {
            double distSq = this.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
            double distance = Math.sqrt(distSq);
            if(meleeMode) {
                if(distance > 3 && !this.isBeingRidden()) {
                    if(!this.isRanged()) {
                        System.out.println("moving");
                        //Move to melee combat
                        double d0 = (target.posX - this.posX) * 0.025;
                        double d1 = (target.posY - this.posY) * 0.05;
                        double d2 = (target.posZ - this.posZ) * 0.025;
                        this.addVelocity(d0, d1, d2);
                    }
                }
            }
        }

    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.0, 20, 14F, 0.3F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPigZombie>(this, EntityPigZombie.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "ab_idle", 0, this::predicateIdle));
        animationData.addAnimationController(new AnimationController(this, "ab_blink", 0, this::predicateBlink));
        animationData.addAnimationController(new AnimationController(this, "ab_attacks", 0, this::predicateAttack));

    }

    private <E extends IAnimatable>PlayState predicateIdle(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(MOVE_ANIM, true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIM, true));
        }
     return PlayState.CONTINUE;
    }
    private <E extends IAnimatable>PlayState predicateBlink(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(BLINK, true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable>PlayState predicateAttack(AnimationEvent<E> event) {
        //Simple Strike Anim
        if(this.isSimpleStrike()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(STRIKE_ANIM, false ));
            return PlayState.CONTINUE;
        }
        // Wand Ranged
        if(this.isRanged()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(RANGED_ANIM, false));
            return PlayState.CONTINUE;
        }
        // Dash
        if(this.isDash()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(DASH_ANIM, false));
            return PlayState.CONTINUE;
        }
        // Spin Attack
        if(this.isSpinAttack()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(SPIN_ANIM, false));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
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

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        if(!this.isFightMode()) {

            List<Consumer<EntityLivingBase>> attacksMelee = new ArrayList<>(Arrays.asList(strikeMelee, RangedAttack, dashAwayMove, spinCircle));
            double[] weights = {
                    (distance < 3) ? 1/distance : 0, // SImple Strike
                    (distance > 3) ? distance * 0.02 : 0, // Ranged Attack
                    (distance < 6 && rangedMode) ? 2/distance : 0, // Dash Away - Ranged Only
                    (distance < 6 && prevAttacks != spinCircle) ? 1/distance : 0 // Spin Circle Attack
            };
            prevAttacks = ModRandom.choice(attacksMelee, rand, weights).next();
            prevAttacks.accept(target);
        }
        return (prevAttacks == RangedAttack) ? 100 : 20;
    }
    // Simple Melee Strike
    private Consumer<EntityLivingBase> strikeMelee = (target) -> {
        this.setFightMode(true);
        this.setSimpleStrike(true);
        addEvent(() -> {
        ModUtils.leapTowards(this, target.getPositionVector(), 0.5f, 0.2f);
        }, 10);

        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.2, 1.2, 0)));
            DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                    .directEntity(this)
                    .build();
            float damage = getAttack();
            ModUtils.handleAreaImpact(0.8f, (e) -> damage, this, offset, source, 0.4f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / getRNG().nextFloat() * 0.4f + 0.8f);
        }, 14);


        addEvent(() -> {
            this.setSimpleStrike(false);
            this.setFightMode(false);
        }, 22);
    };

    private Consumer<EntityLivingBase> RangedAttack = (target) -> {
      this.setRanged(true);
      this.setFightMode(true);

      addEvent(() -> {

          ProjectileAbberrantAttack projectile = new ProjectileAbberrantAttack(this.world, this, this.getAttack());
          Vec3d pos = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.8, 1.3, 0.5)));
          Vec3d targetPos = new Vec3d(target.posX, target.getEntityBoundingBox().minY, target.posZ);
          Vec3d velocity = targetPos.subtract(pos).normalize().scale(0.4);
          projectile.setPosition(pos.x, pos.y, pos.z);
          projectile.setTravelRange(16f);
          ModUtils.setEntityVelocity(projectile, velocity);
          this.getLookVec();
          world.spawnEntity(projectile);


      }, 20);

      addEvent(() -> {
        this.setRanged(false);
        this.setFightMode(false);
      }, 40);
    };

    private Consumer<EntityLivingBase> dashAwayMove = (target) -> {
        this.setDash(true);
        this.setFightMode(true);

        addEvent(()-> {
            Vec3d randomPoint = new Vec3d(ModRandom.getFloat(10), ModRandom.getFloat(1), ModRandom.getFloat(10)).add(this.getPositionVector());
            Vec3d away = this.getPositionVector().subtract(randomPoint).normalize();
            ModUtils.leapTowards(this, away, 0.9f, 0.4f);
        }, 5);

        addEvent(() -> {
            this.setDash(false);
            this.setFightMode(false);
        }, 20);
    };
    // Spin Attack
    private Consumer<EntityLivingBase> spinCircle = (target) -> {
      this.setSpinAttack(true);
      this.setFightMode(true);

      addEvent(()-> {
          for(int i = 0; i < 20; i += 5) {
              addEvent(()-> {
                  Vec3d pos = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1.5, 0)));
                  float damage = this.getAttack() * getConfigFloat("ab_spin_damage");
                  DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                          .directEntity(this)
                          .disablesShields().build();
                  ModUtils.handleAreaImpact(2.8f, (e) -> damage, this, pos, source, 0.4f, 0, false);
              }, i);
          }

      }, 18);

        addEvent(()-> {
            for(int i = 0; i < 20; i += 5) {
                addEvent(()-> {
                    Vec3d pos = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1.5, 0)));
                    float damage = this.getAttack() * getConfigFloat("ab_spin_damage");
                    DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB)
                            .directEntity(this)
                            .disablesShields().build();
                    ModUtils.handleAreaImpact(2.8f, (e) -> damage, this, pos, source, 0.4f, 0, false);
                }, i);
            }

        }, 50);

      addEvent(() -> {
          this.setSpinAttack(false);
          this.setFightMode(false);
      }, 85);

    };




}


