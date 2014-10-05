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
import java.util.List;
import jdbcexercise.business.Contact;

/**
 *
 * @author strudel
 */
public class ContactDAO {
    
    public enum QueryOptions {
      
        byName ("`primeiro-nome`"),
        byLast ("`ultimo-nome`");
        
        private String option;
        
        private QueryOptions(String option) {
            this.option = option;
        }
        
        public String getOption() {
            return this.option;
        }
        
    };
    
    private static Connection connection = ConnectionFactory.getConnection();
    
    public ContactDAO() { }
    
    public static void addContact(Contact contact) throws SQLException {

        String sql = "INSERT INTO contacts (firstName,lastName,email,address,birthDay,charge,workplace) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, contact.getFirstName());
        stmt.setString(2, contact.getLastName());
        stmt.setString(3, contact.getEmail());
        stmt.setString(4, contact.getAddr());
        stmt.setDate(5, contact.getBirthDate());
        stmt.setString(6, contact.getCharge());
        stmt.setInt(7, contact.getWorkPlace());

        stmt.execute();
        stmt.close();

    }
    
    public static ArrayList getContact(String name, QueryOptions qo) throws SQLException {
        
        ArrayList<Contact> contactList = new ArrayList<>();
        
        String query = "SELECT * FROM contatos WHERE " + qo.getOption() + "=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        
        stmt.setString(1, name);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            Contact c = new Contact();
            
            c.setId(rs.getLong("ID"));
            c.setFirstName(rs.getString("firstName"));
            c.setLastName(rs.getString("lastName"));
            c.setEmail(rs.getString("email"));
            c.setAddr(rs.getString("address"));
            c.setBirthDate(rs.getDate("birthDay"));
            c.setWorkPlace(rs.getInt("workplace"));
            
        }
        
        rs.close();
        stmt.close();
        
        return contactList;
        
    }
    
    public static ArrayList<Contact> getContact(String enterprise) throws SQLException {
        
        ArrayList<Contact> list = new ArrayList<>();
        
        String query = "SELECT * FROM contacts AS contato INNER JOIN workplace AS trampo ON trampo.ID = contato.workplace WHERE trampo.enterpriseName=?;";
        PreparedStatement stmt = connection.prepareCall(query);
        
        stmt.setString(1, enterprise);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            Contact c = new Contact();
            
            c.setId(rs.getLong("ID"));
            c.setFirstName(rs.getString("firstName"));
            c.setLastName(rs.getString("lastName"));
            c.setEmail(rs.getString("email"));
            c.setAddr(rs.getString("address"));
            c.setBirthDate(rs.getDate("birthDay"));
            c.setWorkPlace(rs.getInt("workplace"));
            c.setWorkPlaceName(rs.getString("enterpriseName"));
            
            list.add(c);
            
        }
        
        return list;
        
    }
    
    public static ArrayList<Contact> getLista() throws SQLException {
        
        ArrayList<Contact> contacts = new ArrayList<>();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM contacts");
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            Contact c = new Contact();
            
            c.setId(rs.getLong("ID"));
            c.setFirstName(rs.getString("firstName"));
            c.setLastName(rs.getString("lastName"));
            c.setEmail(rs.getString("email"));
            c.setAddr(rs.getString("address"));
            c.setBirthDate(rs.getDate("birthDay"));
            c.setCharge(rs.getString("charge"));
            c.setWorkPlace(rs.getInt("workplace"));
            
            contacts.add(c);
            
        }
        
        rs.close();
        stmt.close();
        return contacts;
        
    }
    
    public static void removeContact(Contact contact) throws SQLException {
        
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM contacts WHERE ID=?");
        stmt.setLong(1, contact.getId());
        
        stmt.execute();
        
    }
    
}
