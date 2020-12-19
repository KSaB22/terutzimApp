package com.example.projectmeodchashuv;

class Teruzim {
    private String reason;
    private String creator;
    private String tluna;
    private int upvotes;

    public Teruzim(String reason, String tluna, String creator,  int upvotes) {
        this.reason = reason;
        this.creator = creator;
        this.tluna = tluna;
        this.upvotes = upvotes;
    }

    public Teruzim() {
        this.reason = "000000";
        this.creator = "mmmmm";
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

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
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
        this.upvotes = DataModel.teruzims.get(place).getUpvotes()+ 1;
    }

}
