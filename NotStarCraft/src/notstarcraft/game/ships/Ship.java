/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import java.util.ArrayList;
import java.util.Iterator;
import notstarcraft.game.ships.projectile.Projectile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Pedro
 */
public abstract class Ship {

    static {
        
    }
    
    protected Image shipImage;
    
    protected float movingToX;
    protected float movingToY;
    
    protected float width;
    protected float height;
    
    protected Vector2f position;
    protected Vector2f speed;
    
    protected Shape defaultHitBox;
    protected Shape hitBox;
    
    protected ArrayList<Projectile> projectiles;
    
    protected boolean isMoving = false;

    protected Ship(float centerX, float centerY) {
        
        this.position = new Vector2f(centerX, centerY);
        
        this.shipImage = getOriginalImage().copy();
        
        this.width = this.shipImage.getWidth();
        this.height = this.shipImage.getHeight();
        
        defaultHitBox = new Rectangle(0, 0, width*0.7f, height*0.7f);
        defaultHitBox.setCenterX(centerX);
        defaultHitBox.setCenterY(centerY);
        
        hitBox = new Rectangle(0, 0, width*0.9f, height*0.9f);
        hitBox.setCenterX(centerX);
        hitBox.setCenterY(centerY);
        
        projectiles = new ArrayList<>();
        
    }
    
    public void setCenterX(float x) {
        this.position.x = x;
    }
    
    public void setCenterY(float y) {
        this.position.y = y;
    }
    
    public float getCenterX() {
        return this.position.x;
    }
    
    public float getCenterY() {
        return this.position.y;
    }
    
    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
    
    public boolean isInside( Shape rect ) {
        return hitBox.intersects(rect);
    }
    
    private void moveShip(int delta) {
        
        boolean checkedY = false;
        boolean checkedX = false;
        
        if( (movingToY-position.y) >= 0 && (speed.y) >= 0) {
            checkedY = true;
        }
        else if( (movingToY-position.y) <= 0 && (speed.y) <= 0  ) {
            checkedY = true;
        }
        
        if( (movingToX-position.x) >= 0 && (speed.x) >= 0) {
            checkedX = true;
        }
        else if( (movingToX-position.x) <= 0 && (speed.x) <= 0  ) {
            checkedX = true;
        }
        
        if( checkedY && checkedX ) {
            Vector2f realSpeed = this.speed.copy();
            realSpeed.scale(delta/10.0f);
        
            position.add(realSpeed);
        }
        else {
            isMoving = false;
        }
        
    }
    
    public void moveTo(int posX, int posY) {
        
        this.movingToX = posX;
        this.movingToY = posY;

        float deltaY = movingToY-position.y;
        float deltaX = movingToX-position.x;
        float rad = (float) Math.atan2(deltaY, deltaX);
        float alfa = (float)Math.toDegrees(rad);
        this.shipImage = getOriginalImage();
        shipImage.rotate(alfa);
        
        this.speed = new Vector2f(getSpeed()*(float)Math.cos(rad), getSpeed()*(float)Math.sin(rad));

        this.isMoving = true;
        
    }

    public abstract void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException;
    
    protected abstract void fireBeam(float posX, float posY);
    
    protected abstract Image getOriginalImage();
    
    public abstract void render(GameContainer container, Graphics g) throws SlickException;
    
    public abstract float getSpeed();
    
}
