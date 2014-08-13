/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.bulkhead;

import java.util.ArrayList;
import projetil.excecoes.InvalidBuckheadAngleException;
import projetil.geometricutils.Colision;
import projetil.geometricutils.Dot;
import projetil.geometricutils.Line;

/**
 * Bulkhead class to instantiate a new bulkhead inside an area.
 * @author Pedro
 */
public class Bulkhead implements Colision {

    private int height;
    private int width;
    private int ang;
    
    // Lista de Vértices do Anteparo.
    private ArrayList<Dot> VList = new ArrayList<Dot>();
    
    // Função linear referente a reta paralela ao comprimento que passa pelo centro do anteparo.
    private Line l1;
    // Função linear referente a reta paralela à altura que passa pelo centro do anteparo.
    private Line l2;
    
    // Centro de Massa do Anteparo.
    private Dot posXY;
    
    /**
     * Constructor for a bulkhead.
     * @param h Bulkhead's height
     * @param w Bulkhead's width
     * @param a Bulkhead's angle
     * @param posX X position
     * @param posY Y position
     * @throws InvalidBuckheadAngleException 
     */
    public Bulkhead(int h, int w, int a, int posX, int posY) throws InvalidBuckheadAngleException {
        
        if ( a%90 != 0 ) {
            throw new InvalidBuckheadAngleException();
        }
        
        this.height = h;
        this.width = w;
        this.ang = a;
        this.posXY = new Dot(posX,posY);
        
        // Vértice Superior Direito.
        int vxSD = (int)Math.round( Math.cos(Math.toRadians(a)) * (w/2) - Math.sin(Math.toRadians(a)) * (-h/2) ) + posX;
        int vySD = (int)Math.round( Math.sin(Math.toRadians(a)) * (w/2) + Math.cos(Math.toRadians(a)) * (-h/2) ) + posY;

        // Vértice Superior Esquerdo.
        int vxSE = (int)Math.round( Math.cos(Math.toRadians(a)) * (-w/2) - Math.sin(Math.toRadians(a)) * (-h/2) ) + posX;
        int vySE = (int)Math.round( Math.sin(Math.toRadians(a)) * (-w/2) + Math.cos(Math.toRadians(a)) * (-h/2) ) + posY;

        // Vértice Inferior Direito.
        int vxID = (int)Math.round( Math.cos(Math.toRadians(a)) * (w/2) - Math.sin(Math.toRadians(a)) * (h/2) ) + posX;
        int vyID = (int)Math.round( Math.sin(Math.toRadians(a)) * (w/2) + Math.cos(Math.toRadians(a)) * (h/2) ) + posY;

        // Vértice Inferior Esquerdo.
        int vxIE = (int)Math.round( Math.cos(Math.toRadians(a)) * (-w/2) - Math.sin(Math.toRadians(a)) * (h/2) ) + posX;
        int vyIE = (int)Math.round( Math.sin(Math.toRadians(a)) * (-w/2) + Math.cos(Math.toRadians(a)) * (h/2) ) + posY;
        
        // Adiciona os vértices a lista.
        VList.add(new Dot(vxSD,vySD));
        VList.add(new Dot(vxID,vyID));
        VList.add(new Dot(vxIE,vyIE));
        VList.add(new Dot(vxSE,vySE));
        
        // Cria as funçoes de acordo com o centro de massa.
        l1 = new Line(a,this.posXY);
        l2 = new Line(a+90, this.posXY);
        
    }
    
    /**
     * Method that checks if som part of a bulkhead is inside of another bulkhead.
     * @param ant Bulkhead
     * @return Returns {@code true} if some part of the bulkhead is inside of another one, {@code false} otherwise.
     */
    @Override
    public boolean CheckColision(Object ant) {
        
        // Variável "castada" com o tipo Bulkhead para as operações seguintes.
        Bulkhead b = (Bulkhead)ant;
        
        // Variável de retorno: true se colidiu, false se não.
        boolean MarkOfDeath = false;
        
        for ( Dot d : this.VList ) {
                
            // Função matemática. A fórmula desta se encontra na classe Line.
            if( Math.round(b.getWidthFunction().getDotToLineDistance(d))  <= b.getHeight()/2 &&
                Math.round(b.getHeightFunction().getDotToLineDistance(d)) <= b.getWidth()/2 ) {
                MarkOfDeath = true;
            }
        }
        
        return MarkOfDeath;
        
    }
    
    /**
     * Method that returns the height of the bulkhead.
     * @return Bulkhead's height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method that returns the width of the bulkhead.
     * @return Bulkhead's width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Method that returns the angle of the bulkhead.
     * @return Bulkhead's angle.
     */
    public int getAng() {
        return ang;
    }

    /**
     * Method that returns the X position of the center of mass of the bulkhead.
     * @return X position.
     */
    public int getPosX() {
        return posXY.getX();
    }

    /**
     * Method that returns the Y position of the center of mass of the bulkhead.
     * @return Y position.
     */
    public int getPosY() {
        return posXY.getY();
    }
    
    /**
     * Method that returns the vertices' list of the bulkhead.
     * @return Vertices ArrayList.
     */
    public ArrayList<Dot> getVList() {
        return VList;
    }

    /**
     * Method that returns the linear function of the width line of the bulkhead.
     * @return Linear function of width line.
     */
    public Line getWidthFunction() {
        return l1;
    }

    /**
     * Method that returns the linear function of the height line of the bulkhead.
     * @return Linear function of height line.
     */
    public Line getHeightFunction() {
        return l2;
    }
    
}
