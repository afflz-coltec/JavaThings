/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogodavelha;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Field {
    
    static int FIELD_WIDTH;
    static int FIELD_HEIGHT;
    
    private int posX;
    private int posY;
    
    private int x;
    private int y;

    public Field(int posX, int posY,int x, int y) {
        this.posX = posX;
        this.posY = posY;
        this.x = x;
        this.y = y;
    }
    
    public int getPosX() {
        return this.posX;
    }
    
    public int getPosY() {
        return this.posY;
    }

    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public boolean isInside(int x, int y) {
        return ( (x > posX && x < (posX + FIELD_WIDTH)) && (y > posY && y < (posY + FIELD_HEIGHT)) );
    }
    
}
