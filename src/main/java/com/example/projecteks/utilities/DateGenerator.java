package com.example.projecteks.utilities;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateGenerator {
    public static DateDTOList getDateDTOList(){
        ArrayList<DateDTO> list = new ArrayList<>();
        DateDTO dateDTO;
        LocalDate d =LocalDate.now();

        int columnsOfFirstMonth = 0;
        int columnsOfFirstYear = 0;
        boolean sameMonth = true;
        boolean sameYear = true;
        for(int i = 0; i<30; i++){
            dateDTO = new DateDTO();
            dateDTO.setDate(d);
            dateDTO.setDayName(d.getDayOfWeek().toString());
            dateDTO.setDayOfMonth(d.getDayOfMonth());
            dateDTO.setMonthName(d.getMonth().toString());
            dateDTO.setYear(d.getYear());
            list.add(dateDTO);
            if(sameMonth){
                sameMonth = (d.getMonthValue()==d.plusDays(1).getMonthValue());
                columnsOfFirstMonth +=1;
            }
            if(sameYear){
                sameYear = (d.getYear()==d.plusDays(1).getYear());
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


        //TEST
        for(DateDTO dto : dateDTOList.getDateDTOS()) {
            System.out.println(""+ d + " " + dto.getDate()+", day: "+ dto.getDayName()+" day: "+dto.getDayOfMonth()+" month :"+dto.getMonthName() + "  "+ dateDTOList.getColumnsOfFirstMonth());
        }
        return dateDTOList;
    }



}
