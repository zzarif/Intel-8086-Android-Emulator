package com.salikoon.emulator8086.utility;

public enum IntentKey {
    USER_CODE("userCode"),
    EDITOR_TITLE("editorTitle");

    private String key;
    IntentKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
