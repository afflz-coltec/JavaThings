/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cadeachou;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Tile {
    
    int height;
    int width;
    
    int posX;
    int posY;
    
    private BufferedImage tile_image;
    
    protected Tile(int height, int width, int posX, int posY) {
        this.height = height;
        this.width = width;
        this.posX = posX;
        this.posY = posY;
    }
    
    public void setTileImage(BufferedImage image) {
        this.tile_image = image;
    }
    
    public BufferedImage getTileImage() {
        return this.tile_image;
    }
    
    public boolean isInside(int x, int y) {
        return ( (x > posX && x < (posX + width)) && (y > posY && y < (posY + height)) );
    }

}
