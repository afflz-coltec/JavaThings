/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexercise.gui;

import java.sql.Connection;
import jdbcexercise.datatile.ConnectionFactory;

/**
 *
 * @author strudel
 */
public class FormGUI {
    
    public static void main(String[] args) {
        Connection c = ConnectionFactory.getConnection();
    }
    
}
