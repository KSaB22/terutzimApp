package com.example.projectmeodchashuv;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataModel {
    /**
     * איפה שנשמרים הערכים מהמסד נתונים
     */
    static public ArrayList<Teruz> teruzims = new ArrayList<>();
    static public ArrayList<User> users = new ArrayList<>();
    static public ArrayList<Request> requests = new ArrayList<>();

    /**
     * שומרת את התירוצים במסד נתונים
     */
    public static void saveTeruzim() {
        FirebaseDatabase.getInstance().getReference("teruzim").setValue(DataModel.teruzims);
    }

    /**
     * שומרת את המשתמשים במסד נתונים
     */
    public static void saveUsers() {
        FirebaseDatabase.getInstance().getReference("users").setValue(DataModel.users);
    }

    /**
     * שומרת את הבקשוות לתירוצים במסד נתונים
     */
    public static void saveRequests() {
        FirebaseDatabase.getInstance().getReference("requests").setValue(DataModel.requests);
    }

}
