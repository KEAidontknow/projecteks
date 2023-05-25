package com.example.projecteks;

import com.example.projecteks.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class AuthTest {

    TestRestTemplate restTemplate;
    URL base;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL("http://localhost:" + 8080);
    }

    @Test
    public void givenCorrectCredentials_whenLogin_ThenSuccess() throws IllegalStateException, IOException {
        restTemplate = new TestRestTemplate();
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        ResponseEntity<String> response = restTemplate.postForEntity(base.toString()+"/login",user, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
                .getBody()
                .contains("true"));
    }

    @Test
    public void givenWrongCredentials_whenLogin_ThenReturnFalse() throws IllegalStateException, IOException {
        restTemplate = new TestRestTemplate();
        User user = new User();
        user.setUsername("user");
        user.setPassword("wrongpassword");
        ResponseEntity<String> response = restTemplate.postForEntity(base.toString()+"/login",user, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
                .getBody()
                .contains("false"));
    }

    @Test
    public void givenLoggedInUser_whenRequestsHomePage_ThenSuccess() throws IllegalStateException, IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString()+"/user", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
                .getBody()
                .contains("user"));
    }

    @Test
    public void givenWrongCredentials_whenRequestsHomePage_ThenUnauthorized() throws IllegalStateException, IOException {
        restTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString()+"/user", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}