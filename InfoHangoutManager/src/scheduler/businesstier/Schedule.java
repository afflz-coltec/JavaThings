/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.businesstier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scheduler.datatier.DAO;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Schedule implements StoreManagement {
    
    private static int lastID;
    
    private static final ArrayList<Schedule> agendaList = new ArrayList<>();
    
    private int iD;
    private String name;
    private String description;

    public Schedule(String name, String description) {
        this.name = name;
        this.description = description;
        this.iD = ++lastID;
    }
    
    static {
        
        String query = "SELECT MAX(ID) FROM schedules";
        
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement(query);
            ResultSet rs = DAO.getData(stmt);
            Schedule.lastID = rs.getInt("MAX(ID)");
        }
        catch (SQLException e) {
            
        }
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return iD;
    }
    
    public Schedule getAgendaByID(int id) {
        Schedule ag = null;
        
        for(Schedule a : agendaList)
            ag = a.getID() == id ? a : null;
            
        return ag;
    }
    
    public ArrayList<Schedule> getAgendaByName(String name) {
        
        ArrayList<Schedule> list = new ArrayList<>();
        
        for(Schedule a : agendaList) 
            if ( a.getName().equals(name) )
                list.add(a);
        
        return list;
    }

    @Override
    public void store() {
        
        String query = "INSERT INTO schedules (ID,Name,Description) VALUES(?,?,?)";
        
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement(query);
            stmt.setInt(1, this.iD);
            stmt.setString(2, this.name);
            stmt.setString(3, this.description);
            
            DAO.save(stmt);
            
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void remove() {
        
        String removeSchedule = "DELETE schd,evt FROM schedule AS schd, events AS evt WHERE schd.ID=evt.Schedule AND schd.ID=?";
        
        try {
            
            PreparedStatement stmt = DAO.getConnection().prepareStatement(removeSchedule);
            stmt.setInt(1, this.iD);
            
            DAO.remove(stmt);
            
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
