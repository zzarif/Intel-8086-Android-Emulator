package com.salikoon.emulator8086.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.salikoon.emulator8086.ui.models.RecentFile;

import java.util.ArrayList;

public class PreferenceManager {

    public enum PreferenceKey {
        SETTINGS_FONT_SIZE("settingsFontSize"),
        SETTINGS_TEXT_WRAP("settingsTextWrap"),
        RECENT_FILES("recentFilePaths");

        private String key;
        PreferenceKey(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
    }


    private final String SALIKOON_STORAGE="salikoonStorage";
    Context context;
    SharedPreferences preferences;
    public PreferenceManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SALIKOON_STORAGE,
                Context.MODE_PRIVATE);
    }

    public void setUserFontSize(String value) {
        preferences.edit().putString(PreferenceKey.SETTINGS_FONT_SIZE.getKey(),value).apply();
    }

    public String getUserFontSize() {
        return preferences.getString(PreferenceKey.SETTINGS_FONT_SIZE.getKey(),"5");
    }

    public void setUserTextWrapOn(boolean value) {
        preferences.edit().putBoolean(PreferenceKey.SETTINGS_TEXT_WRAP.getKey(),value).apply();
    }

    public boolean isUserTextWrapOn() {
        return preferences.getBoolean(PreferenceKey.SETTINGS_TEXT_WRAP.getKey(),false);
    }

    public void addRecentFile(RecentFile recentFile) {
        try {
            ArrayList<RecentFile> recentFiles = new ArrayList<>();
            Gson gson = new Gson();
            String curr_json = preferences.getString(PreferenceKey.RECENT_FILES.getKey(), "");
            preferences.edit().remove(PreferenceKey.RECENT_FILES.getKey()).apply();
            if (!curr_json.isEmpty())
                recentFiles = gson.fromJson(curr_json,
                        new TypeToken<ArrayList<RecentFile>>(){}.getType());
            if (recentFiles.size()==5)
                recentFiles.remove(0);
            recentFiles.add(recentFile);
            String new_json = gson.toJson(recentFiles);
            preferences.edit().putString(PreferenceKey.RECENT_FILES.getKey(), new_json).apply();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RecentFile> getRecentFiles() {
        try {
            ArrayList<RecentFile> recentFiles = new ArrayList<>();
            Gson gson = new Gson();
            String curr_json = preferences.getString(PreferenceKey.RECENT_FILES.getKey(), "");
            if (!curr_json.isEmpty())
                recentFiles = gson.fromJson(curr_json,
                        new TypeToken<ArrayList<RecentFile>>(){}.getType());
            return recentFiles;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}