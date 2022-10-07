package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.util.IAttack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityPlayerBaseSS extends EntityPlayerBase implements IAnimatable, IAttack {
    /**
     * Specific Base for Sword and Shield Npcs
     *
     */
    public EntityPlayerBaseSS(World worldIn) {
        super(worldIn);
    }

    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "player_SS", 0, this::predicateSwordAndShield));
    }

    private <E extends IAnimatable>PlayState predicateSwordAndShield(AnimationEvent<E> event) {
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
}
