/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships.projectile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
public abstract class Projectile {

    private static final int LIFE_TIME = 2000;
    
    protected Image projectile;
    
    protected Vector2f position;
    protected Vector2f speed;
    
    protected Shape hitbox;
    
    protected float timeLiving = 0.0f;
    
    protected boolean isActive;
    
    protected Projectile(float centerX, float centerY, float goToX, float goToY) {
        
        projectile = getProjectileImage();
        
        this.position = new Vector2f(centerX, centerY);
        
        // Sets up the hitbox
        this.hitbox = new Rectangle(0, 0, projectile.getWidth()*0.5f, projectile.getHeight()*0.5f);
        this.hitbox.setCenterX(position.x);
        this.hitbox.setCenterY(position.y);
        
        // More math...
        float deltaY = goToY-centerY;
        float deltaX = goToX-centerX;
        
        float rad = (float) Math.atan2(deltaY, deltaX);
        float angle = (float) Math.toDegrees(rad);
        
        projectile.rotate(angle);
        this.hitbox = hitbox.transform(Transform.createRotateTransform(rad, position.x, position.y));
        
        speed = new Vector2f(10.0f*(float)Math.cos(rad), 10.0f*(float)Math.sin(rad));
        
        position.add(new Vector2f(50.0f*(float)Math.cos(rad), 50.0f*(float)Math.sin(rad)));
        
        isActive = true;
        
    }
    
    public final void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if( isActive ) {
            Vector2f realSpeed = this.speed.copy();
        
            position.add(speed);
            hitbox.setCenterX(position.x);
            hitbox.setCenterY(position.y);
            timeLiving += delta;
        }
        
        if( timeLiving > LIFE_TIME )
            isActive = false;
        
    }
    
    public final void render(GameContainer container, Graphics g) {
        projectile.drawCentered(position.x, position.y); // Render the projectile
        
        // Render the hitbox
//        g.setLineWidth(2);
//        g.draw(hitbox);
    }
    
    /**
     * Return the current state of a projectile
     * @return <code>true</code> if active, <code>false</code> otherwise.
     */
    public boolean isActive() {
        return this.isActive;
    }
    
    public boolean testCollision(Shape hitbox) {
        
        return false;
        
    }
    
    protected abstract Image getProjectileImage();
    
}
