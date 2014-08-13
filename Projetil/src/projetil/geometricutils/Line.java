/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.geometricutils;

/**
 * Line class to create a liner function to any kind of line.
 * General function of the line. ax + bx + c = 0
 * @author Pedro
 */
public class Line {
    
    private double a;
    private final double b = -1;
    private double c;
    
    /**
     * Constructor of the Line.
     * @param ang An {@code int} value of the line's angle.
     * @param p A {@code Dot} dot where the line passes thru.
     */
    public Line (int ang, Dot p) {
        this.a = Math.tan(Math.toRadians(ang));
        this.c = -a*p.getX() - b*p.getY();
    }
    
    /**
     * Method to set the coeficients of the line function.
     * @param d A {@code Dot} dot where the line passes thru.
     * @param ang An {@code int} value of the line's angle.
     */
    public void setLineFunction(Dot d, int ang) {
        this.a = Math.tan(Math.toRadians(ang));
        this.c = -a*d.getX() -b*d.getY();
    }
    
    /**
     * Method that returns the distance from a point to a line.
     * <p>Distance from a point to line function:
     * <p>{@code D = (ax + by + c) / (sqrt(a² + b²))}
     * @param d A {@code Dot} dot to check the distance.
     * @return Returns a {@code double} value of the distance.
     */
    public double getDotToLineDistance(Dot d) {
        return ( (Math.abs(this.a * d.getX() + this.b * d.getY() + this.c)) / (Math.sqrt(Math.pow(this.a, 2) + Math.pow(this.b, 2))) );
    }
    
}
