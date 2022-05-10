package com.salikoon.emulator8086.ui.models;

import java.io.Serializable;
import java.util.ArrayList;

public class HelpModel implements Serializable {
    private String title;
    private String syntax;
    private String description;
    private ArrayList<String> source;
    private ArrayList<String> destination;
    private ArrayList<String> flagsChanged;
    private ArrayList<String> examples;

    public ArrayList<String> getSource() {
        return source;
    }

    public void setSource(ArrayList<String> source) {
        this.source = source;
    }

    public ArrayList<String> getDestination() {
        return destination;
    }

    public void setDestination(ArrayList<String> destination) {
        this.destination = destination;
    }

    public ArrayList<String> getFlagsChanged() {
        return flagsChanged;
    }

    public void setFlagsChanged(ArrayList<String> flagsChanged) {
        this.flagsChanged = flagsChanged;
    }

    public ArrayList<String> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<String> examples) {
        this.examples = examples;
    }

    public HelpModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
