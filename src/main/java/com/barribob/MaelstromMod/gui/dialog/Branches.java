package com.barribob.MaelstromMod.gui.dialog;

public enum Branches {

    TEST1("npc", 1, "first", "", 0);









    private int talkCount;
    private String npc;
    private String reqDialogue;
    private String reqBranch;
    private int reqOpt;


    private Branches(String npc, int num, String reqDialogue, String reqBranch, int reqOpt) {
        this.talkCount = num;
        this.npc= npc;
        this.reqDialogue = reqDialogue;
        this.reqBranch = reqBranch;
        this.reqOpt = reqOpt;
    }

    public int getTalkCount() {
        return this.talkCount;
    }

    public String getNpc() {
        return this.npc;
    }

    public String getLowerCaseName() {
        return this.name().toLowerCase();
    }

    public boolean isCorrectBranch(String dialogue, String profession, String previousBranch, int option) {
        if(dialogue.equals(this.reqDialogue) && profession.equals(this.npc) && previousBranch.equals(this.reqBranch) && option == this.reqOpt) {
            return true;
        }
        return false;
    }
}
