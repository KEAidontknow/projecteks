package com.example.projecteks.dto;

import java.util.ArrayList;

public class DateDTOList {
    private ArrayList<DateDTO> dateDTOS;
    private int columnsOfFirstMonth;
    private int columnsOfSecondMonth;
    private int columnsOfFirstYear;
    private int columnsOfSecondYear;

    public DateDTOList() {
    }

    public ArrayList<DateDTO> getDateDTOS() {
        return dateDTOS;
    }

    public void setDateDTOS(ArrayList<DateDTO> dateDTOS) {
        this.dateDTOS = dateDTOS;
    }

    public int getColumnsOfFirstMonth() {
        return columnsOfFirstMonth;
    }

    public void setColumnsOfFirstMonth(int columnsOfFirstMonth) {
        this.columnsOfFirstMonth = columnsOfFirstMonth;
    }

    public int getColumnsOfSecondMonth() {
        return columnsOfSecondMonth;
    }

    public void setColumnsOfSecondMonth(int columnsOfSecondMonth) {
        this.columnsOfSecondMonth = columnsOfSecondMonth;
    }

    public int getColumnsOfFirstYear() {
        return columnsOfFirstYear;
    }

    public void setColumnsOfFirstYear(int columnsOfFirstYear) {
        this.columnsOfFirstYear = columnsOfFirstYear;
    }

    public int getColumnsOfSecondYear() {
        return columnsOfSecondYear;
    }

    public void setColumnsOfSecondYear(int columnsOfSecondYear) {
        this.columnsOfSecondYear = columnsOfSecondYear;
    }
}
