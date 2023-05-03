package com.example.projecteks;

import com.example.projecteks.service.TimeCalc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TimeCalcTest {
    TimeCalc timeCalc;
    @BeforeEach
    public void setup(){
        timeCalc = new TimeCalc();
    }
    @Test
    public void testEaster(){
        //ARRANGE
        LocalDate accepted = LocalDate.of(2024,3,31);
        //ACT
        LocalDate actual=timeCalc.easterDay(2024);
        //ASSERT
        assertEquals(actual,accepted);
    }
    @Test
    public void testIsHoliday(){
        //ARRANGE
        // accepted if true
        ArrayList<LocalDate> holidays = new ArrayList<>();
        holidays.add(LocalDate.of(2024,1,1));//NYTÅRS DAG
        //holidays.add(LocalDate.of(2024,3,24));// PALME SØNDAG
        holidays.add(LocalDate.of(2024,3,28));// SKÆRTORSDAG
        holidays.add(LocalDate.of(2024,3,29));// LANGFREDAG
        holidays.add(LocalDate.of(2024,3,31));// PÅSKE
        holidays.add(LocalDate.of(2024,4,1));// 2.PÅSKEDAG
        holidays.add(LocalDate.of(2024,5,9));// KRISTI HIMMELFARTSDAG
        holidays.add(LocalDate.of(2025,5,29));// KRISTI HIMMELFARTSDAG
        holidays.add(LocalDate.of(2024,5,19));// PINSE
        holidays.add(LocalDate.of(2024,5,20));// 2.PINSE
        holidays.add(LocalDate.of(2024,12,25));// JULE DAG
        holidays.add(LocalDate.of(2024,12,26));// 2.JULE DAG

        holidays.add(LocalDate.of(2024,6,5));// GNUNDLOVS DAG
        holidays.add(LocalDate.of(2024,12,24));// JULEAFTEN
        holidays.add(LocalDate.of(2024,12,31));// NYTÅSAFTEN
        //ACT
        boolean actual = true;
        for(LocalDate h: holidays) {
            if(!timeCalc.isHoliday(h)){
               actual = false;
               break;
            }
        }
        //ASSERT
        assertTrue(actual);
    }

    @Test
    public void testGetAvalableWorkHours(){
        //ARRANGE
        double accepted = 7 + (4*7.5); // Friday31, monday, tuesday, wednesday, monday11
        //ACT
        double actual = timeCalc.getAvalableWorkHours(LocalDate.of(2023,3,31),LocalDate.of(2023,4,11));
        //ASSERT
        assertEquals(actual,accepted);
    }

}
