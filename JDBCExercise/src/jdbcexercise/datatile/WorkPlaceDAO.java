/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexercise.datatile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jdbcexercise.business.WorkPlace;

/**
 *
 * @author strudel
 */
public class WorkPlaceDAO {
    
    private static Connection connection = ConnectionFactory.getConnection();
    
    public WorkPlaceDAO() { }
    
    public static void addWorkPlace(WorkPlace workPlace) throws SQLException {

        String sql = "INSERT INTO workplace (ID,enterpriseName,enterpriseAddress) VALUES (?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, workPlace.getID());
        stmt.setString(2, workPlace.getEnterpriseName());
        stmt.setString(3, workPlace.getEnterpriseAddr());
        
        stmt.execute();
        stmt.close();

    }
    
    public static ArrayList getCharges() throws SQLException {
        
        ArrayList<String> list = new ArrayList<>();
        
        String query = "SELECT trampo.enterpriseName, charge FROM contacts AS contato INNER JOIN workplace AS trampo ON trampo.ID = contato.workplace;";
        PreparedStatement stmt = connection.prepareCall(query);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            list.add(rs.getString("enterpriseName") + " - " + rs.getString("charge"));
            
        }
        
        return list;
        
    }
    
    public static ArrayList getWorkPlaces() throws SQLException {
        
        ArrayList<WorkPlace> workplaceList = new ArrayList<>();
        
        PreparedStatement stmt = connection.prepareCall("SELECT * FROM workplace");
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            WorkPlace wp = new WorkPlace();
            
            wp.setEnterpriseName(rs.getString("enterpriseName"));
            wp.setEnterpriseAddr(rs.getString("enterpriseAddress"));
            wp.setID(rs.getInt("ID"));
            
            workplaceList.add(wp);
            
        }
        
        return workplaceList;
        
    }
    
    public static void removeWorkPlace(WorkPlace workPlace) throws SQLException {
        
        String sql = "DELETE FROM workplace WHERE ID=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setInt(1, workPlace.getID());
        
        stmt.execute();
        stmt.close();
        
    }
    
}
