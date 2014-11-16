/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import notstarcraft.utils.Line;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public abstract class Ship {

    protected Image shipImage;
    
    protected Image shipUp;
    protected Image shipDown;
    protected Image shipRight;
    protected Image shipLeft;
    protected Image shipUpRight;
    protected Image shipUpLeft;
    protected Image shipDownRight;
    protected Image shipDownLeft;
    
    protected float centerX;
    protected float centerY;
    
    protected float movingToX;
    protected float movingToY;
    
    protected float width;
    protected float height;
    
    protected Line widthLine;
    protected Line heightLine;
    
    protected float currentAngle;
    protected float angleToRotate;
    
    protected Rectangle selectionRect;
    
    protected boolean isSelected = true;
    protected boolean isTurning = false;
    protected boolean isMoving = false;
    protected boolean isXMoving = false;
    protected boolean isYMoving = false;
    protected boolean isXMovingRight = false;
    protected boolean isXMovingLeft = false;
    protected boolean isYMovingUp = false;
    protected boolean isYMovingDown = false;

    protected Ship(float centerX, float centerY, Image[] shipImages) {
        
        this.centerX = centerX;
        this.centerY = centerY;
        
        shipUp          = shipImages[0];
        shipDown        = shipImages[1];
        shipRight       = shipImages[2];
        shipLeft        = shipImages[3];
        shipUpRight     = shipImages[4];
        shipUpLeft      = shipImages[5];
        shipDownRight   = shipImages[6];
        shipDownLeft    = shipImages[7];
        
        this.shipImage = shipUp;
        
        this.width = this.shipImage.getWidth();
        this.height = this.shipImage.getHeight();
        
        this.widthLine = new Line(centerX, centerY, centerX+width/2, centerY);
        
    }
    
    public void setCenterX(float x) {
        this.centerX = x;
    }
    
    public void setCenterY(float y) {
        this.centerY = y;
    }
    
    public void moveShip(int delta) {
        
        if( movingToX > centerX )
            centerX += delta/10;
         
        if( movingToX < centerY )
            centerX -= delta/10;
        
        if( movingToY < centerY )
            centerY -= delta/10;
        
        if( movingToY > centerY )
            centerY += delta/10;
        
        if( !isXMoving && !isYMoving ) {
            isMoving = false;
        }
        
    }
    
    public void moveTo(int posX, int posY) {
        
        this.movingToX = posX;
        this.movingToY = posY;
        
        this.isXMoving = true;
        this.isYMoving = true;
        this.isMoving = true;
        
    }

    public float getCenterX() {
        return this.centerX;
    }
    
    public float getCenterY() {
        return this.centerY;
    }
    
    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
    
    public abstract void render(GameContainer container, Graphics g) throws SlickException;
    
    public abstract void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException;
    
    public abstract float getSpeed();
    
    public abstract void rotate(int x, int y);
    
}
