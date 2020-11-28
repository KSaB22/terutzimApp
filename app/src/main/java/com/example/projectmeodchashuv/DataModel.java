package com.example.projectmeodchashuv;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataModel {

    static public ArrayList<Teruzim> teruzims = new ArrayList<>();

    public static void save()
    {
        FirebaseDatabase.getInstance().getReference("teruzim").setValue(DataModel.teruzims);
    }

}
