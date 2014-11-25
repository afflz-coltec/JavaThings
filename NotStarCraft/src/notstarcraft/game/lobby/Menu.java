/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notstarcraft.game.lobby;

import notstarcraft.game.BattleField;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Menu extends BasicGameState {

    public static final int STATE_ID = 1;
    
    private Image background;
    
    @Override
    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        background = new Image("res/images/menu-bg.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
        Input input = container.getInput();
        
        if( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ) {
            int x = input.getMouseX();
            int y = input.getMouseY();
            
            if( x >= 60 && x <= 185 && y >= 230 && y <= 295 ) {
                game.getState(BattleField.STATE_ID).init(container, game);
                game.enterState(BattleField.STATE_ID);
            }
            else if( x >= 60 && x <= 185 && y >= 305 && y <= 355 ) {
                System.exit(0);
            }
        }
        
    }

}
