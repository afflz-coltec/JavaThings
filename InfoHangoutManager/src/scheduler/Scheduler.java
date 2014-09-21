/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scheduler.businesstier.Event;
import scheduler.businesstier.Person;
import scheduler.businesstier.Schedule;
import scheduler.exceptions.EventNotFoundException;
import scheduler.exceptions.PersonNotFoundException;
import scheduler.exceptions.ScheduleNotFoundException;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Scheduler {
    
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        // Insertion and update of data
        insertThingsIntoTheDB();
        updateThingsOnDB();
        
        // Query tests
        testScheduleQuery();
        testPersonQuery();
        testEventQuery();
        
        // Uncomment to delete what have been created.
//        removeEverythingFromDB();
        
    }
    
    static void insertThingsIntoTheDB() {
        
        Schedule s1 = new Schedule("Agenda1", "AgendaTeste");
        Schedule s2 = new Schedule("Agenda2", "AgendaTeste");

        Person p1 = new Person("111.111.111-11", "Pessoa1", "Rua 1", makeDate(1, 1, 2001));
        Person p2 = new Person("222.222.222-22", "Pessoa2", "Rua 2", makeDate(2, 2, 2002));
        Person p3 = new Person("333.333.333-33", "Pessoa3", "Rua 3", makeDate(3, 3, 2003));
    
        Event e1 = new Event("Evento1", "EventoTeste", makeDate(15, 1, 2014), makeDate(16, 2, 2014), s1);
        Event e2 = new Event("Evento2", "EventoTeste", makeDate(15, 3, 2014), makeDate(16, 4, 2014), s1);
        Event e3 = new Event("Evento3", "EventoTeste", makeDate(15, 5, 2014), makeDate(16, 6, 2014), s2);
        Event e4 = new Event("Evento4", "EventoTeste", makeDate(15, 7, 2014), makeDate(16, 8, 2014), s2);
        
        s1.store();
        s2.store();
        
        p1.store();
        p2.store();
        p3.store();
        
        e1.insertNewGuest(p1);
        e1.insertNewGuest(p2);
        
        e2.insertNewGuest(p2);
        e2.insertNewGuest(p3);
        
        e3.insertNewGuest(p1);
        e3.insertNewGuest(p2);
        e3.insertNewGuest(p3);
        
        e4.insertNewGuest(p1);
        e4.insertNewGuest(p3);
        
        e1.store();
        e2.store();
        e3.store();
        e4.store();
        
    }
    
    static void updateThingsOnDB() throws SQLException {
        
        try {
            
            Schedule sc1 = Schedule.getScheduleByID(1);
            Schedule sc2 = Schedule.getScheduleByID(2);
            
            Person ps1 = Person.getPersonByCPF("111.111.111-11");
            Person ps2 = Person.getPersonByCPF("222.222.222-22");
            Person ps3 = Person.getPersonByCPF("333.333.333-33");
            
            Event ev1 = Event.getEventByID(1);
            Event ev2 = Event.getEventByID(2);
            Event ev3 = Event.getEventByID(3);
            Event ev4 = Event.getEventByID(4);
            
            sc1.setName("AgendasGemeas");
            sc2.setName("AgendasGemeas");
            
            ps1.setName("Pedro Otavio");
            ps2.setName("Lasanha");
            ps3.setName("Lasanha");
            
            ev1.setName("Sabado de Sol");
            ev2.setName("Rolezinho da Info");
            ev3.setName("Info no CEU");
            ev4.setName("Info no CEU");
            
            sc1.store();
            sc2.store();
            
            ps1.store();
            ps2.store();
            ps3.store();
            
            ev1.store();
            ev2.store();
            ev3.store();
            ev4.store();
            
        }
        catch (ScheduleNotFoundException ex) {
            System.err.println("Schedule not found!\n");
        } 
        catch (EventNotFoundException ex) {
            System.err.println("Event not found!\n");
        } 
        catch (PersonNotFoundException ex) {
            System.err.println("Person not found!\n");
        }
        
    }
    
    static void testScheduleQuery() {
        
        try {
            
            Schedule s = Schedule.getScheduleByID(1);
            s.print();
            
            System.out.println();
            
            for( Schedule sch : Schedule.getScheduleByName("AgendasGemeas"))
                sch.print();
            
        }
        catch(ScheduleNotFoundException ex) {
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void testPersonQuery() throws SQLException {
        
        try {
            
            Person.getPersonByCPF("111.111.111-11").print();
            
            for(Person p : Person.getPersonByName("Lasanha"))
                p.print();
            
            for(Person p : Person.getPersonByBirthDay(makeDate(31,12,2000), makeDate(1,1,2003)))
                p.print();
            
        }
        catch (PersonNotFoundException ex) {
            System.err.println("Person not found!\n");
        }
        
    }
    
    static void testEventQuery() throws SQLException {
        
        try {
            
            Event.getEventByID(1).print();
            
            for(Event e : Event.getEventByName("Info no CEU"))
                e.print();
            
            for(Event e : Event.getEventByGuest(Person.getPersonByCPF("222.222.222-22")))
                e.print();
            
        } catch (EventNotFoundException ex) {
            System.err.println("Event not found!\n");
        } catch (ScheduleNotFoundException ex) {
            System.err.println("Schedule not found!\n");
        } catch (PersonNotFoundException ex) {
            System.err.println("Person not found!\n");
        }
        
    }
    
    static void removeEverythingFromDB() throws SQLException {
        
        try {
            
            Schedule s1 = Schedule.getScheduleByID(1);
            Schedule s2 = Schedule.getScheduleByID(2);
            
            Person prs1 = Person.getPersonByCPF("111.111.111-11");
            Person prs2 = Person.getPersonByCPF("222.222.222-22");
            Person prs3 = Person.getPersonByCPF("333.333.333-33");
            
            s1.remove();
            s2.remove();
            
            prs1.remove();
            prs2.remove();
            prs3.remove();
            
        }
        catch (ScheduleNotFoundException ex) {
            System.err.println("Schedule not found!\n");
        } 
        catch (PersonNotFoundException ex) {
            System.err.println("Person not found!\n");
        }
        
    }
    
    public static Date makeDate(int d, int m, int a) {
        return Date.valueOf(String.valueOf(a) + "-" + String.valueOf(m) + "-" + String.valueOf(d));
    }
    
}
