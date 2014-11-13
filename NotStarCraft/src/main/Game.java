package main;


import notstarcraft.NotStarCraft;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Game {
    
    static final int WIDTH = 1280;
    static final int HEIGHT = 720;
    
    public Game() {
        
    }

    public static void main(String[] args) {
        
        try {
            
            AppGameContainer notstarcraft = new AppGameContainer(new NotStarCraft("Not Star Craft!",WIDTH,HEIGHT));
            notstarcraft.setDisplayMode(WIDTH, HEIGHT, false);
            notstarcraft.setVSync(true);
            notstarcraft.start();
            
        } catch (SlickException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
