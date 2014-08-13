/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.projeteis;

import java.util.ArrayList;
import projetil.bulkhead.Bulkhead;
import projetil.area.Area;
import projetil.geometricutils.Colision;
import projetil.geometricutils.Dot;
import projetil.geometricutils.Line;

/**
 *
 * @author Pedro
 */
abstract public class Projectile implements Colision {

    private int angA;
    
    private boolean MarkOfDeath = false;
    
    // Centro de massa do projétil.
    private Dot posXY;
    
    // Área em que o projétil se encontra.
    //private Area a;
    
    // Função linear referente a reta paralela ao comprimento que passa pelo centro do projétil.
    private Line l1;
    // Função linear referente a reta paralela à altura que passa pelo centro do projétil.
    private Line l2;
    
    // Lista de Vértices do Projétil.
    private ArrayList<Dot> VList = new ArrayList<Dot>();
    
    /**
     * Constructor of a projectile.
     * @param initX Inicial X position.
     * @param initY Inicial Y position.
     * @param A Projectile's angle.
     * @param a Area in which the projectile is.
     */
    protected Projectile(int initX, int initY, int A) {
        
        //this.a = a;
        this.posXY = new Dot(initX, initY);
        this.angA = A;
        
        // Vértice Superior Direito.
        int vxSD = (int)Math.round( Math.cos(Math.toRadians(angA)) * (getWidth()/2) - Math.sin(Math.toRadians(angA)) * (-getHeight()/2) ) + initX; // Done
        int vySD = (int)Math.round( Math.sin(Math.toRadians(angA)) * (getWidth()/2) + Math.cos(Math.toRadians(angA)) * (-getHeight()/2) ) + initY; // Done

        // Vértice Superior Esquerdo.
        int vxSE = (int)Math.round( Math.cos(Math.toRadians(angA)) * (-getWidth()/2) - Math.sin(Math.toRadians(angA)) * (-getHeight()/2) ) + initX; // Done
        int vySE = (int)Math.round( Math.sin(Math.toRadians(angA)) * (-getWidth()/2) + Math.cos(Math.toRadians(angA)) * (-getHeight()/2) ) + initY; // Done

        // Vértice Inferior Direito.
        int vxID = (int)Math.round( Math.cos(Math.toRadians(angA)) * (getWidth()/2) - Math.sin(Math.toRadians(angA)) * (getHeight()/2) ) + initX; // Done
        int vyID = (int)Math.round( Math.sin(Math.toRadians(angA)) * (getWidth()/2) + Math.cos(Math.toRadians(angA)) * (getHeight()/2) ) + initY; // Done

        // Vértice Inferior Esquerdo.
        int vxIE = (int)Math.round( Math.cos(Math.toRadians(angA)) * (-getWidth()/2) - Math.sin(Math.toRadians(angA)) * (getHeight()/2) ) + initX; // Done
        int vyIE = (int)Math.round( Math.sin(Math.toRadians(angA)) * (-getWidth()/2) + Math.cos(Math.toRadians(angA)) * (getHeight()/2) ) + initY; // Done
        
        // Adiciona os vértices a lista.
        VList.add(new Dot(vxSD,vySD));
        VList.add(new Dot(vxID,vyID));
        VList.add(new Dot(vxIE,vyIE));
        VList.add(new Dot(vxSE, vySE));
        
        // Cria as funçoes de acordo com o centro de massa.
        l1 = new Line(angA, posXY);
        l2 = new Line(angA + 90, posXY);
        
    }
    
    /**
     * Method that moves the projectile.
     * @param elapsedTime Elapsed time until last movement.
     */
    public final void move(double elapsedTime) {
        
        // posX = posX + t * V * cos(angA)
        this.posXY.setX(posXY.getX() + (int)(Math.round((elapsedTime/1000) * this.getSpeed() * Math.cos(Math.toRadians(angA)))));
        // posX = posX + t * V * sin(angA)
        this.posXY.setY(posXY.getY() + (int)(Math.round((elapsedTime/1000) * this.getSpeed() * Math.sin(Math.toRadians(angA)))));
        
        // Atualiza a posição dos vértices.
        moveVertex(elapsedTime);
        
        // Atualiza a função da reta paralela à altura que passa pelo centro do projétil.
        l2.setLineFunction(posXY, angA+90);
        
        // Imprimi o tipo de projétil e sua posição.
        System.out.println( this.getProjectileName() );
        System.out.println( "( " + posXY.getX() + "," + posXY.getY() + " )\n" );
        
    }
    
    /**
     * Method that updates the position of each vertex of a projectile.
     * @param elapsedTime Elapsed time until last movement.
     */
    private void moveVertex(double elapsedTime) {
        
        for (Dot p : VList) {
            p.setX(p.getX() + (int)(Math.round((elapsedTime/1000) * this.getSpeed() * Math.cos(Math.toRadians(angA)))));
            p.setY(p.getY() + (int)(Math.round((elapsedTime/1000) * this.getSpeed() * Math.sin(Math.toRadians(angA)))));
        }
        
    }
    
    /**
     * Method that checks if a projectile collided with another object.
     * @param obj Object that can collide with a projectile such as another projectile, a bulkhead or the extremities of an area.
     * @return Returns true if the projectile collided, false otherwise.
     */
    @Override
    public final boolean CheckColision(Object obj) {
        
        if (obj instanceof Area) {
            
            // Variável "castada" com o tipo Area para as operações seguintes.
            Area tmp = (Area)obj;
            
            for ( Dot d : VList ) {
                
                // Checa se um dos vértices do projétil se encontra fora da área.
                if ( (d.getX() > tmp.getWidth() ) || (d.getX() < 0) ||
                     (d.getY() > tmp.getHeight()) || (d.getY() < 0) ) {
                    this.MarkOfDeath = true;
                }
            } 
        }
        
        else if ( obj instanceof Bulkhead ) {
            
            // Variável "castada" com o tipo Bulkhead para as operações seguintes.
            Bulkhead tmp = (Bulkhead)obj;
            
            for ( Dot d : this.VList ) {
                
                // Função matemática. A fórmula desta se encontra na classe Line.
                if( Math.round(tmp.getWidthFunction().getDotToLineDistance(d))  <= tmp.getHeight()/2 &&
                    Math.round(tmp.getHeightFunction().getDotToLineDistance(d)) <= tmp.getWidth()/2 ) {
                    this.MarkOfDeath = true;
                }
            }
        }
        
        else {
            
            // Variável "castada" com o tipo Projectile para as operações seguintes.
            Projectile tmp = (Projectile)obj;
            
            for ( Dot d : tmp.getVList() ) {
                
                // Função matemática. A fórmula desta se encontra na classe Line.
                if ( Math.round(this.l1.getDotToLineDistance(d)) <= this.getHeight()/2 &&
                     Math.round(this.l2.getDotToLineDistance(d)) <= this.getWidth()/2 ) {
                    this.MarkOfDeath = true;
                }
            }
        }
        
        return this.MarkOfDeath;
    }
    
    /**
     * Method that prints the position of each vertex of a projectile.
     */
    public final void printVertex() {
        System.out.println("---Vertexs---");
        for ( Dot d : VList ) {
            System.out.println("( " + d.getX() + "," + d.getY() + " )");
        }
        System.out.println("-------------\n");
    }
    
    /**
     * Method that returns the X position of a projectile.
     * @return Returns a {@code int} value of the X position.
     */
    public int getPosX() {
        return posXY.getX();
    }

    /**
     * Method that returns the Y position of a projectile.
     * @return Returns a {@code int} value of the Y position.
     */
    public int getPosY() {
        return posXY.getY();
    }

    /**
     * Method that returns the angle of a projectile.
     * @return Returns a {@code int} value of the projectile's angle.
     */
    public int getAngA() {
        return angA;
    }
    
    /**
     * Method that returns the list of vertices of a projectile.
     * @return Returns an {@code ArrayList<Dot>} with a list of vertices.
     */
    public ArrayList<Dot> getVList() {
        return VList;
    }

    /**
     * Method that sets the mark of death of a projectile.
     * @param MarkOfDeath Boolean value for the mark. True for death, false otherwise.
     */
    public void setMarkOfDeath(boolean MarkOfDeath) {
        this.MarkOfDeath = MarkOfDeath;
    }
    
    /**
     * Method that gets the speed of the projectile.
     * @return Projectile's speed.
     */
    abstract public float getSpeed();
    
    /**
     * Method that returns the height of a projectile.
     * @return Projectile's Height.
     */
    abstract public int getHeight();
    
    /**
     * Method that returns the width of a projectile.
     * @return Projectile's Width.
     */
    abstract public int getWidth();
    
    /**
     * Method that returns the name of a projectile.
     * @return String that contains the projectile's name.
     */
    abstract public String getProjectileName();
    
}
