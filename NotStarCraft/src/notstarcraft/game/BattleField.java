/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
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
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        map.render(mapX, mapY);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        Input input = gc.getInput();
        
        if( input.getMouseX() > 1270 && mapX > maxMapX ) {
            mapX -= 10;
        }
        else if( input.getMouseX() < 10 && mapX != 0 ) {
            mapX += 10;
        }
        if( input.getMouseY() > 710 && mapY > maxMapY ) {
            mapY -= 10;
        }
        else if( input.getMouseY() < 10 && mapY != 0 ) {
            mapY += 10;
        }
        
    }
}
