package com.example.projectmeodchashuv;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPref {
    SharedPreferences mySharedPrefrences;

    public SharedPref(Context context) {
        mySharedPrefrences = context.getSharedPreferences("filename", context.MODE_PRIVATE);
    }

    public void setDarkModeState(boolean state) {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putBoolean("DarkMode", state);
        editor.commit();
    }

    public boolean LoadDarkModeState() {
        boolean state = mySharedPrefrences.getBoolean("DarkMode", false);
        return state;
    }

    private static final String LIST_KEY = "list_key100";

    public static void writeListInPref(Context context, ArrayList<String> list) {
        Gson gson = new Gson(); // copied from an indian on YT https://www.youtube.com/watch?v=TsASX0ZK9ak
        String jsonString = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList<String> readListFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> list = gson.fromJson(jsonString, type);
        return list;
    }

    public void SetUsername(String username) {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public String GetUsername() {
        String user = mySharedPrefrences.getString("username", "guest69");
        return user;
    }

}
