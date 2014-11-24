/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.lobby;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 * 
 * @author Pedro
 */
public class History extends AbstractComponent {

    private String gameTitle = "Not Star Craft!";
    private int x,y,width=320,height=640;
    
    public History(GUIContext container) {
        super(container);
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        
        g.setColor(Color.lightGray);
        g.fill(new Rectangle(50, 50, width, height));
        
        g.setColor(Color.darkGray);
        g.drawString("History", 180, 55);
        
    }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

}
