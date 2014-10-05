/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genius;

import static genius.GeniusGUI.HEIGHT;
import static genius.GeniusGUI.OFFSET;
import static genius.GeniusGUI.TILE_SIZE;
import static genius.GeniusGUI.WIDTH;
import genius.game.Genius;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TileBlinker {
    
    static int BLINK_TIME = 1000;
    static int TIME_BETWEEN_TILES = 500;
    
    
    private Genius genius;
    private ImageManager im;
    
    Tile[][] tiles = new Tile[2][2];
    
    public TileBlinker(Genius genius, ImageManager im) {
        
        this.genius = genius;
        genTiles();
        
        tiles[0][0].setBG(im.red);
        tiles[0][1].setBG(im.green);
        tiles[1][0].setBG(im.blue);
        tiles[1][1].setBG(im.yellow);
        
    }
    
    private void genTiles() {
        
        int index = 0;
        
        for(int i=0; i<2; i++)
            for(int j=0; j<2; j++)
                tiles[i][j] = new Tile(OFFSET+TILE_SIZE*j, OFFSET+TILE_SIZE*i, j, i, index++);
        
    }
    
    public void blinkImage(int index, Graphics2D g) throws InterruptedException {
        
        switch(index) {
            
            case 0:
                g.drawImage(tiles[0][0].bg, null, OFFSET-1, OFFSET-1);
                Thread.sleep(BLINK_TIME);
                g.clearRect(OFFSET-1, OFFSET-1, Tile.WIDTH, Tile.HEIGHT);
                Thread.sleep(TIME_BETWEEN_TILES);
                break;
                
            case 1:
                g.drawImage(tiles[0][1].bg, null, OFFSET+Tile.WIDTH+3, OFFSET-1);
                Thread.sleep(BLINK_TIME);
                g.clearRect(OFFSET+Tile.WIDTH+3, OFFSET-1, Tile.WIDTH, Tile.HEIGHT);
                Thread.sleep(TIME_BETWEEN_TILES);
                break;
                
            case 2:
                g.drawImage(tiles[1][0].bg, null, OFFSET-1, OFFSET+Tile.HEIGHT+3);
                Thread.sleep(BLINK_TIME);
                g.clearRect(OFFSET-1, OFFSET+Tile.HEIGHT+3, Tile.WIDTH, Tile.HEIGHT);
                Thread.sleep(TIME_BETWEEN_TILES);
                break;
                
            case 3:
                g.drawImage(tiles[1][1].bg, null, OFFSET+Tile.WIDTH+3, OFFSET+Tile.HEIGHT+3);
                Thread.sleep(BLINK_TIME);
                g.clearRect(OFFSET+Tile.WIDTH+3, OFFSET+Tile.HEIGHT+3, Tile.WIDTH, Tile.HEIGHT);
                Thread.sleep(TIME_BETWEEN_TILES);
                break;
                
            default:
                break;
            
        }
        
    }
    
}
