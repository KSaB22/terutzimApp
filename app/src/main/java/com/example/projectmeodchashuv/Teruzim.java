package com.example.projectmeodchashuv;

class Teruzim {
    private String creator;
    private String reason;
    private String tluna;
    private int upvotes;

    public Teruzim(String creator, String reason, String tluna) {
        this.creator = creator;
        this.reason = reason;
        this.tluna = tluna;

    }

    public Teruzim() {
        this.creator = "mmmmm";
        this.reason = "000000";
        this.tluna = "000000000";

    }

    public String getCreator() {
        return creator;
    }

    public String getReason() {
        return reason;
    }

    public String getTluna() {
        return tluna;
    }

    public int getUpvote() {
        return upvotes;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTluna(String tluna) {
        this.tluna = tluna;
    }

    public void addUpvote(int place) {
        this.upvotes = DataModel.teruzims.get(place).getUpvote()+ 1;
    }

    @Override
    public String toString() {
        return "למטרת " + this.reason + "   " + this.upvotes + " upvotes";
    }
}
