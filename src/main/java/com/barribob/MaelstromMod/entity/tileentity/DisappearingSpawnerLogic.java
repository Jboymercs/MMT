package com.barribob.MaelstromMod.entity.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

/**
 * The tile entity spawner logic for the disappearing spawner. Similar to the
 * vanilla spawner, except it sets itself to air
 */
public class DisappearingSpawnerLogic extends MobSpawnerLogic {
    public DisappearingSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block) {
        super(world, pos, block);
    }

    /**
     * Returns true if there's a player close enough to this mob spawner to activate
     * it.
     */
    protected boolean isActivated() {
        BlockPos blockpos = this.pos.get();
        return isAnyPlayerWithinRangeAt(this.world.get(), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D,
                this.activatingRangeFromPlayer);
    }

    /**
     * Checks to see if any players (in survival) are in range for spawning
     */
    private boolean isAnyPlayerWithinRangeAt(World world, double x, double y, double z, double range) {
        for (int j2 = 0; j2 < world.playerEntities.size(); ++j2) {
            EntityPlayer entityplayer = world.playerEntities.get(j2);

            if (EntitySelectors.NOT_SPECTATING.apply(entityplayer) && !entityplayer.capabilities.isCreativeMode) {
                double d0 = entityplayer.getDistanceSq(x, y, z);

                if (range < 0.0D || d0 < range * range) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void updateSpawner() {
        // Currently does not deal with any server stuff, although this might be a
        // mistake, so potentially this may have to revert back to the vanilla logic
        if (this.world.get().isRemote || !this.isActivated()) {
            return;
        }

        if (this.spawnDelay > 0) {
            --this.spawnDelay;
            return;
        }

        while (this.count < this.maxCount) {
            // Try multiple times to spawn the entity in a good spot
            int tries = 50;
            for (int t = 0; t < tries; t++) {
                if (this.tryToSpawnEntity()) {
                    break;
                } else if (t == tries - 1) {
                    this.count++;
                }
            }
        }

        this.onSpawn(world.get(), pos.get());
    }

    protected void onSpawn(World world, BlockPos blockpos) {
        world.setBlockToAir(blockpos);
    }
}