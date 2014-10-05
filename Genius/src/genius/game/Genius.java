/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genius.game;

import genius.GeniusGUI;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Genius {

    private static final int MAX_LEVEL = 50;
    
    private int level = 1;
    private int score = 0;
    
    private int[] sequence;
    
    private int playerSequenceIndex = 0;
    
    public Genius() {
        sequence = new int[50];
        generateSequence();
    }
    
    public void generateSequence() {
        
        for(int i=0; i<MAX_LEVEL; i++)
            sequence[i] = new Random().nextInt(4);
        
    }
    
    public void increaseScore() {
        score += 10;
    }
    
    public void levelUp() {
        
        level++;
        playerSequenceIndex = 0;
        
        synchronized(GeniusGUI.staff) {
            GeniusGUI.staff.notify();
        }
        
    }
    
    public boolean checkSequence(int tileIndex) {
        
        if( sequence[playerSequenceIndex] == tileIndex ) {
            
            increaseScore();
            playerSequenceIndex++;
            
            if(level == playerSequenceIndex) {
                levelUp();
            }
            
            return true;
        }
        else {
            return false;
        }
        
    }
    
    public int[] getSequence() {
        return this.sequence;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getScore() {
        return this.score;
    }
    
}
