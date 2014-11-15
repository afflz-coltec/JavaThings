/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import java.awt.geom.Rectangle2D;
import notstarcraft.utils.Line;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
        super(centerX,centerY,new SpriteSheet("res/sprites/masterShip1.png", 120, 140).getSubImage(0, 0, 120, 140));
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        shipImage.drawCentered(this.centerX, this.centerY);
        g.setColor(Color.green);
        g.setLineWidth(2);
        g.draw(selectionRect);
        g.drawString("X: " + centerX + "\nY: " + centerY, 1200, 200);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        if( angleToRotate > 0 ) {
            isTurning = true;
            shipImage.rotate(1);
            angleToRotate -= 1;
        }
        else if( angleToRotate < 0) {
            isTurning = false;
            isMoving = true;
        }
        
        if( isMoving && !isTurning ) {
            moveShip(delta);
        }
        
    }

    @Override
    public float getSpeed() {
        return speed;
    }
    
    @Override
    public void rotate(int x, int y) {
        Line newPath = new Line(this.centerX, this.centerY, x, y);
        
        float rad = widthLine.calculateAngle(newPath);
        
        if( rad < 0 )
            angleToRotate = (float) (90.0f + Math.toDegrees(rad));
        else if( rad > 0 )
            angleToRotate = (float) Math.toDegrees(rad);
        
        widthLine = newPath;
    }
    
    
}
