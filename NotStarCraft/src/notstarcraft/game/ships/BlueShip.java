/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class BlueShip extends Ship {

    static {
        
        try {
            blueShip = new Image("res/sprites/ship1.png");
        } catch (SlickException ex) {
            Logger.getLogger(BlueShip.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static Image blueShip;
    private static final float speed = 1.0f;
    
    public BlueShip(float centerX, float centerY) {
        super(centerX, centerY);
    }

    @Override
    protected Image getOriginalImage() {
        return blueShip.copy();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        
    }

    @Override
    public float getSpeed() {
        return speed;
    }

}
