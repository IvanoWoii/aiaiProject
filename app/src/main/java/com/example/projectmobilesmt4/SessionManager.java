package com.example.projectmobilesmt4;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN_KEY = "IS_LOGGED_IN";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(LOGIN_KEY, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(LOGIN_KEY, false);
    }
}

