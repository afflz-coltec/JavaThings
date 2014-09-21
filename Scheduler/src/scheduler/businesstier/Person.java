/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler.businesstier;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scheduler.datatier.DAO;
import scheduler.datatier.DAO.DataTypes;
import scheduler.datatier.DAO.QueryType;
import scheduler.exceptions.NotFoundException;
import scheduler.exceptions.PersonNotFoundException;

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
    private boolean isNew;

    /**
     * Constructor to instanciate a Person object.
     * @param CPF
     * @param name
     * @param address
     * @param birthDate
     * @param isNew 
     */
    public Person(String CPF, String name, String address, Date birthDate, boolean isNew) {
        this.CPF = CPF;
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
        this.isNew = isNew;
    }
    
    public Person(String CPF, String name, String address, Date birthDate) {
        this(CPF,name,address,birthDate,true);
    }
    
    public Person() {
        
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    public String getCPF() {
        return this.CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public static Person getPersonByCPF(String cpf) throws SQLException, PersonNotFoundException {
        try {
            return (Person) DAO.getData(DataTypes.isPerson, QueryType.queryByCPF, cpf).remove(0);
        } catch (NotFoundException ex) {
            throw new PersonNotFoundException();
        }
    }
    
    public static ArrayList<Person> getPersonByName(String name) throws SQLException, PersonNotFoundException {
        try {
            return DAO.getData(DataTypes.isPerson,QueryType.queryByName,name);
        } catch (NotFoundException ex) {
            throw new PersonNotFoundException();
        }
    }
    
    public static ArrayList<Person> getPersonByBirthDay(Date start, Date end) throws SQLException, PersonNotFoundException {
        try {
            return DAO.getData(DataTypes.isPerson, QueryType.queryByBirthDay, start, end);
        } catch (NotFoundException ex) {
            throw new PersonNotFoundException();
        }
    }
    
    public void print() {
        System.out.println( "\nName: " + this.name + 
                            "\nCPF: " + this.CPF + 
                            "\nAddress: " + this.address + 
                            "\nBirthDay: " + this.birthDate.toString());
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
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
