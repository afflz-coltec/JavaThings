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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import notstarcraft.dataTile.SignInDAO;
import notstarcraft.exceptions.EmptyFieldException;
import notstarcraft.exceptions.FailedLoginException;
import notstarcraft.game.player.Player;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SignIn extends JFrame {

    private static final int WINDOW_WIDTH = 240;
    private static final int WINDOW_HEIGHT = 280;

    public SignIn() {

        initComponents();

    }

    private void initComponents() {

        panel =         new JPanel();
        title =         new JLabel();
        userNameLabel = new JLabel();
        passWordLabel = new JLabel();
        signUpLabel =   new JLabel();
        userName =      new JTextField();
        passWord =      new JPasswordField();
        signInButton =  new JButton();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Not Star Craft! v0.1");
        setLocationRelativeTo(null);
        setResizable(false);

        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        title.          setText("Log In");
        userNameLabel.  setText("Username:");
        passWordLabel.  setText("Password:");
        signUpLabel.    setText("<HTML><A HREF='' STYLE='color: black;'>Create a new account</A></HTML>");

        title.          setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.  setHorizontalAlignment(SwingConstants.LEFT);
        passWordLabel.  setHorizontalAlignment(SwingConstants.LEFT);
        signUpLabel.    setHorizontalAlignment(SwingConstants.RIGHT);

        title.          setFont(new Font("Arial", Font.BOLD, 18));
        userNameLabel.  setFont(new Font("Arial", Font.PLAIN, 14));
        passWordLabel.  setFont(new Font("Arial", Font.PLAIN, 14));
        signUpLabel.    setFont(new Font("Arial", Font.PLAIN, 12));
        userName.       setFont(new Font("Arial",Font.PLAIN,12));
        passWord.       setFont(new Font("Arial",Font.PLAIN,12));

        signInButton.setText("Sign In");
        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.setSize(90, 30);
        signInButton.setBackground(new Color(204, 198, 196));

        add(panel);

        panel.add(title);
        panel.add(userNameLabel);
        panel.add(passWordLabel);
        panel.add(signUpLabel);
        panel.add(userName);
        panel.add(passWord);
        panel.add(signInButton);

        title.          setBounds(10, 10, 210, 30);
        userNameLabel.  setBounds(10, 45, 210, 25);
        userName.       setBounds(10, 70, 210, 25);
        passWordLabel.  setBounds(10, 105, 210, 25);
        passWord.       setBounds(10, 130, 210, 25);
        signInButton.   setBounds(75, 180, 90, 30);
        signUpLabel.    setBounds(10, 220, 210, 25);

        passWord.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    signIn();
                }
            }
            
        });
        
        signInButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                signIn();
            }

        });

        signUpLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getX() >= 90 && e.getX() <= 209 && e.getY() >= 8 && e.getY() <= 20) {
                        dispose();
                        new SignUp().setVisible(true);
                    }
                }
            }

        });

    }

    private void signIn() {

        String user = userName.getText();
        String password = passWord.getText();

        try {

            if (user.equals("") || password.equals(""))
                throw new EmptyFieldException();

            if (SignInDAO.isUserValid(user, password)) {
                Player player = SignInDAO.getPlayer(user);
                
                System.out.println(player.getName());
                System.out.println(player.getBirthDate().toString());
                System.out.println(player.getEmail());
                
            }
            else {
                throw new FailedLoginException();
            }

        } catch (EmptyFieldException ex) {
            JOptionPane.showMessageDialog(this, "Fill all the blanks!", "SignIn", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Could not connect to the server!", "SignIn", JOptionPane.ERROR_MESSAGE);
        } catch (FailedLoginException ex) {
            JOptionPane.showMessageDialog(this, "Incorrect user or password!", "SignIn", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {

        final SignIn si = new SignIn();

        try {

            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                si.setVisible(true);
            }

        });

    }

    private JPanel panel;
    private JLabel title;
    private JLabel userNameLabel;
    private JLabel passWordLabel;
    private JLabel signUpLabel;
    private JTextField userName;
    private JTextField passWord;
    private JButton signInButton;

}
