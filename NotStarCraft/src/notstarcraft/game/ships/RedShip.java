/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import notstarcraft.game.ships.projectile.RedBeam;
import notstarcraft.game.ships.projectile.Projectile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Pedro
 */
public class RedShip extends Ship {
    
    /**
     * Initialize the default image to do rotation operations
     */
    static {
        
        try {
            redShip = new Image("res/sprites/destroyer.png").getScaledCopy(0.23f);
        } catch (SlickException ex) {
            Logger.getLogger(RedShip.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static Image redShip;
    private static final float speed = 1;
    
    private static final float FIRE_RATE = 10.0f;
    private float fire_delta = 0;
    
    /**
     * Constructor for a Red Ship. In this case, this is the player ship.
     * @param centerX
     * @param centerY
     * @throws SlickException 
     */
    public RedShip(float centerX, float centerY) throws SlickException {
        super(centerX,centerY);
    }

    @Override
    protected Image getOriginalImage() {
        return redShip.copy();
    }
    
    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }
    
    @Override
    public Shape getHitBox() {
        return this.hitBox;
    }
    
    @Override
    public float getSpeed() {
        return speed;
    }
    
    @Override
    protected void fireBeam(float posX, float posY) {
        Projectile redBeam = new RedBeam(position.x, position.y, posX, posY);
        this.projectiles.add(redBeam);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        for(Projectile p : projectiles)
            p.render(container, g); // Render the beams
        
        shipImage.drawCentered(this.position.x, this.position.y); // Render the ship
        
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        Input input = gc.getInput();
        
        // For each letter typed, add or subtract its corresponding coordinate
        // Used delta/3.0f for not getting the ship to fast or to slow
        if( input.isKeyDown(Input.KEY_W) ) {
            this.position.y -= delta/3.0f;
        }
        if( input.isKeyDown(Input.KEY_S) ) {
            this.position.y += delta/3.0f;
        }
        
        if( input.isKeyDown(Input.KEY_D) ) {
            this.position.x += delta/3.0f;
        }
        if( input.isKeyDown(Input.KEY_A) ) {
            this.position.x -= delta/3.0f;
        }
        
        // Some math...
        float deltaY = input.getMouseY()-position.y;
        float deltaX = input.getMouseX()-position.x;
        
        // arctg(tg) = angle in rads
        float rad = (float) Math.atan2(deltaY, deltaX);
        float alfa = (float)Math.toDegrees(rad); // Conversion to degrees
        
        // Get the original image to replace the old one and the I rotate it
        this.shipImage = getOriginalImage().copy();
        shipImage.rotate(alfa);
        
        // Same thing with hitbox, but the transform method returns a new Shape so I don't need to make a copy of it
        hitBox = defaultHitBox.transform(Transform.createRotateTransform(rad, position.x, position.y));
        
        // Update the hitbox position to "follow" the ship
        hitBox.setCenterX(this.position.x);
        hitBox.setCenterY(this.position.y);
        
        // Gets an iterator of the projectiles list
        Iterator<Projectile> iterator = projectiles.iterator();
        
        // For each projectile I check if it's active. If so, update it, otherwise remove it
        while( iterator.hasNext() ) {
            
            Projectile p = iterator.next();
            
            if(p.isActive())
                p.update(gc, game, delta);
            else
                iterator.remove();
        }
        
        fire_delta += delta;
        
        // If the left mouse button is pressed, I fire beams like there's no tomorrow by adding more projectiles
        // to the projectiles list.
        if( fire_delta > FIRE_RATE && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ) {
            fireBeam(input.getMouseX(), input.getMouseY());
            fire_delta = 0;
        }
        
    }
    
}
