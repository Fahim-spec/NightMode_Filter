package com.android.tools.bluelightfiltereyecarenightmode.model;

public class ColorModel {
    private int id;
    private String colorCode;

    public ColorModel(){

    }

    public ColorModel(int id, String colorCode) {
        this.id = id;
        this.colorCode = colorCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
