/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import notstarcraft.utils.Line;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ChiefShip extends Ship {
    
    private static final float speed = 1;
    
    private SpriteSheet ship;
    
    public ChiefShip(float centerX, float centerY) throws SlickException {
        super(centerX,centerY, new Image[]{
            
                                            new Image("res/sprites/masterShipUp.png"),
                                            new Image("res/sprites/masterShipDown.png"),
                                            new Image("res/sprites/masterShipRight.png"),
                                            new Image("res/sprites/masterShipLeft.png"),
                                            new Image("res/sprites/masterShipUpRight.png"),
                                            new Image("res/sprites/masterShipUpLeft.png"),
                                            new Image("res/sprites/masterShipDownRight.png"),
                                            new Image("res/sprites/masterShipDownLeft.png")
            
                                          });
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        shipImage.drawCentered(centerX, centerY);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        if( isMoving ) {
            moveShip(delta);
        }
        
    }

    @Override
    public float getSpeed() {
        return speed;
    }
    
    @Override
    public void rotate(int x, int y) {
//        Line newPath = new Line(this.centerX, this.centerY, x, y);
//        
//        float rad = widthLine.calculateAngle(newPath);
//        
//        if( rad < 0 )
//            angleToRotate = (float) (90.0f + Math.toDegrees(rad));
//        else if( rad > 0 )
//            angleToRotate = (float) Math.toDegrees(rad);
//        
//        widthLine = newPath;
        shipImage.rotate(90);
    }
    
    
}
