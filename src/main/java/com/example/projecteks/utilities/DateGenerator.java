package com.example.projecteks.utilities;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateGenerator {
    public static DateDTOList getDateList(){
        ArrayList<DateDTO> list = new ArrayList<>();
        DateDTO dateDTO = new DateDTO();
        LocalDate d = LocalDate.now();
        int columnsOfFirstMonth = 1;
        int columnsOfFirstYear = 1;
        for(int i = 0; i<30; i++){
            dateDTO.setDate(d);
            dateDTO.setDayName(d.getDayOfWeek().toString());
            dateDTO.setDayOfMonth(d.getDayOfMonth());
            dateDTO.setMonthName(d.getMonth().toString());
            dateDTO.setYear(d.getYear());
            list.add(dateDTO);
            if(d.getMonthValue()==d.plusDays(1).getMonthValue()){
                columnsOfFirstMonth +=1;
            }
            if(d.getYear()==d.plusDays(1).getYear()){
                columnsOfFirstYear +=1;
            }

            d=d.plusDays(1);
        }
        DateDTOList dateDTOList = new DateDTOList();
        dateDTOList.setDateDTOS(list);
        dateDTOList.setColumnsOfFirstMonth(columnsOfFirstMonth);
        dateDTOList.setColumnsOfSecondMonth(30-columnsOfFirstMonth);
        dateDTOList.setColumnsOfFirstYear(columnsOfFirstYear);
        dateDTOList.setColumnsOfSecondYear(30-columnsOfFirstYear);

        return dateDTOList;
    }



}
