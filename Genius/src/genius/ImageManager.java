/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genius;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ImageManager {
    
    BufferedImage red;
    BufferedImage green;
    BufferedImage blue;
    BufferedImage yellow;
    
    public ImageManager() throws IOException {
        red = ImageIO.read(new File("res/red-square.png"));
        green = ImageIO.read(new File("res/green-square.png"));
        blue = ImageIO.read(new File("res/blue-square.png"));
        yellow = ImageIO.read(new File("res/yellow-square.png"));
    }
    
}
