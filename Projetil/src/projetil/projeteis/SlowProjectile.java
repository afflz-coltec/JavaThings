/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetil.projeteis;

import projetil.area.Area;

/**
 * Slow speed projectile class.
 * @author Pedro
 */
public class SlowProjectile extends Projectile{
    
    private static final int speed = 300;
    private static final int height = 150;
    private static final int width = 300;
    private static final String ProjectileName = "Slow Projectile";
    
    public SlowProjectile(int initX, int initY, int initA) {
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
