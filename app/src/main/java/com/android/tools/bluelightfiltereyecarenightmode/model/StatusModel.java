package com.android.tools.bluelightfiltereyecarenightmode.model;

public class StatusModel {
    private int id;
    private String intensity;
    private String color;
    private int percent;
    private String opacity;
    private int percentOpacity;
    public StatusModel() {
    }

    public StatusModel(int id, String intensity, String color, int percent) {
        this.id = id;
        this.intensity = intensity;
        this.color = color;
        this.percent = percent;
    }

    public int getPercentOpacity() {
        return percentOpacity;
    }

    public void setPercentOpacity(int percentOpacity) {
        this.percentOpacity = percentOpacity;
    }

    public String getOpacity() {
        return opacity;
    }

    public void setOpacity(String opacity) {
        this.opacity = opacity;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
