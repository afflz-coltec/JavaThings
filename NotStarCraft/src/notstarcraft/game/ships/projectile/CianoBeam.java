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
public class CianoBeam extends Projectile {

    static {
        
        try {
            cianoBeam = new Image("res/sprites/lasers.png").getSubImage(29, 23, 35, 20);
        } catch (SlickException ex) {
            Logger.getLogger(CianoBeam.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static Image cianoBeam;
    
    public CianoBeam(float centerX, float centerY, float goToX, float goToY) {
        super(centerX, centerY, goToX, goToY);
    }

    @Override
    protected Image getProjectileImage() {
        return cianoBeam.copy();
    }

}
