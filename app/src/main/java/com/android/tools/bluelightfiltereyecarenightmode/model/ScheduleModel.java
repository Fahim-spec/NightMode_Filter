package com.android.tools.bluelightfiltereyecarenightmode.model;

public class ScheduleModel {

    private int id;
    private String timeStart;
    private String timeEnd;
    private int percent;
    private String color;
    private int flag;
    public ScheduleModel(){}
    public ScheduleModel(int id, String timeStart, String timeEnd, int percent, String color) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.percent = percent;
        this.color = color;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
