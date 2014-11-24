/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game;

import java.util.ArrayList;
import notstarcraft.game.ships.RedShip;
import notstarcraft.game.ships.Ship;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * 
 * @author Pedro
 */
public class BattleField extends BasicGameState {
    
    public static final int STATE_ID = 1;
    
    private Image background;
    
    private RedShip redShip;
    
    private int mouseX;
    private int mouseY;
    
    private ArrayList<Ship> shipList;

    public BattleField() { }
    
    @Override
    public int getID() {
        return BattleField.STATE_ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
        // Init all the members
        
        background = new Image("res/map/space-red.png");
        
        redShip = new RedShip(640,384);
        
        shipList = new ArrayList<>();
        shipList.add(redShip);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setAntiAlias(true); // kinda obvious
        
        background.draw(0, 0); // Draw the background image
        
        redShip.render(container, g); // Draw the player ship
        
        g.drawString("X: " + mouseX + "\nY: " + mouseY, 1200, 100); // Draw the mouse position
        
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        Input input = gc.getInput(); // Gets the input stream
        
        redShip.update(gc, game, delta); // Updates the player ship
        
        mouseX = input.getMouseX(); // Update the x mouse position
        mouseY = input.getMouseY(); // Update the y mouse position
        
        // Map Movement System
        // Not goin' to use it. No moar time available
        
//        oldY = newY;
//        newY = mapY;
//        oldX = newX;
//        newX = mapX;
//        
//        if( oldX != 0 ) {
//            for( Ship s : shipList )
//                s.setCenterX(redShip.getCenterX() + (newX-oldX));
//        }
//        if( oldY != 0 ) {
//            for( Ship s : shipList )
//                s.setCenterY(redShip.getCenterY() + (newY-oldY));
//        }
//        
//        if( input.getMouseX() > 1270 || input.isKeyDown(Input.KEY_RIGHT) ) {
//            mapX -= 200/delta;
//            
//            if( mapX < maxMapX )
//                mapX += maxMapX-mapX;
//            
//        }
//        else if( input.getMouseX() < 10 || input.isKeyDown(Input.KEY_LEFT)) {
//            mapX += 200/delta;
//            
//            if( mapX >= 0 )
//                mapX -= mapX;
//            
//        }
//        if( input.getMouseY() > 710 || input.isKeyDown(Input.KEY_DOWN)) {
//            mapY -= 200/delta;
//            
//            if( mapY < maxMapY )
//                mapY += maxMapY-mapY;
//            
//        }
//        else if( input.getMouseY() < 10 || input.isKeyDown(Input.KEY_UP)) {
//            mapY += 200/delta;
//            
//            if( mapY >= 0 )
//                mapY -= mapY;
//            
//        }
        
    }
    
}
