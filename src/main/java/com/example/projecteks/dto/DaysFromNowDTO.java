package com.example.projecteks.dto;

public class DaysFromNowDTO {
    private int daysFromNow;

    public DaysFromNowDTO() {
    }

    public int getDaysFromNow() {
        return daysFromNow;
    }

    public void setDaysFromNow(int daysFromNow) {
        this.daysFromNow = daysFromNow;
    }

    public void addDaysFromNow(int daysFromNow) {
        this.daysFromNow += daysFromNow;
    }

}
