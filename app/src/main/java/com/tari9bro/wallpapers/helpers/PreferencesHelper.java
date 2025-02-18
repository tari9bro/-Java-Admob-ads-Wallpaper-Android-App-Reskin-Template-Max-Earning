package com.tari9bro.wallpapers.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferencesHelper {
    public PreferencesHelper(Activity activity) {
        this.activity = activity;
    }

    Activity activity;
    public void SaveString(String key, String value) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String LoadString(String key) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getString(key, activity.getPackageName());

    }
    public void SaveInt(String key, int value) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int LoadInt(String key) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
    public boolean LoadBool(String key) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }
    public void SaveBool(String key, Boolean value) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public void saveIntArray(int intNumb, String KEY_INT_ARRAY ) {
          Gson gson = new Gson();
        ArrayList<Integer> intArray = new ArrayList<>();
        intArray.add(intNumb);
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String intArrayJson = gson.toJson(intArray);
        editor.putString(KEY_INT_ARRAY, intArrayJson);
        editor.apply();
    }

    public ArrayList<Integer> LoadIntArray(String KEY_INT_ARRAY) {
          Gson gson = new Gson();
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        String intArrayJson = preferences.getString(KEY_INT_ARRAY, null);
        if (intArrayJson != null) {
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            return gson.fromJson(intArrayJson, type);
        } else {
            return new ArrayList<>();
        }
    }

    void saveConstraintLayout(boolean isConstraintLayoutVisible) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isConstraintLayoutVisible", isConstraintLayoutVisible);
        editor.apply();
    }
    boolean loadConstraintLayout() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isConstraintLayoutVisible", false);
    }
}

