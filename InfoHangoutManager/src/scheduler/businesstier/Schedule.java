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
import scheduler.datatier.DAO.DataTypes;
import scheduler.datatier.DAO.QueryType;
import scheduler.exceptions.EventNotFoundException;
import scheduler.exceptions.NotFoundException;
import scheduler.exceptions.ScheduleNotFoundException;

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
    private boolean isNew;

    /**
     * Contructor to instanciate a Schedule.
     * @param name
     * @param description
     * @param isNew 
     */
    public Schedule(String name, String description, boolean isNew) {
        this.name = name;
        this.description = description;
        this.iD = ++lastID;
        this.isNew = isNew;
    }
    
    public Schedule(String name, String description) {
        this(name,description,true);
    }
    
    public Schedule() {
        
    }
    
    /**
     * Avoid the waste of identification numbers.
     */
    public static void fixLastID() {
        lastID--;
    }
    
    public boolean isNew() {
        return this.isNew;
    }
    
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
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
    
    public static Schedule getScheduleByID(int id) throws SQLException, ScheduleNotFoundException {
        try {
            return (Schedule) DAO.getData(DataTypes.isSchedule, QueryType.queryByID, id);
        } catch (NotFoundException ex) {
            throw new ScheduleNotFoundException();
        }
    }
    
    public static ArrayList<Schedule> getScheduleByName(String name) throws SQLException, ScheduleNotFoundException {
        try {
            return DAO.getData(DataTypes.isSchedule, QueryType.queryByName, name);
        } catch (NotFoundException ex) {
            throw new ScheduleNotFoundException();
        }
    }

    @Override
    public void store() {
        DAO.save(this);
    }

    @Override
    public void remove() {
        
        try {
            DAO.remove(this);
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EventNotFoundException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void print() {
        System.out.println( "\nSchedule " + this.iD + 
                            "\nName: " + this.name + 
                            "\nDescription: " + this.description);
    }
    
    /**
     * Automatically gets the last used ID.
     */
    static {
        
        String query = "SELECT MAX(ID) AS id FROM schedules";
        
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement(query);
            ResultSet rs = DAO.getData(stmt);
            
            rs.next();
            Schedule.lastID = rs.getInt("id");
            
            stmt.close();
            rs.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
