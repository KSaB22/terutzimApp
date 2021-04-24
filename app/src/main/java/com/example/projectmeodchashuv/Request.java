package com.example.projectmeodchashuv;

import java.util.ArrayList;

public class Request {

    private String user;
    private String log;
    private ArrayList<Integer> idOfAnswers;
    private String category;

    public Request() {
    }

    public Request(String user, String log, String category) {
        this.user = user;
        this.log = log;
        this.idOfAnswers = new ArrayList<>();
        this.category = category;
    }



    public String getUser() {
        return user;
    }

    public String getLog() {
        return log;
    }

    public ArrayList<Integer> getIDofAnswers() {
        return idOfAnswers;
    }

    public String getCategory() {
        return category;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setIDofAnswers(ArrayList<Integer> IDofAnswers) {
        this.idOfAnswers = IDofAnswers;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

