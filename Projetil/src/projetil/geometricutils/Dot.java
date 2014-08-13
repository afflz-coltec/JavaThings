/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.geometricutils;

/**
 * Dot class to represent a point in a cartesian plan.
 * @author Pedro
 */
public class Dot {

    private int x;
    private int y;
    
    public Dot(int x, int y) {
        
        this.x = x;
        this.y = y;
        
    }
    
    /**
     * Method that returns the X position of the dot.
     * @return Returns the X position.
     */
    public int getX() {
        return x;
    }

    /**
     * Method that sets the X position of the dot.
     * @param x An {@code int} X value.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Method that returns the Y position of the dot.
     * @return Returns the Y position.
     */
    public int getY() {
        return y;
    }

    /**
     * Method that sets the Y position of the dot.
     * @param x An {@code int} Y value.
     */
    public void setY(int y) {
        this.y = y;
    }
    
}
