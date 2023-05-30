package com.example.projecteks;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.context.SecurityContext;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DatabaseTest {
    //TODO: find out how to make a embedded in-memory database for testing purposes
    DatabaseInterface database;
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
// Har udkommenteret denne test, da det ikke lykkedes mig at lave en Mock user, som var nødvendig
    /*@Test
    public void addGetAndRemoveProject(){

        //ARRANGE
        String name = randomName;
        String startDate = "21-08-2026";
        String deadline = "23-10-2027";
        Project accepted = new Project();
        accepted.setProjectName(name);
        accepted.setStartDate(startDate);
        accepted.setDeadline(deadline);

        //ACT


            database.addProject(accepted); // Har brugt denne metode, da det ikke lykkedes mig at lave en Mog user

            Project actual = database.getCertainProject(database.getProjectId(name));
            //ASSERT
            assertEquals(actual.getDeadline(), accepted.getDeadline());
            assertEquals(actual.getProjectName(), accepted.getProjectName());
            assertEquals(actual.getStartDate(), accepted.getStartDate());

            //ACT
            int id = actual.getProjectId();
            //ASSERT
            assertEquals(actual.getProjectName(), database.showProjects().get(database.showProjects().size() - 1).getProjectName());
            //ACT
            database.deleteById(id);
            actual = database.showProjects().get(database.showProjects().size() - 1);
            //ASSERT
            assertNotEquals(actual.getProjectName(), accepted.getProjectName());


    }*/







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
        accepted.setStartDate(LocalDate.parse(startDate));
        accepted.setTimeEstimate(estimatedTime);
        accepted.setDeadline(LocalDate.parse(deadline));
        accepted.setCreationDate(creationDate);
        accepted.setState(state);
        int projectId = database.showProjects().get(database.showProjects().size()-1).getProjectId();
        accepted.setProjectId(projectId);
        //ACT
        database.addTask(accepted);
        int index = database.getTasks(projectId).size()-1;
        Task actual = database.getTasks(projectId).get(index);
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
        if(database.getTasks(projectId).isEmpty()){
            //ASSERT
            assertTrue(true);
        }else {
            index = database.getTasks(projectId).size() - 1;
            actual = database.getTasks(projectId).get(index);
            //ASSERT
            assertFalse(actual.getName().equals(accepted.getName()));
        }

    }
    @Test
    public void testUpdateState(){
        //ARRANGE
        int expected = 0;
        int projectId = database.showProjects().get(database.showProjects().size()-1).getProjectId();
        Task t = new Task();
        t.setName(randomName);
        t.setStartDate(LocalDate.now());
        t.setDeadline(LocalDate.now().plusDays(1));
        t.setCreationDate("2023-05-30");
        t.setProjectId(projectId);
        t.setState(random.nextInt(1,4));
        database.addTask(t);
        int index = database.getTasks(projectId).size()-1;
        int id = database.getTasks(projectId).get(index).getId();

        for(int i = 0; i < 3; i++) {
            int pre = database.getTasks(projectId).get(index).getState();
            switch (pre) {
                case 1 -> expected = 2;
                case 2 -> expected = 3;
                case 3 -> expected = 1;
            }
            //ACT
            database.updateState(id, pre);
            int actual = database.getTasks(projectId).get(index).getState();
            //ASSERT
            assertEquals(actual, expected);


        }
        //Cleanup
        database.removeTask(id);
    }

    @Test
    public void testUpdateStar() {
        // ARRANGE
        int taskId = 1;
        int initialStar = 1;
        int expectedStar = 2;

        // ACT
        database.updateStar(taskId, initialStar);
        Task task = database.getTaskById(taskId);
        int actualStar = task.getStar();

        // ASSERT
        assertEquals(expectedStar, actualStar);
    }

    @Test
    public void testEditTask() {
        // ARRANGE
        Task task = new Task();
        task.setId(1);
        task.setName("Updated Task");
        task.setTimeEstimate(100);
        task.setStartDate(LocalDate.parse("2023-06-01"));
        task.setDeadline(LocalDate.parse("2023-06-30"));

        // ACT
        database.editTask(task);
        Task updatedTask = database.getTaskById(task.getId());

        // ASSERT
        assertEquals(task.getName(), updatedTask.getName());
        assertEquals(task.getTimeEstimate(), updatedTask.getTimeEstimate());
        assertEquals(task.getStartDate(), updatedTask.getStartDate());
        assertEquals(task.getDeadline(), updatedTask.getDeadline());
    }

    @Test
    public void testUpdateProjectName() {
        // ARRANGE
        String newProjectName = "Updated Project Name";
        String newStartDate = "2023-06-01";
        String newDeadline = "2023-06-30";
        int projectId = database.showProjects().get(database.showProjects().size() - 1).getProjectId();

        // ACT
        database.updateProjectName(projectId, newProjectName, newStartDate, newDeadline);
        Project updatedProject = database.getCertainProject(projectId);

        // ASSERT
        assertNotNull(updatedProject);
        assertEquals(newProjectName, updatedProject.getProjectName());
        assertEquals(newStartDate, updatedProject.getStartDate());
        assertEquals(newDeadline, updatedProject.getDeadline());
    }
}







