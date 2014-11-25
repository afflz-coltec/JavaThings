/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notstarcraft;

import notstarcraft.game.BattleField;
import notstarcraft.game.lobby.GameOver;
import notstarcraft.game.lobby.Menu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Pedro
 */
public class NotStarCraft extends StateBasedGame {
    
    /**
     * Main class to control the states of the game.
     * @param name 
     */
    public NotStarCraft(String name) {
        super(name);
        this.addState(new Menu());
        this.addState(new GameOver());
        this.addState(new BattleField());
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(Menu.STATE_ID).init(gc, this);
        this.enterState(Menu.STATE_ID);
    }
    
}
