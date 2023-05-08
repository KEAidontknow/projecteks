package com.example.projecteks.service;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateGenerator {
    public static ArrayList<LocalDate> getDateList(){ // todo : bedre metode
        ArrayList<LocalDate> dateList = new ArrayList<>();
        LocalDate d = LocalDate.now();
        for(int i = 0; i<30; i++){
            dateList.add(d);
            d.plusDays(i);
        }
        return dateList;
    }



}
