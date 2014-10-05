/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cadeachou;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ImageLoader {

    public BufferedImage tile_image;

    public ImageLoader() throws IOException {
        tile_image = ImageIO.read(new File("res/image-60x60.png"));
    }
    
}
