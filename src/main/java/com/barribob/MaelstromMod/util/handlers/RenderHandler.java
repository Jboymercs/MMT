package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.entity.entities.*;
import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityAlternativeMaelstromGauntletStage1;
import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityAlternativeMaelstromGauntletStage2;
import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityCrimsonCrystal;
import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityMaelstromGauntlet;
import com.barribob.MaelstromMod.entity.entities.npc.*;
import com.barribob.MaelstromMod.entity.entities.EntityPlayerBase;
import com.barribob.MaelstromMod.entity.entities.overworld.*;
import com.barribob.MaelstromMod.entity.model.*;
import com.barribob.MaelstromMod.entity.projectile.*;
import com.barribob.MaelstromMod.entity.render.*;
import com.barribob.MaelstromMod.entity.util.*;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Function;

public class RenderHandler {
    public static void registerEntityRenderers() {

        //Registers all entities
        registerModEntityRenderer(EntityShade.class, new ModelMaelstromWarrior(), "shade_base.png", "shade_azure.png", "shade_golden.png", "shade_crimson.png");
        registerModEntityRenderer(EntityDreamElk.class, new ModelDreamElk(), "dream_elk.png");
        registerModEntityRenderer(EntityBeast.class, new ModelBeast(), "beast.png", "beast.png", "beast.png", "beast_crimson.png");
        registerModEntityRenderer(EntityMaelstromMage.class, new ModelMaelstromMage(), "maelstrom_mage.png", "maelstrom_mage_azure.png", "maelstrom_mage_golden.png", "maelstrom_mage_crimson.png");
        registerModEntityRenderer(EntityFloatingSkull.class, new ModelFloatingSkull(), "floating_skull.png");
        registerModEntityRenderer(Herobrine.class, (manager) -> new RenderHerobrine(manager, new ResourceLocation(Reference.MOD_ID + ":textures/entity/herobrine_1.png")));
        registerModEntityRenderer(EntityHerobrineOne.class, (manager) -> new RenderHerobrine(manager, new ResourceLocation(Reference.MOD_ID + ":textures/entity/shadow_clone.png")));
        registerModEntityRenderer(NexusGunTrader.class, new ModelGunTrader(), "gun_trader.png");
        registerModEntityRenderer(NexusMageTrader.class, new ModelMageTrader(), "mage_trader.png");
        registerModEntityRenderer(NexusArmorer.class, new ModelArmorer(), "armorer.png");
        registerModEntityRenderer(NexusBladesmith.class, new ModelBladesmith(), "bladesmith.png");
        registerModEntityRenderer(NexusSpecialTrader.class, new ModelNexusSaiyan(), "nexus_saiyan.png");
        registerModEntityRenderer(EntityGoldenPillar.class, new ModelGoldenPillar(), "golden_pillar.png");
        registerModEntityRenderer(EntityGoldenBoss.class, RenderStatueOfNirvana::new);
        registerModEntityRenderer(EntityMaelstromWitch.class, new ModelMaelstromWitch(), "maelstrom_witch.png");
        registerModEntityRenderer(EntitySwampCrawler.class, new ModelSwampCrawler(), "swamp_crawler.png");
        registerModEntityRenderer(EntityIronShade.class, new ModelIronShade(), "iron_shade.png", null, null, "iron_shade_crimson.png");
        registerModEntityRenderer(EntityCliffFly.class, new ModelCliffFly(), "cliff_fly.png");
        registerModEntityRenderer(EntityAzureVillager.class, RenderAzureVillager::new);
        registerModEntityRenderer(EntityMaelstromIllager.class, RenderMaelstromIllager::new);
        registerModEntityRenderer(EntityCliffGolem.class, (manager) -> new RenderCliffGolem(manager, "cliff_golem.png"));
        registerModEntityRenderer(EntityMaelstromBeast.class, RenderMaelstromBeast::new);
        registerModEntityRenderer(EntityMonolith.class, RenderMonolith::new);
        registerModEntityRenderer(EntityWhiteMonolith.class, RenderWhiteMonolith::new);
        registerModEntityRenderer(EntityMaelstromLancer.class, new ModelMaelstromLancer(), "maelstrom_lancer.png", "maelstrom_lancer_azure.png", "maelstrom_lancer_golden.png", "maelstrom_lancer_crimson.png");
        registerModEntityRenderer(EntityChaosKnight.class, (manager) -> new RenderChaosKnight(manager, "chaos_knight.png"));
        registerModEntityRenderer(EntityMaelstromHealer.class, new ModelMaelstromHealer(), "maelstrom_healer.png");
        registerModEntityRenderer(EntityMaelstromGauntlet.class, (manager) -> new RenderMaelstromGauntlet(manager, "maelstrom_gauntlet.png"));
        registerModEntityRenderer(EntityTuningForkLazer.class, RenderTuningForkLazer::new);
        registerModEntityRenderer(ProjectileMegaFireball.class, (manager) -> new RenderNonLivingEntity<>(manager, "fireball.png", new ModelFireball(), -1.501F));
        registerModEntityRenderer(EntityMaelstromStatueOfNirvana.class, new ModelStatueOfNirvana(), "maelstrom_statue.png");
        registerModEntityRenderer(ProjectileHomingFlame.class, (manager) -> new RenderNonLivingEntity<>(manager, "homing_fireball.png", new ModelHomingFlame(), -0.2F));
        registerModEntityRenderer(EntityAlternativeMaelstromGauntletStage2.class, new ModelMaelstromGauntlet(), "maelstrom_gauntlet_stage_2.png");
        registerModEntityRenderer(EntityMaelstromFury.class, new ModelMaelstromFury(), "maelstrom_fury.png");
        registerModEntityRenderer(EntityAlternativeMaelstromGauntletStage1.class, new ModelMaelstromGauntlet(), "maelstrom_gauntlet.png");
        registerModEntityRenderer(EntityCrimsonCrystal.class, (manager) -> new RenderCrimsonCrystal(manager, "crystal.png", -0.5f));
        registerModEntityRenderer(ProjectileKnightSlash.class, (manager) -> new RenderNonLivingEntity<>(manager, "knightslash.png", new ModelKnightSlash(), 0.0F));


        //Registers all projectiles
        registerProjectileRenderer(Projectile.class);
        registerProjectileRenderer(ProjectileBullet.class);
        registerProjectileRenderer(EntityPortalSpawn.class);
        registerProjectileRenderer(EntityNexusParticleSpawner.class);
        registerProjectileRenderer(ProjectileSwampSpittle.class, ModItems.SWAMP_SLIME);
        registerProjectileRenderer(EntityParticleSpawner.class);
        registerProjectileRenderer(ProjectileBone.class, Items.BONE);
        registerProjectileRenderer(ProjectileHorrorAttack.class, ModItems.MAELSTROM_PELLET);
        registerProjectileRenderer(ProjectileFireball.class, Items.FIRE_CHARGE);
        registerProjectileRenderer(ProjectileBlackFireball.class, Items.FIRE_CHARGE);
        registerProjectileRenderer(ProjectileGoldenFireball.class, Items.FIRE_CHARGE);
        registerProjectileRenderer(ProjectileMonolithFireball.class, Items.FIRE_CHARGE);
        registerProjectileRenderer(ProjectileGoldenMissile.class, ModItems.GOLD_PELLET);
        registerProjectileRenderer(EntityCrimsonTowerSpawner.class);
        registerProjectileRenderer(EntityHealerOrb.class);
        registerProjectileRenderer(ProjectileChaosFireball.class, ModItems.CRIMSON_PELLET);
        registerProjectileRenderer(ProjectileStatueMaelstromMissile.class, ModItems.MAELSTROM_PELLET);
        registerProjectileRenderer(ProjectileBeastFireball.class, Items.FIRE_CHARGE);
        registerProjectileRenderer(EntityMaelstromTowerDestroyer.class);
        registerProjectileRenderer(ProjectileAbberrantAttack.class, Items.FIRE_CHARGE);
        registerProjectileRenderer(ProjectileVoidLeaf.class, ModItems.VOID_LEAF);
        registerProjectileRenderer(ProjectileSporeBomb.class, ModItems.SPORE_BALL);

    }

    /**
     * Registers an entity with a model and sets it up for rendering
     */
    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, U model, String... textures) {
        registerModEntityRenderer(entityClass, (manager) -> new RenderModEntity(manager, model, textures));
    }

    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, Function<RenderManager, Render<? super T>> renderClass) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>() {
            @Override
            public Render<? super T> createRenderFor(RenderManager manager) {
                return renderClass.apply(manager);
            }
        });
    }

    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass) {
        registerProjectileRenderer(projectileClass, null);
    }

    /**
     * Makes a projectile render with the given item
     *
     * @param projectileClass
     */
    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass, Item item) {
        RenderingRegistry.registerEntityRenderingHandler(projectileClass, new IRenderFactory<T>() {
            @Override
            public Render<? super T> createRenderFor(RenderManager manager) {
                return new RenderProjectile<T>(manager, Minecraft.getMinecraft().getRenderItem(), item);
            }
        });
    }

    //Geckolib Handler for Rendering

    public static void registerGeoEntityRenders() {
        // Azure Maelstrom Horror Redone
        RenderingRegistry.registerEntityRenderingHandler(EntityHorror.class, RenderHorror::new);
        //Azure Golem
        RenderingRegistry.registerEntityRenderingHandler(EntityAzureGolem.class, RenderAzureGolem::new);
        // Golden Valley Maelstrom Knight
        RenderingRegistry.registerEntityRenderingHandler(EntityMaelstromKnight.class, RenderMaelstromKnight::new);
        // Raid Only Maelstrom Navigator
        RenderingRegistry.registerEntityRenderingHandler(EntityMaelstromNavigator.class, RenderMaelstromNavigator::new);
        // Lush Maelstrom Shielder
        RenderingRegistry.registerEntityRenderingHandler(EntityMaelstromShielder.class, RenderMaelstromShielder::new);
        // Player Base
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerBase.class, RenderPlayerBase::new);
        //Player Dialog Base
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerDialouge.class, RenderNPCDialog::new);
        //Azure Beetle
        RenderingRegistry.registerEntityRenderingHandler(EntityAzureBeetle.class, RenderAzureBeetle::new);
        //Azure Wraith & WraithHand
        RenderingRegistry.registerEntityRenderingHandler(EntityAzureWraith.class, RenderAzureWraith::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWraithHand.class, RenderWraithHand::new);
        //Maelstrom Hunter
        RenderingRegistry.registerEntityRenderingHandler(EntityMaelstromHunter.class, RenderMaelstromHunter::new);
        // Nether Knight - Overworld
        RenderingRegistry.registerEntityRenderingHandler(EntityNetherKnight.class, RenderNetherKnight::new);
        // Abberrant Spirit - Overworld
        RenderingRegistry.registerEntityRenderingHandler(EntityAbberrant.class, RenderAbberrant::new);
        // ROT Shade - Overworld
        RenderingRegistry.registerEntityRenderingHandler(EntityShadeKnight.class,RenderShadeKnight::new);
        // Phaser - Overworld
        RenderingRegistry.registerEntityRenderingHandler(EntityPhaser.class, RenderPhaser::new);
        // Dhav - Golden Valley
        RenderingRegistry.registerEntityRenderingHandler(EntityDhav.class, RenderDhav::new);
        // Void Blossom - Lush Dimension
        RenderingRegistry.registerEntityRenderingHandler(EntityVoidBlossom.class, RenderVoidBlossom::new);

        //Projectile Like Entities
        RenderingRegistry.registerEntityRenderingHandler(EntityKnightCrystal.class, RenderKnightCrystal::new);
        // Hunter Missile
        RenderingRegistry.registerEntityRenderingHandler(EntityHunterMissile.class, RenderHunterMissile::new);
        // Fire Ring
        RenderingRegistry.registerEntityRenderingHandler(EntityFireRing.class, RenderFireRing::new);
        //Void Spike
        RenderingRegistry.registerEntityRenderingHandler(EntityVoidSpike.class, RenderVoidSpike::new);

    }
}
