package com.barribob.MaelstromMod.gui.dialog;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityPlayerDialouge;
import com.barribob.MaelstromMod.packets.MessageSyncDialogData;
import com.barribob.MaelstromMod.util.IPlayerData;
import com.barribob.MaelstromMod.util.PlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class NPCDialog {
    public static NPCDialog instance;

    public static void newInstance(int npcID) {
        instance = new NPCDialog(npcID);
    }

    public static void removeInstance() {
        instance = null;
    }

    public dialogData getDialogue() {
        return this.dialogData;
    }

    private dialogData dialogData;
    private Branches branch;
    private EntityPlayerDialouge entity;
    private Minecraft mc;

    public NPCDialog(int id) {
        this.entity = (EntityPlayerDialouge) Minecraft.getMinecraft().world.getEntityByID(id);
        this.mc = Minecraft.getMinecraft();
        this.dialogData = getFirstDialog();
    }

    private dialogData getFirstDialog() {
        String name = entity.getName().toLowerCase();
        IPlayerData data = mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null);
        for (dialogData d : dialogData.values()) {
            if (d != dialogData.FIRST && d.getNpcName().equals(entity) && data.getString("dialog".concat(d.getLowercasedName()))) {
                return d;
            }
        }
        return dialogData.FIRST;
    }

    public int getName() {
        return this.entity.getEntityId();
    }

    public int getTalkCount() {
        int a = getBranchTalkCount();
        if(a != -1) return a;
        if(dialogData != dialogData.FIRST) return dialogData.getTalkCount();
        return entity.getNameDialog().getTalkCount();
    }

    public EntityPlayerDialouge.NameSystem getNameDialog() {
        return entity.getNameDialog();
    }



    public int getBranchTalkCount() {
        if(branch != null)
            return branch.getTalkCount();
        return -1;
    }

    public String getLocalizedDialogue(int talkCount) {
        return I18n.format(this.getDialogueLangKey(talkCount)).concat(" ");
    }

    public String getLocalizedDialogueOption(int talkCount, int option) {
        String string = I18n.format(String.format(this.getDialogueLangKey(talkCount).concat(".option%d"), option));
        if(string.length() > 6) {
            if(string.substring(0, 7).equals("npc")) string = null;
            else string = string.concat(" ");
        }
        return string;
    }

    private String getDialogueLangKey(int talkCount) {
        String prefix = "npc.".concat(this.entity.getName().toLowerCase());

        String dialogueName = this.dialogData.getLowercasedName();
        if(!dialogueName.equals("first")) prefix = prefix.concat(dialogueName);

        prefix = prefix.concat(".");
        if(this.branch != null)
            prefix = prefix.concat(branch.getLowerCaseName());

        prefix = prefix.concat(String.format("talk%d", talkCount));

        return prefix;
    }

    public boolean updateDialogueData(int talkCount, int option) {
        String npc = entity.getName().toLowerCase();
        String currentName = this.dialogData.getLowercasedName();
        String oldBranch = this.branch == null ? "" : this.branch.getLowerCaseName();


        for(dialogData d : dialogData.values()) {
            if(d.getNpcName().equals(npc)) {
                Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialog".concat(d.getLowercasedName()), false);
                Main.network.sendToServer(new MessageSyncDialogData(d.getLowercasedName(), false));
                this.dialogData = d;
                this.branch = null;
                return true;

            }
        }
        if(option != -1) {
            for(Branches b : Branches.values()) {
                if(b.isCorrectBranch(currentName, npc, oldBranch, option)) {
                    this.branch = b;
                    return true;
                }
            }
        }
        if((branch != null && talkCount >= this.branch.getTalkCount())) {
            this.branch = null;
            return true;
        }
        return false;
    }


    private void removeDialogue(dialogData d) {
        Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString("dialog".concat(d.getLowercasedName()));
        Main.network.sendToServer(new MessageSyncDialogData(d.getLowercasedName(), true));
    }

    public boolean doesCloseDialogue(int talkCount, int option) {
        // End Sequence, used for Ending a conversation KEY
        if(this.dialogData == dialogData.END && this.branch == null && option == 1) return true;

        return false;
    }
}
