/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.area;

/**
 * Area class to instantiate a new Area.
 * @author Pedro
 */
public class Area {
    
    private int height;
    private int width;
    
    /**
     * Constructor of the Area.
     * @param h The area's height.
     * @param w The area's width.
     */
    public Area(int h, int w) {
        this.height = h;
        this.width = w;
    }
    
    /**
     * Method that returns the height of the area.
     * @return Area's height.
     */
    public int getHeight() {
        return this.height;
    }
    
    /**
     * Method that returns the width of the area.
     * @return Area's width.
     */
    public int getWidth() {
        return this.width;
    }
    
}