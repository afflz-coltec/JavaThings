/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.businesstier;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
    private ArrayList<Person> invitedList;

    public Event(String name, String description, Date eventStart, Date eventEnd, Schedule scdl, ArrayList<Person> invitedList) {
        this.name = name;
        this.description = description;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.schedule = scdl;
        this.invitedList = invitedList;
    }
    
    static {
        
    }

    @Override
    public void store() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
