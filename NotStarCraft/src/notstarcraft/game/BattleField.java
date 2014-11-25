/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notstarcraft.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import notstarcraft.Game;
import notstarcraft.game.lobby.GameOver;
import notstarcraft.game.ships.BlueShip;
import notstarcraft.game.ships.RedShip;
import notstarcraft.game.ships.Ship;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Pedro
 */
public class BattleField extends BasicGameState {

    public static final int STATE_ID = 2;

    private static final float SPAWN_TIME = 2000.0f;

    private Image background;

    private RedShip redShip;

    private float timePassed = 0;

    private int level = 1;
    private boolean isGameOver;

    private int mouseX;
    private int mouseY;

    private ArrayList<BlueShip> enemiesList;

    public BattleField() {
    }

    @Override
    public int getID() {
        return BattleField.STATE_ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        // Init all the members
        background = new Image("res/map/space-red.png");
        redShip = new RedShip(640, 384);
        enemiesList = new ArrayList<>();

        level = 1;

        for (int i = 0; i < level * 5; i++) {
            int randX = 0;
            int randY = 0;

            int s = new Random().nextInt(4);

            if (s == 0) {
                randX = new Random().nextInt(50) * (-1);
                randY = new Random().nextInt(Game.HEIGHT);
            } else if (s == 1) {
                randX = new Random().nextInt(Game.WIDTH);
                randY = new Random().nextInt(50) * (-1);
            } else if (s == 2) {
                randX = new Random().nextInt(50) + Game.WIDTH;
                randY = new Random().nextInt(Game.HEIGHT);
            } else {
                randX = new Random().nextInt(Game.WIDTH);
                randY = new Random().nextInt(50) + Game.HEIGHT;
            }

            enemiesList.add(new BlueShip(randX, randY, redShip));
        }

        isGameOver = false;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setAntiAlias(true); // kinda obvious

        background.draw(0, 0); // Draw the background image

        redShip.render(container, g); // Draw the player ship

        for (Ship s : enemiesList) {
            s.render(container, g);
        }

        g.drawString("Level " + level, 1200, 50);
        g.drawString("X: " + mouseX + "\nY: " + mouseY, 1200, 100); // Draw the mouse position

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        timePassed += delta;

        Input input = gc.getInput(); // Gets the input stream

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            gc.setPaused(!gc.isPaused());
        }

        if (!gc.isPaused()) {
            redShip.update(gc, game, delta); // Updates the player ship
        }
        Iterator<BlueShip> it = enemiesList.iterator();

        while (it.hasNext()) {

            BlueShip bs = it.next();

            if (bs.isActive()) {
                bs.update(gc, game, delta);
            } else {
                it.remove();
            }
        }

        for (BlueShip bs : enemiesList) {
            if (redShip.getHitBox().intersects(bs.getHitBox())) {
                isGameOver = true;
            }
        }

        if (enemiesList.isEmpty()) {
            level++;
            for (int i = 0; i < level * 5; i++) {
                int randX = 0;
                int randY = 0;

                int s = new Random().nextInt(4);

                if (s == 0) {
                    randX = new Random().nextInt(50) * (-1);
                    randY = new Random().nextInt(Game.HEIGHT);
                } else if (s == 1) {
                    randX = new Random().nextInt(Game.WIDTH);
                    randY = new Random().nextInt(50) * (-1);
                } else if (s == 2) {
                    randX = new Random().nextInt(50) + Game.WIDTH;
                    randY = new Random().nextInt(Game.HEIGHT);
                } else {
                    randX = new Random().nextInt(Game.WIDTH);
                    randY = new Random().nextInt(50) + Game.HEIGHT;
                }

                enemiesList.add(new BlueShip(randX, randY, redShip));
            }

        }

        if (isGameOver) {
            game.enterState(GameOver.STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }

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
