/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cadeachou;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TileBlinker {
    
    public static int MAX_TILE_SIZE = 60;
    
    private ImageLoader il;
    
    ArrayList<Tile> tileList;
    
    public TileBlinker(ImageLoader il) {
        this.il = il;
        
        tileList = new ArrayList<>();
    }
    
    public ArrayList<Tile> getTileList() {
        return tileList;
    }
    
    public void genNewTile() {
        
        int maxX = 800-(MAX_TILE_SIZE-CadeAchou.level);
        int maxY = 600-(MAX_TILE_SIZE-CadeAchou.level);
        
        int x = new Random().nextInt(maxX);
        int y = new Random().nextInt(maxY);
        
        Tile t = new Tile((MAX_TILE_SIZE-CadeAchou.level*10), (MAX_TILE_SIZE-CadeAchou.level*10), x, y);
        t.setTileImage(il.tile_image);
        tileList.add(t);
        
    }
    
    public void blinkTile(int round, Graphics2D g) throws InterruptedException {
        
        Tile t = tileList.get(round);
        
        g.drawImage(t.getTileImage(), t.posX, t.posY, t.width, t.height, null);
        Thread.sleep(1000);
        g.clearRect(t.posX, t.posY, t.width, t.height);
        
    }

}
