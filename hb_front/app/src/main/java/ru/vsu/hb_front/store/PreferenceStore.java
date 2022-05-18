package ru.vsu.hb_front.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferenceStore {
    private SharedPreferences sharedPreferences;
    private static final PreferenceStore prefStore = new PreferenceStore();
    private static final Gson gson = new Gson();

    public static void init(Context context) {
        prefStore.sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static PreferenceStore getInstance() {
        return prefStore;
    }

    @SuppressLint("ApplySharedPref")
    public void saveToken(String token) {
        sharedPreferences.edit().putString("token", token).commit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    @SuppressLint("ApplySharedPref")
    public void saveName(String name) {
        sharedPreferences.edit().putString("name", name).commit();
    }

    public String getName() {
        return sharedPreferences.getString("name", "");
    }

}
