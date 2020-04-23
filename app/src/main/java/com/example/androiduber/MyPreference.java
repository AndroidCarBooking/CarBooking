package com.example.androiduber;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreference {

    public static final String DEFAULT_EMPTY = "";
    public static final String EMAIL = "Email";

    public static SharedPreferences getSharedPreferencces(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void putString(Context context, String key, String value) {
        getSharedPreferencces(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return getSharedPreferencces(context).getString(key, DEFAULT_EMPTY);
    }

}
