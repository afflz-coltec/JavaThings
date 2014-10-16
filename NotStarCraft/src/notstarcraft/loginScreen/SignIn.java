/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notstarcraft.loginScreen;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SignIn extends Frame {
    
    private static final int WINDOW_WIDTH = 320;
    private static final int WINDOW_HEIGHT = 320;

    public SignIn() {
        super("Sign In");
        
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
            
        });
        
        initComponents();
        
    }
    
    private void initComponents() {
        
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setBackground(Color.WHITE);
        setVisible(true);
        
    }
    
    public static void main(String[] args) {
        SignIn si = new SignIn();
    }
    
}
