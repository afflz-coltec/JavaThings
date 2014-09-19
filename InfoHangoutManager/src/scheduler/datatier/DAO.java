/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.datatier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author strudel
 */
public class DAO {
    
    private static final Connection connection = ConnectionFactory.getConnection();
    
//    static {
//        DAO.connection = ConnectionFactory.getConnection();
//    }
    
    public static Connection getConnection() {
        return connection;
    }
    
    public ResultSet getData(String query) throws SQLException {
        return connection.prepareStatement(query).executeQuery();
    }
    
    public static void save(PreparedStatement stmt) throws SQLException {
        stmt.execute();
        stmt.close();
    }
    
    public static ResultSet getData(PreparedStatement stmt) throws SQLException {
        
        ResultSet rs = stmt.executeQuery();
        stmt.close();
        
        return rs;
    }
    
    public static void remove(PreparedStatement stmt) throws SQLException {
        stmt.execute();
        stmt.close();
    }
    
}
