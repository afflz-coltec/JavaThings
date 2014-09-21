/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.datatier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scheduler.businesstier.Event;
import scheduler.businesstier.Person;
import scheduler.businesstier.Schedule;
import scheduler.exceptions.EventNotFoundException;
import scheduler.exceptions.NotFoundException;
import scheduler.exceptions.PersonNotFoundException;
import scheduler.exceptions.ScheduleNotFoundException;

/**
 *
 * @author strudel
 */
public class DAO {
    
    /**
     * Enumeration with the data(table) types.
     */
    public enum DataTypes {
        isSchedule,
        isPerson,
        isEvent;
    };
    
    /**
     * Enumeration with the query type.
     */
    public enum QueryType {
        queryByName,
        queryByCPF,
        queryByID,
        queryByPeriod,
        queryByBirthDay,
        queryBySchedule;
    }
    
    private static final String[] DataTypeOptions = {"schedules", "people", "events"};
    private static final String[] QueryTypeOptions = {"Name","CPF","ID","Start>=? AND End<=?","BirthDay>=? AND BirthDay<=?","Schedule"};
    
    private static Connection connection;
    
    private static final String HOST = "jdbc:mysql://localhost/Scheduler";
    private static final String USER = "pedro";
    private static final String PSWD = "p";
    
    /**
     * Automatically connects to server when the program runs.
     */
    static {
        try {
            DAO.connection = DriverManager.getConnection(HOST,USER,PSWD);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the connection to the server.
     * @return 
     */
    public static Connection getConnection() {
        return connection;
    }
    
    /**
     * Simple method used to run a simple statement.
     * @param stmt
     * @return
     * @throws SQLException 
     */
    public static ResultSet getData(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
    
    /**
     * Gets a single object by an identification number.
     * @param d
     * @param q
     * @param pattern
     * @return
     * @throws SQLException 
     * @throws scheduler.exceptions.ScheduleNotFoundException 
     */
    public static Object getData(DataTypes d, QueryType q, int pattern) throws SQLException, NotFoundException {
        
        Object result = null;
        
        String defaultQuery = "SELECT * FROM " + DataTypeOptions[d.ordinal()] + " WHERE " + QueryTypeOptions[q.ordinal()] + "=?";
        PreparedStatement stmt = connection.prepareStatement(defaultQuery);
        
        stmt.setInt(1, pattern);
        
        ResultSet rs = stmt.executeQuery();
        
        if ( !rs.next() )
            throw new NotFoundException();
        
        switch(d) {
            
            case isSchedule:
                
                Schedule s = new Schedule();
                
                s.setiD(rs.getInt("ID"));
                s.setName(rs.getString("Name"));
                s.setDescription(rs.getString("Description"));
                s.setIsNew(false);
                
                Schedule.fixLastID();
                result = s;
                break;
                
            case isPerson:
                
                Person p = new Person();
                
                p.setCPF(rs.getString("CPF"));
                p.setName(rs.getString("Name"));
                p.setAddress(rs.getString("Address"));
                p.setBirthDate(rs.getDate("BirthDay"));
                p.setIsNew(false);
                
                result = p;
                break;
                
            case isEvent:
                
                Event e = new Event();
                
                e.setiD(rs.getByte("ID"));
                e.setName(rs.getString("Name"));
                e.setDescription(rs.getString("Description"));
                e.setEventStart(rs.getDate("Start"));
                e.setEventEnd(rs.getDate("End"));
                try { e.setSchedule(Schedule.getScheduleByID(rs.getInt("Schedule"))); } catch (ScheduleNotFoundException ex) { }
                e.setIsNew(false);
                
                Event.fixLastID();
                
                String getGuestRelation = "SELECT Person FROM invites WHERE Event=?";
                PreparedStatement st = connection.prepareStatement(getGuestRelation);
                st.setInt(1, rs.getInt("ID"));

                ResultSet r = st.executeQuery();

                while(r.next()) {
                    try { e.insertNewGuest(Person.getPersonByCPF(r.getString("Person"))); } catch (PersonNotFoundException ex) { }
                }
                
                result = e;

                break;
            
        }
        
        return result;
    }
    
    /**
     * Gets a list of objects by date.
     * @param d
     * @param q
     * @param start
     * @param end
     * @return
     * @throws SQLException 
     */
    public static ArrayList getData(DataTypes d, QueryType q, Date start, Date end) throws SQLException, NotFoundException {
        
        ArrayList<Object> list = new ArrayList<>();
        
        String defaultQuery = "SELECT * FROM " + DataTypeOptions[d.ordinal()] + " WHERE " + QueryTypeOptions[q.ordinal()];
        PreparedStatement stmt = connection.prepareStatement(defaultQuery);
        
        stmt.setDate(1, start);
        stmt.setDate(2, end);
        
        ResultSet rs = stmt.executeQuery();
        
        switch(d) {
            
            case isPerson:
                while(rs.next()) {
                    list.add(new Person(rs.getString("CPF"), rs.getString("Name"), rs.getString("Address"), rs.getDate("BirthDay"),false));
                }
                break;
                
            case isEvent:
                while(rs.next()) {
                    
                    Event e = new Event();
                
                    e.setiD(rs.getByte("ID"));
                    e.setName(rs.getString("Name"));
                    e.setDescription(rs.getString("Description"));
                    e.setEventStart(rs.getDate("Start"));
                    e.setEventEnd(rs.getDate("End"));
                    try { e.setSchedule(Schedule.getScheduleByID(rs.getInt("Schedule"))); } catch (ScheduleNotFoundException ex) { }
                    e.setIsNew(false);
                
                    Event.fixLastID();
                    
                    String getGuestRelation = "SELECT Person FROM invites WHERE Event=?";
                    PreparedStatement st = connection.prepareStatement(getGuestRelation);
                    st.setInt(1, rs.getInt("ID"));

                    ResultSet r = st.executeQuery();

                    while(r.next()) {
                        try { e.insertNewGuest(Person.getPersonByCPF(r.getString("Person"))); } catch (PersonNotFoundException ex) { }
                    }

                    list.add(e);
                }
                break;
            
        }
        
        if (list.isEmpty())
            throw new NotFoundException();
        else
            return list;
        
    }
    
    /**
     * Gets a list of object by some kind of string type.
     * @param d
     * @param q
     * @param pattern
     * @return
     * @throws SQLException 
     */
    public static ArrayList getData(DataTypes d, QueryType q, String pattern) throws SQLException, NotFoundException {
        
        ArrayList<Object> list = new ArrayList<>();
        
        String defautQuery = "SELECT * FROM " + DataTypeOptions[d.ordinal()] + " WHERE " + QueryTypeOptions[q.ordinal()] + "=?";
        PreparedStatement stmt = connection.prepareStatement(defautQuery);

        if (q == QueryType.queryBySchedule)
            stmt.setInt(1, Integer.parseInt(pattern));
        else
            stmt.setString(1, pattern);
        
        ResultSet rs = stmt.executeQuery();
        
        switch(d) {
            
            case isSchedule:
                while(rs.next()) {
                    Schedule s = new Schedule();
                    
                    s.setiD(rs.getInt("ID"));
                    s.setName(rs.getString("Name"));
                    s.setDescription(rs.getString("Description"));
                    s.setIsNew(false);
                    
                    list.add(s);
                }
                break;
                
            case isPerson:
                while(rs.next()) {
                    list.add(new Person(rs.getString("CPF"), rs.getString("Name"), rs.getString("Address"), rs.getDate("BirthDay"),false));
                }
                break;
                
            case isEvent:
                while(rs.next()) {
                    
                    Event e = new Event();

                    e.setiD(rs.getByte("ID"));
                    e.setName(rs.getString("Name"));
                    e.setDescription(rs.getString("Description"));
                    e.setEventStart(rs.getDate("Start"));
                    e.setEventEnd(rs.getDate("End"));
                    try { e.setSchedule(Schedule.getScheduleByID(rs.getInt("Schedule"))); } catch (ScheduleNotFoundException ex) { }
                    e.setIsNew(false);

                    Event.fixLastID();
                    
                    String getGuestRelation = "SELECT Person FROM invites WHERE Event=?";
                    PreparedStatement st = connection.prepareStatement(getGuestRelation);
                    st.setInt(1, rs.getInt("ID"));

                    ResultSet r = st.executeQuery();

                    while(r.next()) {
                        try { e.insertNewGuest(Person.getPersonByCPF(r.getString("Person"))); } catch (PersonNotFoundException ex) { }
                    }

                    list.add(e);
                }
                break;
        }
        
        if (list.isEmpty())
            throw new NotFoundException();
        else
            return list;
        
    }
    
    /**
     * Gets a list of events by a guest.
     * @param p
     * @return
     * @throws SQLException 
     */
    public static ArrayList<Event> getData(Person p) throws SQLException, NotFoundException {
        
        ArrayList<Event> list = new ArrayList<>();
        
        String defaulQuery =    "SELECT events.ID,events.Name,events.Description,events.Start,events.End,events.Schedule FROM invites " + 
                                "INNER JOIN events ON ID = invites.Event WHERE Person=?;";
        
        PreparedStatement stmt = connection.prepareStatement(defaulQuery);
        
        stmt.setString(1, p.getCPF());
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            Event e = new Event();

            e.setiD(rs.getByte("ID"));
            e.setName(rs.getString("Name"));
            e.setDescription(rs.getString("Description"));
            e.setEventStart(rs.getDate("Start"));
            e.setEventEnd(rs.getDate("End"));
            try { e.setSchedule(Schedule.getScheduleByID(rs.getInt("Schedule"))); } catch (ScheduleNotFoundException ex) { }
            e.setIsNew(false);
            
            Event.fixLastID();
            
            String getGuestRelation = "SELECT Person FROM invites WHERE Event=?";
            PreparedStatement st = connection.prepareStatement(getGuestRelation);
            st.setInt(1, rs.getInt("ID"));
            
            ResultSet r = st.executeQuery();
            
            while(r.next()) {
                try { e.insertNewGuest(Person.getPersonByCPF(r.getString("Person"))); } catch (PersonNotFoundException ex) { }
            }
            
            list.add(e);
            
        }
        
        if (list.isEmpty())
            throw new NotFoundException();
        else
            return list;
        
    }
    
    /**
     * Saves an object Schedule in the DB.
     * @param s 
     */
    public static void save(Schedule s) {
        
        String query;
        PreparedStatement stmt;
        
        if ( s.isNew() )
            query = "INSERT INTO schedules (ID,Name,Description) VALUES(?,?,?)";
        else
            query = "UPDATE schedules SET Name=?, Description=? WHERE ID=?";
        
        try {
            
            stmt = connection.prepareStatement(query);
            
            if ( s.isNew() ) {
                stmt.setInt(1, s.getiD());
                stmt.setString(2, s.getName());
                stmt.setString(3, s.getDescription());
                s.setIsNew(false);
            }
            else {
                stmt.setString(1, s.getName());
                stmt.setString(2, s.getDescription());
                stmt.setInt(3, s.getiD());
            }
            
            DAO.runStatement(stmt);
            
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Saves an object Person in the DB.
     * @param p 
     */
    public static void save(Person p) {
        
        String query;
        
        if ( p.isNew() )
            query = "INSERT INTO people VALUES(?,?,?,?);";
        else
            query = "UPDATE people SET Name=?,Address=?,BirthDay=? WHERE CPF=?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            
            if( p.isNew() ) {
                stmt.setString(1, p.getCPF());
                stmt.setString(2, p.getName());
                stmt.setString(3, p.getAddress());
                stmt.setDate(4, p.getBirthDate());
                p.setIsNew(false);
            }
            else {
                stmt.setString(1, p.getName());
                stmt.setString(2, p.getAddress());
                stmt.setDate(3, p.getBirthDate());
                stmt.setString(4, p.getCPF());
            }
            
            DAO.runStatement(stmt);
            
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Saves an object Event in the DB.
     * @param e 
     */
    public static void save(Event e) {
        
        String addEvent = "INSERT INTO events VALUES(?,?,?,?,?,?)";
        String addGuest = "INSERT INTO invites VALUES(?,?)";
        
        try {
            
            if( !e.isNew() ) 
                DAO.remove(e);
            
            PreparedStatement stmt = connection.prepareStatement(addEvent);
            
            stmt.setInt(1, e.getiD());
            stmt.setString(2, e.getName());
            stmt.setString(3, e.getDescription());
            stmt.setDate(4, e.getEventStart());
            stmt.setDate(5, e.getEventEnd());
            stmt.setInt(6, e.getSchedule().getiD());
            
            DAO.runStatement(stmt);
            
            if (e.getGuestList().size() > 0) {
                for ( Person p : e.getGuestList() ) {
                    PreparedStatement stmt2 = connection.prepareStatement(addGuest);
                    stmt2.setInt(1, e.getiD());
                    stmt2.setString(2, p.getCPF());
                    
                    DAO.runStatement(stmt2);
                }
            }
            
            e.setIsNew(false);
            
        }
        catch(SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Generic method to run a runStatement statement.
     * @param stmt
     * @throws SQLException 
     */
    public static void runStatement(PreparedStatement stmt) throws SQLException {
        stmt.execute();
        stmt.close();
    }
    
    /**
     * Removes a Schedule from th e DB.
     * @param s
     * @throws SQLException 
     */
    public static void remove(Schedule s) throws SQLException, EventNotFoundException {
        
        String removeSchedule = "DELETE FROM schedules WHERE ID=?";
        PreparedStatement stmt1 = connection.prepareStatement(removeSchedule);
        stmt1.setInt(1, s.getiD());
        
        for(Event e : Event.getEventBySchedule(s))
            e.remove();

        DAO.runStatement(stmt1);

    }
    
    /**
     * Removes a Person from the DB.
     * @param p
     * @throws SQLException 
     */
    public static void remove(Person p) throws SQLException {
        
        String removePerson = "DELETE FROM people WHERE CPF=?";
        String removePersonFromEvents = "DELETE FROM invites WHERE Person=?";
        
        PreparedStatement stmt1 = connection.prepareStatement(removePerson);
        PreparedStatement stmt2 = connection.prepareCall(removePersonFromEvents);
        
        stmt1.setString(1, p.getCPF());
        stmt2.setString(1, p.getCPF());
        
        DAO.runStatement(stmt2);
        DAO.runStatement(stmt1);
        
    }
    
    /**
     * Removes a Event from the DB.
     * @param e
     * @throws SQLException 
     */
    public static void remove(Event e) throws SQLException {
        
        String removeEvent = "DELETE FROM events WHERE ID=?";
        String removeGuestRelation = "DELETE FROM invites WHERE Event=?";
        
        PreparedStatement stmt1 = connection.prepareStatement(removeEvent);
        PreparedStatement stmt2 = connection.prepareStatement(removeGuestRelation);
        
        stmt2.setInt(1, e.getiD());
        stmt1.setInt(1, e.getiD());
        
        DAO.runStatement(stmt2);
        DAO.runStatement(stmt1);
        
    }
    
}
