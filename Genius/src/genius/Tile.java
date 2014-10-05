/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genius;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Tile {

    static int WIDTH = 200;
    static int HEIGHT = 200;
    
    private int posX;
    private int posY;
    private int x;
    private int y;
    
    private int index;
    
    BufferedImage bg;

    public Tile(int posX, int posY, int x, int y, int index) {
        this.posX = posX;
        this.posY = posY;
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }
    
    public void setBG(BufferedImage bg) {
        this.bg = bg;
    }
    
    public boolean isInside(int x, int y) {
        return ( (x > posX && x < (posX + WIDTH)) && (y > posY && y < (posY + HEIGHT)) );
    }
    
}
