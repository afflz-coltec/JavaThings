/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships;

import java.util.logging.Level;
import java.util.logging.Logger;
import notstarcraft.game.ships.projectile.CianoBeam;
import notstarcraft.game.ships.projectile.Projectile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Pedro
 */
public class BlueShip extends Ship {

    static {
        
        try {
            blueShip = new Image("res/sprites/ship1.png").getScaledCopy(0.6f);
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
        for( Projectile p : projectiles )
            p.render(container, g);
        
        shipImage.drawCentered(position.x, position.y);
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    protected void fireBeam(float posX, float posY) {
        this.projectiles.add(new CianoBeam(position.x, position.y, posX, posY));
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
    }

}
