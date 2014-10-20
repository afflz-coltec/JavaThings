/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.dataTile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import notstarcraft.game.player.Player;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SignInDAO {
    
    private static final Connection connection = ConnectionFactory.getConnection();
    
    public static boolean isUserValid(String username, String password) throws SQLException {
        
        String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setString(1, username);
        
        ResultSet rs = stmt.executeQuery();
        
//        if( rs.next() )
//            if( rs.getString("password").equals(password) )
//                return true;
        
        return rs.next() && rs.getString("password").equals(password);
        
    }
    
    public static Player getPlayer(String username) throws SQLException {
        
        String sql = "SELECT * FROM UserInfo WHERE username=?";
        PreparedStatement stmt = connection.prepareCall(sql);
        
        stmt.setString(1, username);
        
        ResultSet rs = stmt.executeQuery();
        
        if( rs.next() ) {
            
            Player player = new Player();
            
            player.setName(rs.getString("name"));
            player.setNickname(rs.getString("username"));
            player.setEmail(rs.getString("email"));
            player.setGender(rs.getString("gender"));
            player.setBirthDate(rs.getDate("birthDate"));
            
            return player;
            
        }
        
        return null;
        
    }
    
}
