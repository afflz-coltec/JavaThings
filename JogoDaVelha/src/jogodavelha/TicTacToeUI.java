/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogodavelha;

import java.awt.BasicStroke;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jogodavelha.business.TicTacToe;
import jogodavelha.exceptions.InvalidPositionException;

/**
 *
 * @author strudel
 */
public class TicTacToeUI extends Frame {

    private static final int OFFSET = 50;
    
    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;
    
    private static final int FIELD_WIDTH = (WIDTH-2*OFFSET)/3;
    private static final int FIELD_HEIGHT = (HEIGHT-2*OFFSET)/3;
    
    private TicTacToe game;
    
    private Field[][] fields;
    
    public TicTacToeUI() {
        
        super("Jogo da Velha");
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setResizable(false);
        
        this.game = new TicTacToe();
        Field.FIELD_WIDTH = FIELD_WIDTH;
        Field.FIELD_HEIGHT = FIELD_HEIGHT;
        genNewFields();
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
                System.out.println("x: " + e.getX() + " y: " + e.getY());
                try {
                    HandleClick(e.getX(), e.getY());
                } catch (InvalidPositionException ex) {
                    return;
                }
               
            }
            
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
    }
    
    public final void genNewFields() {
        
        Field[][] newFields = new Field[3][3];
        
        for(int row=0; row < 3; row++)
            for(int col=0; col < 3; col++)
                newFields[row][col] = new Field(OFFSET+FIELD_WIDTH*col, OFFSET+FIELD_HEIGHT*row,row,col);
        
        this.fields = newFields;
        
    }
    
    public void HandleClick(int x, int y) throws InvalidPositionException {
        
        for(Field[] row : this.fields) {
            for(Field col : row) {
                if(col.isInside(x, y)) {
                    
                    boolean hasWinner = game.play(col.getX(), col.getY());
                    repaint();
                    if(hasWinner) {
                        JOptionPane.showMessageDialog(this, "The " + game.getWinner() + " Player won the game!!", "And the winner is...", JOptionPane.INFORMATION_MESSAGE);
                        this.game = new TicTacToe();
                        repaint();
                    }
                    else if(game.isGameOver()){
                        JOptionPane.showMessageDialog(this, "Game Over!!", "Game Over!!", JOptionPane.INFORMATION_MESSAGE);
                        this.game = new TicTacToe();
                        repaint();
                    }
                    
                }
            }   
        }
        
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setStroke(new BasicStroke(4.0f));
        
        g2d.draw(new Line2D.Double(OFFSET+FIELD_WIDTH, OFFSET, OFFSET+FIELD_WIDTH, HEIGHT-OFFSET));
        g2d.draw(new Line2D.Double(OFFSET+FIELD_WIDTH*2, OFFSET, OFFSET+FIELD_WIDTH*2, HEIGHT-OFFSET));
        g2d.draw(new Line2D.Double(OFFSET, OFFSET+FIELD_HEIGHT, WIDTH-OFFSET, OFFSET+FIELD_HEIGHT));
        g2d.draw(new Line2D.Double(OFFSET, OFFSET+FIELD_HEIGHT*2, WIDTH-OFFSET, OFFSET+FIELD_HEIGHT*2));
     
        String[][] board = game.getBoard();
        
        for(int row=0; row < 3; row++)
            for(int col=0; col < 3; col++) {
                if( board[row][col].equalsIgnoreCase(TicTacToe.PLAYER_1) ) {
                    g2d.draw(new Line2D.Double(fields[row][col].getPosX()+20, fields[row][col].getPosY()+20, fields[row][col].getPosX()+Field.FIELD_WIDTH-20, fields[row][col].getPosY()+Field.FIELD_HEIGHT-20));
                    g2d.draw(new Line2D.Double(fields[row][col].getPosX()+Field.FIELD_WIDTH-20, fields[row][col].getPosY()+20, fields[row][col].getPosX()+20, fields[row][col].getPosY()+Field.FIELD_HEIGHT-20));
                }
                else if( board[row][col].equalsIgnoreCase(TicTacToe.PLAYER_2) ) {
                    g2d.draw(new Ellipse2D.Double(fields[row][col].getPosX()+20, fields[row][col].getPosY()+20, FIELD_WIDTH-40, FIELD_HEIGHT-40));
                }
                else {
                    
                }
            }
        
    }
    
}
