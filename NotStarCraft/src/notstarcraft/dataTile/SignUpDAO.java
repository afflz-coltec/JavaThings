/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.dataTile;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SignUpDAO {

    private static final Connection connection = ConnectionFactory.getConnection();
    
    public static boolean isUserAvailable(String username) throws SQLException {
        
        String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setString(1, username);
        
        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
        
    }
    
    public static void addUser(String username, String password) throws SQLException {
        
        String sql = "INSERT INTO users VALUES (?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setString(1, username);
        stmt.setString(2, password);
        
        stmt.execute();
        
    }
    
    public static void addUserInfo(String username, String name, String gender, String email, Date birthDate) throws SQLException {
        
        String sql = "INSERT INTO UserInfo VALUES(?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setString(1, username);
        stmt.setString(2, name);
        stmt.setDate(3, birthDate);
        stmt.setString(4, email);
        stmt.setString(5, gender);
        
        stmt.execute();
        
    }
    
}
