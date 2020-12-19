package com.example.projectmeodchashuv;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPrefrences;
    public SharedPref(Context context){
        mySharedPrefrences = context.getSharedPreferences("filename", context.MODE_PRIVATE);
    }
    public void setDarkModeState(boolean state){
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putBoolean("DarkMode", state);
        editor.commit();
    }
    public boolean LoadDarkModeState() {
        boolean state= mySharedPrefrences.getBoolean("DarkMode", false);
        return state;
    }

}
