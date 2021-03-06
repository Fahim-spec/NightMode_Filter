package com.android.tools.bluelightfiltereyecarenightmode.model;

public class ShortcutModel {
    private int icon;
    private String value;

    public ShortcutModel(int icon, String value) {
        this.icon = icon;
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
