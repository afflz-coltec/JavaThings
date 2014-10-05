/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbcexercise.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.FailedLoginException;
import javax.swing.JOptionPane;
import jdbcexercise.datatile.ConnectionFactory;
import jdbcexercise.datatile.ContactDAO;
import jdbcexercise.datatile.WorkPlaceDAO;
import jdbcexercise.exceptions.FailedToQueryException;

/**
 *
 * @author Pedro
 */
public class ContactManager implements Runnable {
    
    private ArrayList<Contact> contactList;
    
    public ContactManager() { 
        this.contactList = new ArrayList<>();
    }
    
    public void addNewContact(Contact c) throws FailedToQueryException {
        
        try {
            ContactDAO.addContact(c);
        } catch (SQLException ex) {
            throw new FailedToQueryException();
        }
        
        synchronized(this) {
            notify();
        }
        
    }
    
    public void removeContact(Contact c) throws FailedToQueryException {
        
        try {
            ContactDAO.removeContact(c);
        } catch (SQLException ex) {
            throw new FailedToQueryException();
        }
        
        synchronized(this) {
            notify();
        }
        
    }
    
    public ArrayList getUpdatedList() {
        return this.contactList;
    }
    
    public ArrayList getEmployees(String enterprise) throws FailedToQueryException {
        try {
            return ContactDAO.getContact(enterprise);
        } catch (SQLException ex) {
            throw new FailedToQueryException();
        }
    }

    @Override
    public void run() {
        
        while(true) {
            
            try {
                this.contactList = ContactDAO.getLista();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Connection to DB Server got down. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            
            synchronized(this) {
                notify();
            }
            
            try {
                synchronized(this) {
                    wait();
                }
            }
            catch(InterruptedException ex) {
                Logger.getLogger(ContactManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
}
