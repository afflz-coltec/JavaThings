/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.businesstier;

import java.sql.Date;
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
public class Event implements StoreManagement {
    
    private static int lastID;
    
    private int iD;
    private String name;
    private String description;
    private Date eventStart;
    private Date eventEnd;
    private Schedule schedule;
    private ArrayList<Person> guestList = new ArrayList<>();
    private boolean isNew;

    /**
     * Constructor to make an Event Object.
     * @param name
     * @param description
     * @param eventStart
     * @param eventEnd
     * @param scdl
     * @param isNew 
     */
    public Event(String name, String description, Date eventStart, Date eventEnd, Schedule scdl, boolean isNew) {
        this.iD = ++lastID;
        this.name = name;
        this.description = description;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.schedule = scdl;
        this.isNew = isNew;
    }
    
    public Event(String name, String description, Date eventStart, Date eventEnd, Schedule scdl) {
        this(name,description,eventStart,eventEnd,scdl,true);
    }
    
    public Event() {
        
    }
    
    public static void fixLastID() {
        lastID--;
    }
    
    public int getiD() {
        return this.iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getName() {
        return this.name;
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

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Date getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Date eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public void insertNewGuest(Person p) {
        this.guestList.add(p);
    }
    
    public ArrayList<Person> getGuestList() {
        return this.guestList;
    }
    
    public boolean isNew() {
        return this.isNew;
    }
    
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    public static Event getEventByID(int id) throws SQLException, EventNotFoundException, ScheduleNotFoundException {
        try {
            return (Event) DAO.getData(DataTypes.isEvent, QueryType.queryByID, id);
        } catch (NotFoundException ex) {
            throw new EventNotFoundException();
        }
    }
    
    public static ArrayList<Event> getEventByName(String name) throws SQLException, EventNotFoundException {
        try {
            return DAO.getData(DataTypes.isEvent, QueryType.queryByName, name);
        } catch (NotFoundException ex) {
            throw new EventNotFoundException();
        }
    }
    
    public static ArrayList<Event> getEventByDate(Date start, Date end) throws SQLException, EventNotFoundException {
        try {
            return DAO.getData(DataTypes.isEvent, QueryType.queryByPeriod, start, end);
        } catch (NotFoundException ex) {
            throw new EventNotFoundException();
        }
    }
    
    public static ArrayList<Event> getEventByGuest(Person p) throws SQLException, EventNotFoundException {
        try {
            return DAO.getData(p);
        } catch (NotFoundException ex) {
            throw new EventNotFoundException();
        }
    }
    
    public static ArrayList<Event> getEventBySchedule(Schedule s) throws SQLException, EventNotFoundException {
        try {
            return DAO.getData(DataTypes.isEvent, QueryType.queryBySchedule, String.valueOf(s.getiD()));
        } catch (NotFoundException ex) {
            throw new EventNotFoundException();
        }
    }
    
    public void print() {
        System.out.print( "\nEvent Name: " + this.name + 
                            "\nDescription: " + this.description + 
                            "\nStarts: " + this.eventStart.toString() + 
                            "\nEnds: " + this.eventEnd.toString() + 
                            "\nSchedule: " + this.schedule.getName() +
                            "\nGuests:");
        
        for(Person p : guestList)
            System.out.print(p.getName() + " ");
        
        System.out.println();
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
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Automatically gets the last used id.
     */
    static {
        
        String query = "SELECT MAX(ID) AS id FROM events";
        
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement(query);
            ResultSet rs = DAO.getData(stmt);
            
            rs.next();
            Event.lastID = rs.getInt("id");
            
            stmt.close();
            rs.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
