package com.barribob.MaelstromMod.gui.dialog;

import com.barribob.MaelstromMod.gui.GuiHandler;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiNpcScreen extends GuiScreen {
private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, ":textures/gui/npc_gui.png");

private int letterCount= 1;
private int interval = 1;
private int intervalCount = 0;
private int dialogLength = 0;
private String dialog = "";
private String option0 = "";
private String option1 = "";
private String option2 = "";
private double scaleMultiplier = 1;
private int xSize = 960;

private String npcName = "";
private int ySize = 540;
private NPCDialog instance;

private List<String> strings = new ArrayList<>();


private int selectedOption = -1;
private int talkCount = 0;


public GuiNpcScreen() {
    this.instance = NPCDialog.instance;
}
@Override
    public void initGui(){
    this.scaleMultiplier = this.getScaleMultiplier();
    this.xSize = (int) (512 * this.scaleMultiplier);
    this.ySize = (int) (164 * this.scaleMultiplier);
    this.buttonList.add(new GuiButton(1, this.width / 2 - 200, this.height - this.ySize - 20, I18n.format("gui.dialogue.talk")));
    this.buttonList.add(new GuiButton(2, this.width / 2, this.height - this.ySize - 20, I18n.format("gui.dialogue.trade")));

    this.npcName = instance.getNameDialog().getName().toLowerCase();



    if(this.dialog.equals("")) {
        this.dialog = instance.getLocalizedDialogue(talkCount);
        //this.dialogue = I18n.format(String.format("dweller.%s.talk%d", this.getTalkingEntityName(), this.talkCount));
        this.dialogLength = this.dialog.length();
    }
    this.setDialogSpeed();
    this.option0 = instance.getLocalizedDialogueOption(talkCount, 0);
    this.option1 = instance.getLocalizedDialogueOption(talkCount, 1);
    if(this.option0 != null) this.selectedOption = 0;
    this.splitDialog();
    super.initGui();
}

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawModalRectWithCustomSizedTexture(this.width/2 - this.xSize/2, this.height - this.ySize, 0F, 0F, xSize, ySize, xSize, xSize);
        this.setDialogSpeed();

        int totalOffset = 5;
        for(int i = 0; i < strings.size(); i++) {
            int prevLength = GuiHandler.getPreviousStringsLength(strings, i);
            if(this.letterCount > prevLength) {
                String s = strings.get(i);
                String toWrite = s.substring(0, Math.min(s.length(), Math.max(0, this.letterCount - 1 - prevLength)));
                //String toWrite = strings.get(i).substring(0, Math.min(stringettes.get(j).length(), Math.max(0, this.letterCount - 1 - GuiHelper.getPreviousStringsLength(i, this.dialogue) - GuiHelper.getPreviousStringsLengthByWidth(j, strings[i], (int)(this.xSize * 0.9), mc.fontRenderer))));
                drawString(mc.fontRenderer, toWrite, this.width/2 - this.xSize / 2 + (int)(24 * this.scaleMultiplier), this.height + (int)(18*this.scaleMultiplier) + totalOffset - this.ySize, 0xFFFFFF);
                totalOffset += 15;
            } else break;
        }

        if(this.option0 != null && this.option1 != null && this.letterCount == this.dialogLength) {
            String[] option0split = GuiHandler.splitStrings(this.option0);
            for(int i = 0; i < option0split.length; i++) {
                String toWrite = option0split[i].substring(0, Math.min(option0split[i].length(), Math.max(0, this.option0.length() - 1 - GuiHandler.getPreviousStringsLength(i, this.option0))));
                drawCenteredString(mc.fontRenderer, toWrite, this.width/2 - this.xSize / 4, this.height + (i * 15) - (int)(this.ySize*0.39), (this.selectedOption == 1 ? 0xFFFFFF : 0xFFFF00));

            }

            String[] option1split = GuiHandler.splitStrings(this.option1);
            for(int i = 0; i < option1split.length; i++) {
                String toWrite = option1split[i].substring(0, Math.min(option1split[i].length(), Math.max(0, this.option1.length() - 1 - GuiHandler.getPreviousStringsLength(i, this.option1))));
                drawCenteredString(mc.fontRenderer, toWrite, this.width/2 + this.xSize / 4, this.height + (i * 15) - (int)(this.ySize*0.39), (this.selectedOption == 0 ? 0xFFFFFF : 0xFFFF00));

            }

        }

        this.setDialogSpeed();


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

private double getScaleMultiplier() {
    switch(this.mc.gameSettings.guiScale) {
        case 0 : return 0.85;
        case 1 : return 1.5;
        case 2 : return 1;
        case 3 : return 0.875;
        default: return 1;
    }
}
@Override
    public void updateScreen() {
        this.intervalCount++;
        if (this.letterCount < this.dialogLength) {
            if (this.intervalCount >= this.interval) {
                this.intervalCount = 0;
                this.letterCount++;
                char c = this.dialog.charAt(this.letterCount - 2);
                if (c == '.' || c == '?' || c == '!') this.intervalCount-=5;
                else if(this.dialog.charAt(this.letterCount - 2) == ',') this.intervalCount--;
                this.setDialogSpeed();
            }
        }
}

    private void setDialogSpeed() {
        boolean deleteChar = true;
        int offset = 0;
        if(this.letterCount >= this.dialogLength - 1) return;
        if(this.letterCount == 1) offset = -1;
        switch(this.dialog.charAt(this.letterCount + offset)) {
            case '{':
                this.interval += 1;
                break;
            case '[':
                this.interval = 3;
                break;
            case '|':
                this.intervalCount -= 8;
                break;
            case '}':
            case ']':
                this.interval = 1;
                break;
            default:
                deleteChar = false;
        }
        if(deleteChar) {
            StringBuilder sb = new StringBuilder(this.dialog);
            this.dialog = sb.deleteCharAt(this.letterCount + offset).toString();
            this.dialogLength = this.dialog.length();
            this.splitDialog();
        }


    }
    private void splitDialog() {
        String[] strings = GuiHandler.splitStrings(dialog);
        this.strings.clear();
        int length = 0;
        for(String s : strings) {
            List<String> widthSplit = GuiHandler.splitStringsByWidth(s, (int) (this.xSize * 0.9), mc.fontRenderer);
            this.strings.addAll(widthSplit);
            for(String ws : widthSplit)
                length += ws.length();
        }
        this.dialogLength = length;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {

        NPCDialog.removeInstance();
        //}
        super.onGuiClosed();
    }

    private void proceedDialogue(boolean option) {
        if(instance.doesCloseDialogue(talkCount, this.selectedOption)) {
            if(instance.getDialogue() == dialogData.NPC)
                talkCount++;
            instance.updateDialogueData(talkCount, selectedOption);
            this.mc.displayGuiScreen((GuiScreen)null);
            NPCDialog.removeInstance();
            return;
        }
        int tmp = this.talkCount;
        this.talkCount = option ? 0 : this.talkCount+1;
        if(instance.updateDialogueData(talkCount, selectedOption)) {
            this.talkCount = 0;
            this.selectedOption = -1;
        }
        this.talkCount %= instance.getTalkCount();
        this.letterCount = 1;
        this.interval = 1;
        this.dialog = instance.getLocalizedDialogue(talkCount);
        this.option0 = instance.getLocalizedDialogueOption(talkCount, 0);
        this.option1 = instance.getLocalizedDialogueOption(talkCount, 1);
        this.dialogLength = this.dialog.length();
        this.splitDialog();
        this.setDialogSpeed();
        if(this.option0 != null) this.selectedOption = 0;
        else this.selectedOption = -1;

    }

    private void completeDialogue() {
        StringBuilder sb = new StringBuilder(this.dialog);
        for(int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if(c == '{' || c == '[' || c == '}' || c == ']' || c == '|') {
                sb.deleteCharAt(i);
                i--;
            }
        }
        this.dialog = sb.toString();
        this.dialogLength = this.dialog.length();
        this.letterCount = this.dialogLength;
        this.splitDialog();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1:
                if(this.letterCount < this.dialogLength) completeDialogue();
                else {
                    if(this.selectedOption != -1) proceedDialogue(true);
                    else proceedDialogue(false);
                }
                break;
            case 2:
                //this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setDialogueType(0);
                //DialogueHandler.removeDialogue(Minecraft.getMinecraft().player);

                NPCDialog.removeInstance();
                break;
        }
        super.actionPerformed(button);
    }

}
