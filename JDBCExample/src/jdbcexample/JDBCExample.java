/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexample;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class JDBCExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        DAO dao = new DAO();

//        while(true) {
        Contact c = new Contact();
        Scanner kb = new Scanner(System.in);
        
        String name = "";
        String addr = "";
        String email = "";
        String date = "";
        
        System.out.print("Type ur name: ");
        name = kb.nextLine();
        System.out.print("Type ur address: ");
        addr = kb.nextLine();
        System.out.print("Type ur email: ");
        email = kb.nextLine();
        System.out.print("Type ur birthday(9999-99-99): ");
        date = kb.nextLine();
        
        c.setName(name);
        c.setAddr(addr);
        c.setEmail(email);
        c.setBirthDate(Date.valueOf(date));
        
        dao.addContact(c);

//        }
//        Contact c = new Contact();
//        
//        c.setName("Lasanha");
//        c.setAddr("Rua Lasanha");
//        c.setEmail("lasanha@lasanha.org");
//        c.setBirthDate(Date.valueOf("2004-4-4"));
//        
//        dao.addContact(c);
//        
//        ArrayList<Contact> contacts = dao.getLista();
//        
//        for(Contact con:contacts) {
//            System.out.println(con.getName() + "\n" + con.getAddr() + "\n" + con.getEmail() + "\n" + con.getBirthDate().toString());
//        }
    }
    
}
