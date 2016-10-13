package com.dandy.searchapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *SharedPreferences工具类
 */
public class UtilSharedPreferences {

    private static final String sShareKey = "share_dandy";

    public static final String KEY_PAGE = "key_page";
    public static final String KEY_CLASSIFICATION_STATE = "key_classification";


    public static void saveStringData(Context context, String key, String data) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static String getStringData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void saveBooleanData(Context context, String key, boolean data) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, data);
        editor.apply();
    }

    public static boolean getBooleanData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void saveIntData(Context context, String key, int data) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    public static int getIntData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    public static void saveLongData(Context context, String key, long data) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, data);
        editor.apply();
    }

    public static long getLongData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(sShareKey, Context.MODE_PRIVATE);
        return preferences.getLong(key, -1);
    }

}
