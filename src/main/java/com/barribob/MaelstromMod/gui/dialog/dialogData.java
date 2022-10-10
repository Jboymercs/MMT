package com.barribob.MaelstromMod.gui.dialog;

public enum dialogData {
    //Base NPC
    NPC("test_dummy", 1 ,0),
    //FIRST ENTRY
    FIRST("any", 0, 0),
    // END ENTRY
    END("npc", 1, 1);

    private int talkCount;
    private String npcName;
    private int unlocks;

    private dialogData(String npcName, int number, int unlocks) {
        this.talkCount = number;
        this.npcName = npcName;
        this.unlocks = unlocks;
    }

    public int getTalkCount() {
      return   this.talkCount;
    }

    public String getNpcName() {
        return this.npcName;
    }

    public String getLowercasedName() {
        return this.name().toLowerCase();
    }
}
