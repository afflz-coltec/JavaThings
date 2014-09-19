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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import scheduler.datatier.DAO;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Person implements StoreManagement {
    
    private static final ArrayList<Person> peopleList = new ArrayList<>();
    
    private String CPF;
    private String name;
    private String address;
    private Date birthDate;

    public Person(String CPF, String name, String address, Date birthDate) {
        this.CPF = CPF;
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public static Person getPersonByCPF(String cpf) throws SQLException {
        
        Person prs = null;
        
        String getPersonByCPFQuery = "SELECT CPF,Name,Address,BirthDay FROM person WHERE cpf=?";
        
        PreparedStatement stmt = DAO.getConnection().prepareStatement(getPersonByCPFQuery);
        
        stmt.setString(1, cpf);
        
        ResultSet rs = DAO.getData(stmt);
        
        prs = new Person(rs.getString("CPF"), rs.getString("Name"), rs.getString("Address"), rs.getDate("BirthDay"));
        
        return prs;
        
    }
    
    @Override
    public void store() {
        
        String addPerson = "INSERT INTO people VALUES(?,?,?,?);";
        
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement(addPerson);
            
            stmt.setString(1, this.CPF);
            stmt.setString(2, this.name);
            stmt.setString(3, this.address);
            stmt.setDate(4, this.birthDate);
            
            DAO.save(stmt);
            
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void remove() {
        
        String removePerson = "DELETE prs,evt FROM people AS prs, events AS evt WHERE prs.ID=evt.Schedule AND prs.ID=?";
        
    }

}
