/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.BasicStroke;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TicTacToe extends Frame{

    private static final int OFFSET = 50;
    private static final int D_1 = 200;
    private static final int D_2 = 400;
    
    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TicTacToe();
    }
    
    public TicTacToe() {
        super("Tic Tac Toe");
        setSize(600, 600);
        setResizable(false);
        setVisible(true);
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
    }
    
    public void handleClick() {
        
        
        
    }
    
    public void updateScreen(Graphics2D g2d) {
        
        g2d.setStroke(new BasicStroke(3.0f));
        
        g2d.draw(new Line2D.Double(D_1, OFFSET, D_1, HEIGHT-OFFSET));
        g2d.draw(new Line2D.Double(D_2, OFFSET, D_2, HEIGHT-OFFSET));
        g2d.draw(new Line2D.Double(OFFSET, D_1, WIDTH-OFFSET, D_1));
        g2d.draw(new Line2D.Double(OFFSET, D_2, WIDTH-OFFSET, D_2));
        
    }
    
    public void paint(Graphics g) {
        updateScreen((Graphics2D)g);
    }
    
}
