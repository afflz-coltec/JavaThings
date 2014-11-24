/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notstarcraft;

import notstarcraft.game.BattleField;
import notstarcraft.game.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Pedro
 */
public class NotStarCraft extends StateBasedGame {

    private int gameWidth;
    private int gameHeight;
    
    public NotStarCraft(String name, int gameWidth, int gameHeight) {
        super(name);
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.addState(new BattleField());
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(2).init(gc, this);
        this.enterState(2);
    }
    
}
