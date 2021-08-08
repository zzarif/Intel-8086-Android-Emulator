package com.salikoon.emulator8086.ui.models;

public class RecentFile {
    private String fileName,filePath,time;

    public RecentFile() {
    }

    public RecentFile(String fileName, String filePath, String time) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.time = time;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
