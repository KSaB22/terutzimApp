package com.example.projectmeodchashuv;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataModel {

    static public ArrayList<Teruzim> teruzims = new ArrayList<>();
    static public ArrayList<User> users = new ArrayList<>();

    public static void saveTeruzim() {
        FirebaseDatabase.getInstance().getReference("teruzim").setValue(DataModel.teruzims);
    }

    public static void saveUsers() {
        FirebaseDatabase.getInstance().getReference("users").setValue(DataModel.users);
    }

}
