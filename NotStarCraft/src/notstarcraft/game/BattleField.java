/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game;

import java.awt.Point;
import java.util.ArrayList;
import notstarcraft.game.ships.ChiefShip;
import notstarcraft.game.ships.Ship;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class BattleField extends BasicGameState {
    
    private static final int STATE_ID = 2;
    
    private int gameWidth;
    private int gameHeight;
    
    private TiledMap map;
    private int mapX = 0;
    private int mapY = 0;
    private int maxMapX;
    private int maxMapY;
    
    private Rectangle selectionArea;
    private boolean selecting = false;
    
    private ChiefShip p1Ship;
    
    private float oldX = 0;
    private float newX = 0;
    private float oldY = 0;
    private float newY = 0;
    
    private int mouseX;
    private int mouseY;
    
    private Point pointToGo;
    
    private Line l;
    
    private ArrayList<Ship> shipList;

    public BattleField(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }
    
    @Override
    public int getID() {
        return BattleField.STATE_ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
        map = new TiledMap("res/map/map.tmx");
        
        maxMapX = -(map.getWidth()*map.getTileWidth() - gameWidth);
        maxMapY = -(map.getHeight()*map.getTileHeight() - gameHeight);
        
        p1Ship = new ChiefShip(300,300);
        
        selectionArea = new Rectangle(0, 0, 0, 0);
        
        pointToGo = new Point();
        
        l = new Line(oldX, oldX);
        
        shipList = new ArrayList<>();
        shipList.add(p1Ship);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        map.render(mapX, mapY);
        
        p1Ship.render(container, g);
        
        g.setColor(Color.green);
        g.setLineWidth(2);
        g.draw(selectionArea);
        g.draw(l);
        
        g.drawString("X: " + mouseX + "\nY: " + mouseY, 1200, 100);
        
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        Input input = gc.getInput();
        
        if( input.getMouseX() > 1270 || input.isKeyDown(Input.KEY_RIGHT) ) {
            mapX -= 200/delta;
            
            if( mapX < maxMapX )
                mapX += maxMapX-mapX;
            
        }
        else if( input.getMouseX() < 10 || input.isKeyDown(Input.KEY_LEFT)) {
            mapX += 200/delta;
            
            if( mapX >= 0 )
                mapX -= mapX;
            
        }
        if( input.getMouseY() > 710 || input.isKeyDown(Input.KEY_DOWN)) {
            mapY -= 200/delta;
            
            if( mapY < maxMapY )
                mapY += maxMapY-mapY;
            
        }
        else if( input.getMouseY() < 10 || input.isKeyDown(Input.KEY_UP)) {
            mapY += 200/delta;
            
            if( mapY >= 0 )
                mapY -= mapY;
            
        }
        
        oldY = newY;
        newY = mapY;
        oldX = newX;
        newX = mapX;
        
        if( oldX != 0 ) {
            p1Ship.setCenterX(p1Ship.getCenterX() + (newX-oldX));
        }
        if( oldY != 0 ) {
            p1Ship.setCenterY(p1Ship.getCenterY() + (newY-oldY));
        }
        
        p1Ship.update(gc, game, delta);
        
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        
    }
    
    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        
        if( selecting )
            selectionArea.setSize(newx-selectionArea.getX(), newy-selectionArea.getY());
        
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        
        if( button == Input.MOUSE_LEFT_BUTTON ) {
            selectionArea.setLocation(x, y);
            selecting = true;
        } else if( button == Input.MOUSE_RIGHT_BUTTON ) {
            
        }
        
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        
        if( button == Input.MOUSE_LEFT_BUTTON ) {
            selectionArea.setSize(0, 0);
            selecting = false;
        }
        else if( button == Input.MOUSE_RIGHT_BUTTON ) {
            p1Ship.moveTo(x, y);
            l.set(p1Ship.getCenterX(), p1Ship.getCenterY(), x, y);
        }
        
    }
    
}
