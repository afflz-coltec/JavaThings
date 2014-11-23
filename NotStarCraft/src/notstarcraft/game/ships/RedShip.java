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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class RedShip extends Ship {
    
    static {
        
        try {
            redShip = new Image("res/sprites/ship2.png");
        } catch (SlickException ex) {
            Logger.getLogger(RedShip.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static Image redShip;
    private static final float speed = 1;
    
    private SpriteSheet ship;
    
    public RedShip(float centerX, float centerY) throws SlickException {
        super(centerX,centerY);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        shipImage.drawCentered(this.position.x, this.position.y);
//        if( isSelected ) {
//            g.setLineWidth(2);
//            g.draw(hitBox);
//        }
    }
    
//    @Override
//    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
//        
//        
//        
//    }

    protected Image getOriginalImage() {
        return redShip.copy();
    }
    
    @Override
    public float getSpeed() {
        return speed;
    }
    
}
