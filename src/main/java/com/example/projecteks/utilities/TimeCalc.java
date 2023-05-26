package com.example.projecteks.utilities;

import com.example.projecteks.models.Assign;
import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class TimeCalc {

    private static Map<DayOfWeek,Double> weeklyWorkHours;


    public static void setWeeklyWorkHours(){
        weeklyWorkHours = new HashMap<>();
        weeklyWorkHours.put(DayOfWeek.MONDAY,7.5);
        weeklyWorkHours.put(DayOfWeek.TUESDAY,7.5);
        weeklyWorkHours.put(DayOfWeek.WEDNESDAY,7.5);
        weeklyWorkHours.put(DayOfWeek.THURSDAY,7.5);
        weeklyWorkHours.put(DayOfWeek.FRIDAY,7.0);
    }

    public static double getAvalableWorkHours(LocalDate start, LocalDate end){ //UNITEST
        setWeeklyWorkHours();
        double hours = 0;

        while (!start.isAfter(end)){
            if(!isHoliday(start)&&isWorkday(start)){
                hours += weeklyWorkHours.get(start.getDayOfWeek());
            }
            start = start.plusDays(1);
        }
        return hours;
    }

    public static boolean isWorkday(LocalDate day){
        return !(day.getDayOfWeek()==DayOfWeek.SATURDAY)&&!(day.getDayOfWeek()==DayOfWeek.SUNDAY)&&!isHoliday(day);
    }

    public static boolean isHoliday(LocalDate day){ //This is Danish holidays   //UNITEST
        int year = day.getYear();
        LocalDate easter = easterDay(year);
        ArrayList<LocalDate> holidays = new ArrayList<>();
        holidays.add(LocalDate.of(year,1,1)); //New Years day
        holidays.add(LocalDate.of(year,6,5)); // Constitution Day
        holidays.add(LocalDate.of(year,12,24)); // Christmas Eve
        holidays.add(LocalDate.of(year,12,25)); // 1st Christmas day
        holidays.add(LocalDate.of(year,12,26)); // 2nd Christmas day
        holidays.add(LocalDate.of(year,12,31)); // New Years Eve
        holidays.add(easter.minusDays(3)); // Skærtorsdag
        holidays.add(easter.minusDays(2)); // Langfredag
        holidays.add(easter); // 1.Easter
        holidays.add(easter.plusDays(1)); // 2.Easter
        holidays.add(easter.plusWeeks(5).plusDays(4)); // Chr. Himmelfartsdag
        holidays.add(easter.plusWeeks(7)); // 1.Pinse dag
        holidays.add(easter.plusWeeks(7).plusDays(1)); // 2. Pinse dag

        for(LocalDate h : holidays){
            if(day.equals(h)){
                return true;
            }
        }
        return false;
    }


    public static LocalDate easterDay(int year){       //UNITEST
        int a,b,c,d,e,f,g,h,i,k,l,m,month,day;
        a = year % 19;     //rest
        b = year / 100;
        c = year % 100;    //rest
        d = b / 4;
        e = b % 4;        //rest
        f = (b+8) / 25;
        g = (b - f + 1) / 3;
        h = (19 * a + b - d - g + 15) % 30; //rest
        i = c / 4;
        k = c % 4;         //rest
        l = (32 + (2 * e) + (2 * i) - h - k) % 7; // rest
        m = (a + (11 * h) + (22 * l)) / 451;
        month = ((h + l) - (7 * m) +114) / 31;
        day = ((h + l) - (7 * m) +114) % 31; //rest

        LocalDate date = LocalDate.of(year,month,day).plusDays(1);

        return date;
    }
    public static int hoursOfTaskInProject(ArrayList<Task> taskList){
        int sum = 0;
        for (Task t : taskList){
         sum += t.getTimeEstimate();
        }
        return sum;
    }

    public static int deploymentRate(Task task, ArrayList<Assign> assignsList){
        int deploymentRate;
        int sum = 0;
        for (Assign assignment : assignsList){
            sum += getAvalableWorkHours(assignment.getStartDate(),assignment.getEndDate());
        }
        deploymentRate = sum * 100 /(int)(getAvalableWorkHours(task.getStartDate(),task.getDeadline()));
        return deploymentRate;
    }
}
