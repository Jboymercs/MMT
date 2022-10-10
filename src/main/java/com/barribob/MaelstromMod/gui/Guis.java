package com.barribob.MaelstromMod.gui;

import com.barribob.MaelstromMod.gui.dialog.GuiNpcScreen;
import net.minecraft.client.gui.GuiScreen;

public class Guis {

    public static final String GuiEmpty = "GE";
    public static final String GuiNPCdialog = "NPD";


    public static GuiScreen getGui(String id, Object... args) {
        switch(id) {
            case GuiEmpty: return null;

            case GuiNPCdialog: return new GuiNpcScreen();

        }

        return null;
    }
}
