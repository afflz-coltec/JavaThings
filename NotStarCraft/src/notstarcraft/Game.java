package notstarcraft;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pedro
 */
public class Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    /**
     * Instantiate a new Game.
     * @throws SlickException 
     */
    public Game() throws SlickException {
        AppGameContainer notstarcraft = new AppGameContainer(new NotStarCraft("Not Star Craft!"));
        notstarcraft.setDisplayMode(WIDTH, HEIGHT, false); // Set the size and not fullscreen
        notstarcraft.setTargetFrameRate(60); // Set the frame rate to 60FPS
        notstarcraft.setVSync(true); // Turn vSync on
        notstarcraft.start(); // Start the game
    }

}
