package com.example.projecteks;

import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    Database database;
    Random random;

    char[] c = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    String randomName ="";
    @BeforeEach
    public void setup(){
        database = new Database();
        random = new Random();
        for(int i=0; i<20; i++){
            randomName += c[random.nextInt(0,26)];
        }
    }


    @Test
    public void addGetAndRemoveTask(){
        //ARRANGE
        String name = randomName;
        String startDate = "2023-05-01";
        int estimatedTime = 1234;
        String deadline = "2023-05-30";
        String creationDate = LocalDate.now().toString();
        int state = 1;
        Task accepted = new Task();
        accepted.setName(name);
        accepted.setStartDate(startDate);
        accepted.setTimeEstimate(estimatedTime);
        accepted.setDeadline(deadline);
        accepted.setCreationDate(creationDate);
        accepted.setState(state);

        //ACT
        database.addTask(accepted);
        int index = database.getTasks().size()-1;
        Task actual = database.getTasks().get(index);
        //ASSERT
        assertEquals(actual.getState(),accepted.getState());
        assertEquals(actual.getName(),accepted.getName());
        assertEquals(actual.getTimeEstimate(),accepted.getTimeEstimate());
        assertEquals(actual.getDeadline(),accepted.getDeadline());
        assertEquals(actual.getCreationDate(),accepted.getCreationDate());
        assertEquals(actual.getStartDate(),accepted.getStartDate());
        //ACT
        int id = actual.getId();
        database.removeTask(id);
        index = database.getTasks().size()-1;
        actual = database.getTasks().get(index);
        //ASSERT
        assertFalse(actual.getName().equals(accepted.getName()));

    }
    @Test
    public void testUpdateState(){
        //ARRANGE
        int expected = 0;
        int index = database.getTasks().size()-1;
        int id = database.getTasks().get(index).getId();
        for(int i = 0; i < 3; i++) {
            int pre = database.getTasks().get(index).getState();
            switch (pre) {
                case 1 -> expected = 2;
                case 2 -> expected = 3;
                case 3 -> expected = 1;
            }
            //ACT
            database.updateState(id, pre);
            int actual = database.getTasks().get(index).getState();
            //ASSERT
            assertEquals(actual, expected);
        }
    }





}
