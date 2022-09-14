package com.barribob.MaelstromMod.entity.util;

import com.barribob.MaelstromMod.entity.action.IAction;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Designed to organize multiple attacks for a single entity Uses bytes to
 * handle the animations on the server side smoothly
 */
public class ComboAttack {
    private HashMap<Byte, IAction> actions = new HashMap<Byte, IAction>();

    @SideOnly(Side.CLIENT)
    private HashMap<Byte, Supplier<Animation>> animations;
    private byte currentAttack;

    public void setCurrentAttack(byte b) {
        currentAttack = b;
    }

    public byte getCurrentAttack() {
        return currentAttack;
    }

    public IAction getCurrentAttackAction() {
        return getAction(currentAttack);
    }

    @SideOnly(Side.CLIENT)
    public void setAttack(byte b, IAction action, Supplier<Animation> anim) {
        if (animations == null) {
            animations = new HashMap<Byte, Supplier<Animation>>();
        }
        setAttack(b, action);
        animations.put(b, anim);
    }

    public void setAttack(byte b, BiConsumer<EntityLeveledMob, EntityLivingBase> action) {
        actions.put(b, new IAction() {
            @Override
            public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
                action.accept(actor, target);
            }
        });
    }

    public void setAttack(byte b, IAction action) {
        actions.put(b, action);
    }

    @SideOnly(Side.CLIENT)
    public Animation getAnimation(byte b) {
        if (animations == null) {
            return new AnimationNone();
        }
        if (!animations.containsKey(b)) {
            throw new IllegalArgumentException("The byte " + b + " does not correspond to an attack");
        }
        return animations.get(b).get();
    }

    private IAction getAction(byte b) {
        if (!actions.containsKey(b)) {
            throw new IllegalArgumentException("The byte " + b + " does not correspond to an attack");
        }
        return actions.get(b);
    }
}
