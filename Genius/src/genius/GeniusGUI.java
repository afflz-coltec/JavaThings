/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genius;

import genius.game.Genius;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GeniusGUI extends Frame {

    public static Object staff = new Object();
    
    static int WIDTH = 502;
    static int HEIGHT = 502;
    static int TILE_SIZE = 200;
    static int OFFSET = 50;
    
    private Genius genius;
    private ImageManager im;
    private TileBlinker tb;
    
    private Thread blinkerThread;
    
    private boolean isBlinking = false;
    
    public static boolean isGameOver = false;
    
    public GeniusGUI() throws IOException {
        super("Genius");
        im = new ImageManager();
        initComponents();
        
        genius = new Genius();
        tb = new TileBlinker(genius,im);
        
        blinkerThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                
                int[] sequence = genius.getSequence();
                
                while(true) {
                    
                    isBlinking = true;
                    
                    paintLevel((Graphics2D)getGraphics());
                    
                    for( int i=0; i<genius.getLevel(); i++ ) {
                        try {
                            tb.blinkImage(sequence[i], (Graphics2D)getGraphics());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GeniusGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    isBlinking = false;
                    
                    try {
                        synchronized(staff) {
                            staff.wait();
                        }
                    } catch ( InterruptedException ex ) {
                        Logger.getLogger(GeniusGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
            
        });
        
        blinkerThread.start();
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (!isBlinking) {
                    for (Tile[] row : tb.tiles) {
                        for (Tile col : row) {
                            if (col.isInside(e.getX(), e.getY())) {
                                try {
                                    tb.blinkImage(col.getIndex(), (Graphics2D) getGraphics());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(GeniusGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (!genius.checkSequence(col.getIndex())) {
                                    JOptionPane.showMessageDialog(null, "Game Over! You have scored " + genius.getScore() + " points!\nStarting new game...", "Game Over", JOptionPane.WARNING_MESSAGE);
                                    genius = new Genius();
                                    synchronized (staff) {
                                        staff.notify();
                                    }
                                }
                            }
                        }
                    }
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
    
    private void initComponents() {
        
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setBackground(Color.WHITE);
        setVisible(true);
        
    }
    
    public void paintGrid(Graphics2D g) {
        g.setStroke(new BasicStroke(2.0f));
        g.draw(new Line2D.Double(WIDTH/2, OFFSET, WIDTH/2, HEIGHT-OFFSET));
        g.draw(new Line2D.Double(OFFSET, HEIGHT/2, WIDTH-OFFSET, HEIGHT/2));
    }
    
    public void paintLevel(Graphics2D g) {
        g.clearRect(OFFSET, HEIGHT-OFFSET, 200, 100);
        g.drawString("Level: " + genius.getLevel(), OFFSET, HEIGHT-OFFSET + 30);
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        
        paintGrid(g2d);
        
    }
    
    public static void main(String[] args) {
        
        try {
            new GeniusGUI();
        } catch (IOException ex) {
            Logger.getLogger(GeniusGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
