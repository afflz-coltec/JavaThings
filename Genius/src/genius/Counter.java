/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genius;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Counter implements Runnable {

    public void run() {
        
        int count = 0;
        
        while(count < 5) {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Counter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            count++;
            
        }
        GeniusGUI.isGameOver = true;
    }
    
}
