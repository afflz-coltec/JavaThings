/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.datatier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author strudel
 */
public class ConnectionFactory {
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/Scheduler", "pedro", "p");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
