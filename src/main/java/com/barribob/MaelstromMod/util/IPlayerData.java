package com.barribob.MaelstromMod.util;

public interface IPlayerData {

    void addString(String string, boolean Temp);
    void addStringFromNBT(String string);
    boolean getString(String string);
    void removeString(String string);

    void removeAllStrings();
}
