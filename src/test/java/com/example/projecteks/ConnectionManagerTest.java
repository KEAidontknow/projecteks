package com.example.projecteks;

import com.example.projecteks.utilities.ConnectionManager;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConnectionManagerTest {

    @Test
    public void testConnection() {
        //ACT
        Connection con = ConnectionManager.getConnection();
        boolean actual = (con != null);
        //ASSERT
        assertTrue(actual);
    }
}
