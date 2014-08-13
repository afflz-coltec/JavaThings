/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.projeteis;

import projetil.area.Area;

/**
 * Fast speed projectile class.
 * @author Pedro
 */
public class FastProjectile extends Projectile{
        
    private static final int speed = 850;
    private static final int height = 100;
    private static final int width = 400;
    private static final String ProjectileName = "Fast Projectile";
    
    public FastProjectile(int initX, int initY, int initA) {
        super(initX,initY,initA);
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public String getProjectileName() {
        return ProjectileName;
    }
    
}