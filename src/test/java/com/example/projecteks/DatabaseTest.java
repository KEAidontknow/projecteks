package com.example.projecteks;

import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTest {
    Database database;
    @BeforeEach
    public void setup(){
        database = new Database();
    }
    @Test
    public void addAndGetTask(){
        //ARRANGE
        String name = "TestName";
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
        Task actual = database.getTasks().get((database.getTasks().size()-1));
        //ASSERT
        assertEquals(actual.getState(),accepted.getState());
        assertEquals(actual.getName(),accepted.getName());
        assertEquals(actual.getTimeEstimate(),accepted.getTimeEstimate());
        assertEquals(actual.getDeadline(),accepted.getDeadline());
        assertEquals(actual.getCreationDate(),accepted.getCreationDate());
        assertEquals(actual.getStartDate(),accepted.getStartDate());


    }





}
