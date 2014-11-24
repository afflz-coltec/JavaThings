/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.loginScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import notstarcraft.dataTile.SignUpDAO;
import notstarcraft.exceptions.EmptyFieldException;
import notstarcraft.exceptions.UserNotAvailableException;
import notstarcraft.exceptions.WrongPasswordException;

/**
 * 
 * @author Pedro
 */
public class SignUp extends JFrame {
    
    private static final int WINDOW_WIDTH = 320;
    private static final int WINDOW_HEIGHT = 520;
    
    private static final String[] GENDERS = {"Male", "Female"};
    
    public SignUp() {
        initComponents();
    }
    
    private void initComponents() {
        
        panel =                 new JPanel();
        titleLabel =            new JLabel();
        nameLabel =             new JLabel();
        usernameLabel =         new JLabel();
        passwordLabel =         new JLabel();
        confirmPasswordLabel =  new JLabel();
        emailLabel =            new JLabel();
        genderLabel =           new JLabel();
        birthDateLabel =        new JLabel();
        rightSlash1 =           new JLabel();
        rightSlash2 =           new JLabel();
        goSignInLabel =         new JLabel();
        nameField =             new JTextField();
        usernameField =         new JTextField();
        passwordField =         new JPasswordField();
        confirmPasswordField =  new JPasswordField();
        emailField =            new JTextField();
        dayField =              new JTextField();
        monthField =            new JTextField();
        yearField =             new JTextField();
        genderBox =                new JComboBox(GENDERS);
        signUpButton =          new JButton();
        labelFont =             new Font("Arial", Font.PLAIN, 14);
        
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Not Star Craft! v0.1");
        setLocationRelativeTo(null);
        setResizable(false);
        
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        
        titleLabel.             setText("Sign Up!");
        nameLabel.              setText("Name:");
        usernameLabel.          setText("Username:");
        passwordLabel.          setText("Password:");
        confirmPasswordLabel.   setText("Confirm password:");
        emailLabel.             setText("Email:");
        genderLabel.            setText("Gender:");
        birthDateLabel.         setText("Birth Date:");
        signUpButton.           setText("Sign Up");
        rightSlash1.            setText("/");
        rightSlash2.            setText("/");
        goSignInLabel.          setText("<HTML><A HREF='' STYLE='color: black;'>Have an account? Sign In!</A></HTML>");
        
        titleLabel.     setHorizontalAlignment(SwingConstants.CENTER);
        rightSlash1.    setHorizontalAlignment(SwingConstants.CENTER);
        rightSlash2.    setHorizontalAlignment(SwingConstants.CENTER);
        goSignInLabel.  setHorizontalAlignment(SwingConstants.RIGHT);
        
        titleLabel.             setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.              setFont(labelFont);
        usernameLabel.          setFont(labelFont);
        passwordLabel.          setFont(labelFont);
        confirmPasswordLabel.   setFont(labelFont);
        emailLabel.             setFont(labelFont);
        genderLabel.            setFont(labelFont);
        birthDateLabel.         setFont(labelFont);
        dayField.               setFont(labelFont);
        monthField.             setFont(labelFont);
        yearField.              setFont(labelFont);
        signUpButton.           setFont(new Font("Arial", Font.BOLD, 14));
        goSignInLabel.          setFont(new Font("Arial",Font.PLAIN,12));
        
        signUpButton.setSize(90, 30);
        signUpButton.setBackground(new Color(204, 198, 196));
        
        add(panel);
        
        panel.add(titleLabel);
        panel.add(nameLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(confirmPasswordLabel);
        panel.add(emailLabel);
        panel.add(genderLabel);
        panel.add(birthDateLabel);
        panel.add(rightSlash1);
        panel.add(rightSlash2);
        panel.add(goSignInLabel);
        panel.add(nameField);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(confirmPasswordField);
        panel.add(emailField);
        panel.add(genderBox);
        panel.add(dayField);
        panel.add(monthField);
        panel.add(yearField);
        panel.add(signUpButton);
        
        titleLabel.             setBounds(10, 10, 290, 30);
        usernameLabel.          setBounds(10, 45, 290, 25);
        usernameField.          setBounds(10, 70, 290, 25);
        nameLabel.              setBounds(10, 95, 290, 25);
        nameField.              setBounds(10, 120, 290, 25);
        passwordLabel.          setBounds(10, 145, 290, 25);
        passwordField.          setBounds(10, 170, 290, 25);
        confirmPasswordLabel.   setBounds(10, 195, 290, 25);
        confirmPasswordField.   setBounds(10, 220, 290, 25);
        emailLabel.             setBounds(10, 245, 290, 25);
        emailField.             setBounds(10, 270, 290, 25);
        genderLabel.            setBounds(10, 295, 290, 25);
        genderBox.              setBounds(10, 320, 100, 25);
        birthDateLabel.         setBounds(10, 345, 290, 25);
        dayField.               setBounds(10, 370, 25, 25);
        rightSlash1.            setBounds(35, 370, 10, 25);
        monthField.             setBounds(45, 370, 25, 25);
        rightSlash2.            setBounds(70, 370, 10, 25);
        yearField.              setBounds(80, 370, 50, 25);
        signUpButton.           setBounds(115, 420, 90, 30);
        goSignInLabel.          setBounds(10, 460, 290, 25);
        
        signUpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
            
        });
        
        goSignInLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getButton() == MouseEvent.BUTTON1 ) {
                    if (e.getX() >= 148 && e.getX() <= 289 && e.getY() >= 8 && e.getY() <= 20) {
                        dispose();
                        new SignIn().setVisible(true);
                    }
                }
            }
            
        });
        
    }
    
    private void signUp() {
        
        String name =           nameField.getText();
        String userName =       usernameField.getText();
        String passwd =         passwordField.getText();
        String confirmPasswd =  confirmPasswordField.getText();
        String email =          emailField.getText();
        String day =            dayField.getText();
        String month =          monthField.getText();
        String year =           yearField.getText();
        String gender =         (String)genderBox.getSelectedItem();
        
        try {
            
            if( name.equals("") || userName.equals("") || passwd.equals("") || 
                confirmPasswd.equals("") || email.equals("") || day.equals("") || 
                month.equals("") || year.equals("") || gender.equals("") )
                throw new EmptyFieldException();
            
            if( SignUpDAO.isUserAvailable(userName) )
                throw new UserNotAvailableException();
            
            if( !passwd.equals(confirmPasswd) )
                throw new WrongPasswordException();
            
            SignUpDAO.addUser(userName, passwd);
            SignUpDAO.addUserInfo(userName, name, gender, email, Date.valueOf(year+"-"+month+"-"+day));
            JOptionPane.showMessageDialog(this, "User " + userName + " created successfully!", "SignUp", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new SignIn().setVisible(true);
            
        } catch ( EmptyFieldException ex ) {
            
        } catch (SQLException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserNotAvailableException ex) {
            JOptionPane.showMessageDialog(this, "Username not available!", "SignUp", JOptionPane.ERROR_MESSAGE);
        } catch (WrongPasswordException ex) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "SignUp", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
//    public static void main(String[] args) {
//        
//        final SignUp su = new SignUp();
//        
//        try {
//
//            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                su.setVisible(true);
//            }
//
//        });
//        
//    }
    
    private JPanel panel;
    private JLabel titleLabel, nameLabel, usernameLabel, passwordLabel,confirmPasswordLabel, emailLabel, genderLabel, birthDateLabel, rightSlash1, rightSlash2, goSignInLabel;
    private JTextField nameField, usernameField, passwordField, confirmPasswordField, emailField, dayField, monthField, yearField;
    private JButton signUpButton;
    private JComboBox genderBox;
    private Font labelFont;
    
}
