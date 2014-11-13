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
    
    protected float x;
    protected float y;
    
    protected float centerX;
    protected float centerY;
    
    protected float movingToX;
    protected float movingToY;
    
    protected float width;
    protected float height;
    
    protected Line verticalLine;
    protected Line horizontalLine;
    
    protected float angleToRotate;
    
    protected Rectangle selectionRect;
    
    protected boolean isSelected = true;
    protected boolean isTurning = false;
    protected boolean isMoving = false;
    protected boolean isXMoving = false;
    protected boolean isYMoving = false;

    protected Ship(float centerX, float centerY, Image shipImage) {
        
        this.centerX = centerX;
        this.centerY = centerY;
        
        this.shipImage = shipImage;
        this.width = shipImage.getWidth();
        this.height = shipImage.getHeight();
        
        this.verticalLine = new Line(centerX, centerY, centerX, centerY-this.height/2);
         
        this.x = Math.round(Math.cos(Math.atan(verticalLine.getCoefA())) * (-this.shipImage.getWidth()/2) - Math.sin(Math.atan(verticalLine.getCoefA())) * (-this.shipImage.getHeight()/2) ) + centerX;
        this.y = Math.round(Math.sin(Math.atan(verticalLine.getCoefA())) * (-this.shipImage.getWidth()/2) + Math.cos(Math.atan(verticalLine.getCoefA())) * (-this.shipImage.getHeight()/2) ) + centerY;
        
        selectionRect = new Rectangle(0, 0, width, height);
        selectionRect.setCenterX(centerX);
        selectionRect.setCenterY(centerY);
        
    }
    
    public void setCenterX(float x) {
        this.centerX = x;
        this.x = (float) (Math.cos(Math.atan(verticalLine.getCoefA())) * (-shipImage.getWidth()/2) - Math.sin(Math.atan(verticalLine.getCoefA())) * (-shipImage.getHeight()/2) + centerX);
    }
    
    public void setCenterY(float y) {
        this.centerY = y;
        this.y = (float) (Math.sin(Math.atan(verticalLine.getCoefA())) * (-shipImage.getWidth()/2) + Math.cos(Math.atan(verticalLine.getCoefA())) * (-shipImage.getHeight()/2) + centerY);
    }
    
    public void moveShip(int delta) {
        

        if( movingToX > centerX ) {
            float newCenterX = (float) (
                centerX
                + (delta/10)
                * this.getSpeed()
                * Math.cos(Math.atan(verticalLine.getCoefA())
                )
            );
            setCenterX(newCenterX);
            selectionRect.setCenterX(newCenterX);
            
            if( centerX > movingToX  )
                isXMoving = false;
        }
        else if ( movingToX < centerX ) {
            float newCenterX = (float) (
                centerX
                - (delta/10)
                * this.getSpeed()
                * Math.cos(Math.atan(verticalLine.getCoefA())
                )
            );
            setCenterX(newCenterX);
            selectionRect.setCenterX(newCenterX);
            if( centerX < movingToX  )
                isXMoving = false;
        }
        
        if( movingToY > centerY ) {
            float newCenterY = (float) (
                centerY 
                + ((delta/10) 
                * this.getSpeed() 
                * Math.sin(Math.atan(verticalLine.getCoefA())
                ))
            );
            setCenterY(newCenterY);
            selectionRect.setCenterY(newCenterY);
            if( centerY < movingToY  )
                isYMoving = false;
        }
        else if( movingToY < centerY ) {
            float newCenterY = (float) (
                centerY 
                - ((delta/10) 
                * this.getSpeed() 
                * Math.sin(Math.atan(verticalLine.getCoefA())
                ))
            );
            setCenterY(newCenterY);
            selectionRect.setCenterY(newCenterY);
            if( centerY > movingToY  )
                isYMoving = false;
        }
        
        if( !isXMoving && !isYMoving )
            isMoving = false;
        
    }
    
    public void moveTo(int posX, int posY) {
        movingToX = posX;
        movingToY = posY;
        isMoving = true;
        
        isXMoving = (posX != centerX);
        isYMoving = (posY != centerY);
        
        rotate(posX, posY);
    }
    
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
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
