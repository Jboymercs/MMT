package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Keeps track of all the sound resources and registers them
 */
public class SoundsHandler {
    public static SoundEvent ENTITY_SHADE_AMBIENT;
    public static SoundEvent ENTITY_SHADE_HURT;
//    public static SoundEvent ENTITY_SHADE_DEATH;
    public static SoundEvent ENTITY_HORROR_AMBIENT;
    public static SoundEvent ENTITY_HORROR_HURT;
//    public static SoundEvent ENTITY_HORROR_DEATH;
    public static SoundEvent ENTITY_BEAST_AMBIENT;
    public static SoundEvent ENTITY_BEAST_HURT;
    public static SoundEvent ENTTIY_CRAWLER_AMBIENT;
    public static SoundEvent ENTTIY_CRAWLER_HURT;
    public static SoundEvent ENTITY_MONOLITH_AMBIENT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_BLOCK;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_AMBIENT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_HURT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_DEATH;
    public static SoundEvent ENTITY_GAUNTLET_AMBIENT;
    public static SoundEvent ENTITY_GAUNTLET_HURT;
    public static SoundEvent ENTITY_GAUNTLET_LAZER_CHARGE;
    public static SoundEvent NONE;

    public static SoundEvent ENTITY_KNIGHT_CAST;

    public static SoundEvent ENTITY_KNIGHT_SPIN;

    public static SoundEvent ENTITY_KNIGHT_SLAM;

    public static SoundEvent ENTITY_KNIGHT_EVADE;

    public static SoundEvent ENTITY_KNIGHT_HURT;

    public static SoundEvent ENTITY_KNIGHT_DEATH;

    public static SoundEvent ENTITY_KNIGHT_SUMMONCRYSTALS;

    public static SoundEvent ENTITY_KNIGHT_CRYSTALSUMMON;

    public static SoundEvent ENTITY_KNIGHT_CAST1;

    public static SoundEvent ENTITY_NAVIGATOR_HORN;

    public static SoundEvent ENTITY_HORROR_SHOOT;

    public static SoundEvent BEETLE_IDLE;

    public static SoundEvent ENTITY_WRAITH_IDLE;

    public static SoundEvent ENTITY_WRAITH_HURT;

    public static SoundEvent ENTITY_NK_SLASH;

    public static SoundEvent VOID_SPIKE_SHOOT;
    public static SoundEvent APPEARING_WAVE;

    public static SoundEvent SPORE_PREPARE;

    public static SoundEvent BLOSSOM_BURROW;

    // Sound hooks
    public static class Hooks{
        public static SoundEvent ENTITY_ILLAGER_SPELL_CHARGE;
        public static SoundEvent ENTITY_ILLAGER_DOME_CHARGE;
        public static SoundEvent ENTITY_ILLAGER_VORTEX;
        public static SoundEvent ENTITY_ILLAGER_DOME;
    }

    public static void registerSounds() {
        ENTITY_HORROR_AMBIENT = registerSound("horror.ambient", "entity");
        ENTITY_HORROR_HURT = registerSound("horror.hurt", "entity");

        ENTITY_SHADE_AMBIENT = registerSound("shade.ambient", "entity");
        ENTITY_SHADE_HURT = registerSound("shade.hurt", "entity");

        ENTITY_BEAST_AMBIENT = registerSound("beast.ambient", "entity");
        ENTITY_BEAST_HURT = registerSound("beast.hurt", "entity");

        ENTTIY_CRAWLER_AMBIENT = registerSound("swamp_crawler.ambient", "entity");
        ENTTIY_CRAWLER_HURT = registerSound("swamp_crawler.hurt", "entity");
        ENTITY_MONOLITH_AMBIENT = registerSound("monolith.ambient", "entity");
        ENTITY_CHAOS_KNIGHT_BLOCK = registerSound("chaos_knight.block", "entity");
        ENTITY_CHAOS_KNIGHT_AMBIENT = registerSound("chaos_knight.ambient", "entity");
        ENTITY_CHAOS_KNIGHT_HURT = registerSound("chaos_knight.hurt", "entity");
        ENTITY_CHAOS_KNIGHT_DEATH = registerSound("chaos_knight.death", "entity");
        ENTITY_GAUNTLET_AMBIENT = registerSound("gauntlet.ambient", "entity");
        ENTITY_GAUNTLET_HURT = registerSound("gauntlet.hurt", "entity");
        ENTITY_GAUNTLET_LAZER_CHARGE = registerSound("gauntlet.lazer_charge", "entity");
        NONE = registerSound("none", "entity");

        ENTITY_KNIGHT_CAST = registerSound("mk.cast", "entity");
        ENTITY_KNIGHT_SPIN = registerSound("mk.spin", "entity");
        ENTITY_KNIGHT_SLAM = registerSound("mk.slam", "entity");
        ENTITY_KNIGHT_EVADE = registerSound("mk.evade", "entity");
        ENTITY_KNIGHT_HURT = registerSound("mk.hurt", "entity");
        ENTITY_KNIGHT_DEATH = registerSound("mk.death", "entity");
        ENTITY_KNIGHT_SUMMONCRYSTALS = registerSound("mk.summoncrystals", "entity");
        ENTITY_KNIGHT_CAST1 = registerSound("mk.cast1", "entity");

        ENTITY_NAVIGATOR_HORN = registerSound("navigator.rally", "entity");

        ENTITY_KNIGHT_CRYSTALSUMMON = registerSound("mk.crystalsummon", "entity");
        ENTITY_HORROR_SHOOT = registerSound("horror.shoot", "entity");

        BEETLE_IDLE = registerSound("beetle.idle", "entity");

        ENTITY_WRAITH_IDLE = registerSound("wraith.idle", "entity");
        ENTITY_WRAITH_HURT = registerSound("wraith.hurt", "entity");

        ENTITY_NK_SLASH = registerSound("nether_knight.knight_slash_three", "entity");
        VOID_SPIKE_SHOOT = registerSound("spike.shoot", "entity");
        APPEARING_WAVE = registerSound("spike.wave", "entity");
        BLOSSOM_BURROW = registerSound("spike.burrow", "entity");
        SPORE_PREPARE = registerSound("spike.prepare", "entity");

        Hooks.ENTITY_ILLAGER_SPELL_CHARGE = registerSound("illager.spell_charge", "entity");
        Hooks.ENTITY_ILLAGER_DOME_CHARGE = registerSound("illager.dome_charge", "entity");
        Hooks.ENTITY_ILLAGER_VORTEX = registerSound("illager.vortex", "entity");
        Hooks.ENTITY_ILLAGER_DOME = registerSound("illager.dome", "entity");



    }

    private static SoundEvent registerSound(String name, String category) {
        String fullName = category + "." + name;
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, fullName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(fullName);
        ForgeRegistries.SOUND_EVENTS.register(event);

        return event;
    }
}
