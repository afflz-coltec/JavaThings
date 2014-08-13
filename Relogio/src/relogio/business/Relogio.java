/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package relogio.business;

/**
 *
 * @author strudel
 */
public class Relogio implements Runnable {

    private static final int UM_SEGUNDO = 1000;
    
    private javax.swing.JTextPane CountLog;
    
    private int count = 0;
    
    private int h = 0;
    private int m = 0;
    private int s = 0;
    
    public Relogio() {
        
    }
    
    public String getTime() {
        
        return String.format("%02d:%02d:%02d", this.h, this.m, this.s );
        
    }
    
    public void setStartTime(int min, int sec) {
        this.count = min*60 + sec;
    }
    
    public void restart() {
        this.count = 0;
    }
    
    @Override
    public void run() {
        
        try {

            while ( true ) {

                synchronized(this) {
                    
                    if ( count != 0 && count%3600 == 0 ) {
                        this.h++;
                        count = 0;
                    }

                    this.m = count / 60;
                    this.s = count % 60;
                    
                    notify();

                    System.out.println(String.format("%02d:%02d:%02d", h,m,s));

                    count++;
                    
                }
                Thread.sleep(UM_SEGUNDO);

            }

        }
        catch ( InterruptedException e ) {

        }
        
    }
    
    
    
}
