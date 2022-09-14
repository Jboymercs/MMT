package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.IAnimatedMob;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

public class AnimationArchitect implements IAnimatedMob {
    //Basis for queue animations
    private List<BBAnimation> animationList = new ArrayList<>();



    //Adds in a response system for adding animations into a queue.

    /**
     *
     * @param animationId the specified id "horror.walk"
     *
     * @return    Back to an instance of current builder
     */

    //Loop can be defined in the ModBBAnimations Class, making for loops to be disabled
    public AnimationArchitect addAnimation(String animationId) {
        animationList.add(new BBAnimation(animationId));
        return this;
    }


    public AnimationArchitect clearListAnimations() {
        animationList.clear();
        return this;
    }

    public AnimationArchitect removeFromList(String animationId) {
        animationList.remove(animationId);
        return this;
    }


    @Override
    public Animation getCurrentAnimation() {
        return null;
    }
}
