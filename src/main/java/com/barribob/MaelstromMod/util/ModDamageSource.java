package com.barribob.MaelstromMod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

/**
 * Holds the custom damage sources that is the maelstrom damage type
 */
public class ModDamageSource {
    public static final String MAELSTROM = Reference.MOD_ID + ":" + "maelstrom";
    public static final String MOB = Reference.MOD_ID + ":" + "mobMaelstrom";
    public static final String PLAYER = Reference.MOD_ID + ":" + "playerMaelstrom";
    public static final String PROJECTILE = Reference.MOD_ID + ":" + "thrownMaelstrom";
    public static final String EXPLOSION = Reference.MOD_ID + ":" + "explosionMaelstrom.player";
    public static final String MAGIC = Reference.MOD_ID + ":" + "magicMaelstrom";

    public static final DamageSource MAELSTROM_DAMAGE = (new DamageSource(MAELSTROM));
    // To Fix the Access Transformer Issue, move the mm_at.cfg to META_INF after exporting the mod. //
    public static DamageSource causeElementalMeleeDamage(EntityLivingBase mob, Element element) {
        return new EntityElementalDamageSource(MOB, mob, element);
    }

    public static DamageSource causeElementalPlayerDamage(EntityPlayer player, Element element) {
        return new EntityElementalDamageSource(PLAYER, player, element);
    }

    public static DamageSource causeElementalThrownDamage(Entity source, @Nullable Entity indirectEntityIn, Element element) {
        return (new EntityElementalDamageSourceIndirect(PROJECTILE, source, indirectEntityIn, element)).setProjectile();
    }

    public static DamageSource causeElementalMagicDamage(Entity source, @Nullable Entity indirectEntityIn, Element element) {
        return (new EntityElementalDamageSourceIndirect(MAGIC, source, indirectEntityIn, element)).setDamageBypassesArmor();
    }

    public static DamageSource causeElementalExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn, Element element) {
        return new EntityElementalDamageSource(EXPLOSION, entityLivingBaseIn, element).setExplosion();
    }

    public static DamageSourceBuilder builder() {
        return new DamageSourceBuilder();
    }

    public static class DamageSourceBuilder {
        private Element element = Element.NONE;
        private Entity directEntity;
        private Entity indirectEntity;
        private String damageType;
        private boolean stoppedByArmorNotShields;
        private boolean disablesShields;

        public DamageSourceBuilder element(Element element) {
            this.element = element;
            this.damageType = MOB;
            return this;
        }

        public DamageSourceBuilder directEntity(Entity directEntity) {
            this.directEntity = directEntity;
            return this;
        }

        public DamageSourceBuilder stoppedByArmorNotShields() {
            this.stoppedByArmorNotShields = true;
            return this;
        }

        public DamageSourceBuilder type(String damageType) {
            this.damageType = damageType;
            return this;
        }

        public DamageSourceBuilder indirectEntity(Entity indirectEntity) {
            this.indirectEntity = indirectEntity;
            return this;
        }

        public DamageSourceBuilder disablesShields() {
            disablesShields = true;
            return this;
        }

        public DamageSource build() {
            if (this.damageType != null && this.directEntity != null) {
                EntityElementalDamageSourceIndirect source = new EntityElementalDamageSourceIndirect(this.damageType, this.directEntity, this.indirectEntity, this.element);

                if (this.stoppedByArmorNotShields) {
                    source.setStoppedByArmor(true);
                    source.isUnblockable = true;
                }
                source.setDisablesShields(disablesShields);
                return source;
            } else {
                throw new NullPointerException("Damage Source type or entity can be null");
            }
        }
    }
}