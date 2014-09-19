/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexample;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class DAO {

    private Connection connection;

    public DAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void addContact(Contact contact) throws SQLException {

        String sql = "INSERT INTO contatos (nome,email,endereco,dataNascimento) VALUES (?,?,?,?)";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, contact.getName());
        stmt.setString(2, contact.getEmail());
        stmt.setString(3, contact.getAddr());
        stmt.setDate(4, contact.getBirthDate());

        stmt.execute();
        stmt.close();

    }
    
    public ArrayList<Contact> getLista() throws SQLException {
        
        ArrayList<Contact> contacts = new ArrayList<>();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM contatos");
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            
            Contact c = new Contact();
            
            c.setId(rs.getLong("id"));
            c.setName(rs.getString("nome"));
            c.setEmail(rs.getString("email"));
            c.setAddr(rs.getString("endereco"));
            c.setBirthDate(rs.getDate("dataNascimento"));
            
            contacts.add(c);
            
        }
        
        rs.close();
        stmt.close();
        return contacts;
        
    }

}
