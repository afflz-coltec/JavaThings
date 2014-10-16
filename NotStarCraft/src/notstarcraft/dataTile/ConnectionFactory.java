/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notstarcraft.dataTile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ConnectionFactory {

    private static Connection connection;
    
    public static Connection getConnection() {
        
        try {
            
            if ( connection == null )
                connection = DriverManager.getConnection("jdbc:mysql://localhost/NotStarCraftDB", "pedro", "p");
            
            return connection;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
