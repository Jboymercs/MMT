package com.barribob.MaelstromMod.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(IPlayerData.class)
    public static final Capability<IPlayerData> PLAYERDATA = null;

    IPlayerData instance = PLAYERDATA.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PLAYERDATA;
    }
    public PlayerDataProvider() {

    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == PLAYERDATA ? PLAYERDATA.<T> cast(this.instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) PLAYERDATA.getStorage().writeNBT(PLAYERDATA, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        PLAYERDATA.getStorage().readNBT(PLAYERDATA, this.instance, null, nbt);
    }
}
