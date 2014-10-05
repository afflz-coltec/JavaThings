/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexercise.datatile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author strudel
 */
public class ConnectionFactory {
    
    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            if ( connection == null )
                connection = DriverManager.getConnection("jdbc:mysql://localhost/JDBCExercise", "pedro", "p");
            
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
