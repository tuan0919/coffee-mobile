package com.nlu.packages.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyUtils {
    public static void deleteTokenResponse(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Mobile_APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Token");
        editor.apply();
    }

    public static void save(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Mobile_APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String get(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Mobile_APP", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}
