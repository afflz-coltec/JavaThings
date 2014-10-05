/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cadeachou;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Counter implements Runnable {

    @Override
    public void run() {
        
        int count = 0;
       
        while(count != 5) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                return;
            }
            count++;
        }
        
        synchronized(CadeAchou.staff1) {
            CadeAchou.staff1.notify();
        }
        
    }
    
}
