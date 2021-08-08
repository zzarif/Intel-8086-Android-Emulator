package com.salikoon.emulator8086.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public enum PreferenceKey {
        SETTINGS_FONT_SIZE("settingsFontSize"),
        SETTINGS_TEXT_WRAP("settingsTextWrap"),
        RECENT_FILE_PATHS("recentFilePaths");

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

    public void addFileAbsolutePath(String value) {
        preferences.edit().putString(PreferenceKey.RECENT_FILE_PATHS.getKey(),value).apply();
    }

    public String getFileAbsolutePath() {
        return preferences.getString(PreferenceKey.RECENT_FILE_PATHS.getKey(), "");
    }
}