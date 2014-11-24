/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notstarcraft.dataTile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pedro
 */
public class ConnectionFactory {

    private static Connection connection;
    
    /**
     * Gets a connection with the database.
     * @return 
     */
    public static Connection getConnection() {
        
        try {
            
            // Simple single ton(Not sure if its the right way to write it)
            if ( connection == null )
                connection = DriverManager.getConnection("jdbc:mysql://localhost/NotStarCraftDB", "pedro", "p");
            
            return connection;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
