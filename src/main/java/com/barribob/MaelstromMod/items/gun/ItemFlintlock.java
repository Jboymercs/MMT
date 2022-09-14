package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemFlintlock extends ItemGun {
    public ItemFlintlock(String name, float level) {
        super(name, 20, 6, level);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        float inaccuracy = 0.0f;
        float velocity = 5.0f;
        float pitch = player.rotationPitch + ModRandom.getFloat(1);
        float yaw = player.rotationYaw + ModRandom.getFloat(3);

        Projectile projectile = factory.get(world, player, stack, this.getEnchantedDamage(stack));
        projectile.setElement(getElement());
        projectile.shoot(player, pitch, yaw, 0.0F, velocity, inaccuracy);
        projectile.setTravelRange(30);

        world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("flintlock"));
    }
}
