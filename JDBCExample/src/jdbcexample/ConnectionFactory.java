/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ConnectionFactory {

    private static Connection connection;
    
    public Connection getConnection() {
        try {
            if ( connection == null )
                connection = DriverManager.getConnection("jdbc:mysql://150.164.102.160/daw-aluno9", "daw-aluno9", "daw09");
            
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
