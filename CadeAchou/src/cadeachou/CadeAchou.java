/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadeachou;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class CadeAchou extends Frame {

    static Object staff1 = new Object();
    static Object staff2 = new Object();

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private ImageLoader il;
    private TileBlinker tb;

    static int MAX_LEVEL = 59;
    static boolean isGameOver = false;
    static int level = 0;
    static int playerScore = 0;

    private int round = 0;

    boolean isBlinking = false;

    private Thread gameThread;
    private Thread counterThread;
    private Thread gameStateCheckerThread;

    public CadeAchou() throws HeadlessException, IOException {
        super("Cade?Achou!");

        il = new ImageLoader();
        tb = new TileBlinker(il);

        initComponents();

        tb.genNewTile();

        gameThread = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    try {

                        isBlinking = true;

                        tb.genNewTile();
                        tb.blinkTile(round, (Graphics2D) getGraphics());
                        round++;
                        if(round == 5) {
                            level++;
                            round = 0;
                        }

                        isBlinking = false;

                        Counter c = new Counter();
                        counterThread = new Thread(c);
                        counterThread.start();

                        synchronized (staff1) {
                            staff1.wait();
                        }

                    } catch (InterruptedException ex) {
                        Logger.getLogger(CadeAchou.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (!isBlinking) {

                    counterThread.interrupt();

                    for (Tile t : tb.getTileList()) {
                        if (t.isInside(e.getX(), e.getY())) {
                            synchronized (staff1) {
                                staff1.notify();
                            }
                        } else {
                            try {
                                tb.blinkTile(round-1, (Graphics2D)getGraphics());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(CadeAchou.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }

            }

        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }

        });

    }

    public void startGame() {
        JOptionPane.showMessageDialog(this, "Click OK to start the game!", "Cade?Achou!", JOptionPane.INFORMATION_MESSAGE);
        gameThread.start();
    }

    private void initComponents() {

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setBackground(Color.WHITE);
        setVisible(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            CadeAchou ca = new CadeAchou();
            ca.startGame();
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(CadeAchou.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
