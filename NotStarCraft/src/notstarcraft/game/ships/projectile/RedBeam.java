/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.ships.projectile;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * 
 * @author Pedro
 */
public class RedBeam extends Projectile {

    static {
        
        try {
            cianoBeam = new Image("res/sprites/beams.png").getSubImage(280, 36, 27, 19);
        } catch (SlickException ex) {
            Logger.getLogger(RedBeam.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static Image cianoBeam;
    private static int DAMAGE_VALUE = 10;
    
    public RedBeam(float centerX, float centerY, float goToX, float goToY) {
        super(centerX, centerY, goToX, goToY);
    }

    @Override
    protected Image getProjectileImage() {
        return cianoBeam.copy();
    }

    @Override
    public int getDamage() {
        return DAMAGE_VALUE;
    }

}
