package com.barribob.MaelstromMod.gui.dialog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;

public class GuiNpcButton extends GuiButton {
    protected String[] display;
    private int displayValue;
    public int id;



    public GuiNpcButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, I18n.translateToLocal(buttonText));
        this.displayValue = 0;
        this.id = buttonId;
    }
    public GuiNpcButton(int buttonId, int x, int y, String[] display, int val ){
        this(buttonId, x, y, display[val]);
        this.displayValue = val;
        this.display = display;

    }

    public GuiNpcButton(int buttonId, int x, int y, int l, int m, String string) {
        super(buttonId, x, y, l, m, I18n.translateToLocal(string));
        this.displayValue = 0;
        this.id = buttonId;
    }


    public GuiNpcButton(int buttonId, int x, int y, int l, int m, String string, boolean enabled) {
        this(buttonId, x, y, l, m, string);
        this.enabled = enabled;
    }

    // Missing some stuff, but see if I can make it work with what I have
    public void setDisplayText(String text) {
        this.displayString = I18n.translateToLocal(text);
    }
    public int getValue() {
        return this.displayValue;
    }
    public void setEnabled(boolean IsEnabled) {
        this.enabled = IsEnabled;
    }
    public void setVisible(boolean Visibility) {
        this.visible = Visibility;
    }
    public void setDisplay(int value) {
        this.displayValue = value;
        this.setDisplayText(this.display[value]);
    }
    public boolean mousePressed(Minecraft minecraft, int buttonId, int x) {
        boolean IsEnabled = super.mousePressed(minecraft, buttonId, x);
        if (IsEnabled && this.display != null && this.display.length !=0) {
            this.displayValue = (this.displayValue + 1) % this.display.length;
            this.setDisplayText(this.display[this.displayValue]);
        }
        return IsEnabled;
    }

    public int getWidth() {
        return this.width;
    }
}
