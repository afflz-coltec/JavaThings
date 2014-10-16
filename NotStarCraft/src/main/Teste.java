package main;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.BasicGame;
 
public class Teste extends BasicGame{
        private UnicodeFont uFont = null;
        private String fontPath = "Fonts/Interstate-Regular.ttf";
        private TextField text1;
        private TextField text2;
       
        public Teste() {
                super("Test");
        }
        @Override
        public void init(GameContainer container) throws SlickException {
//                uFont = new UnicodeFont(fontPath,13,false,false);
//                uFont.addAsciiGlyphs();   //Add Glyphs
//                uFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
//                uFont.loadGlyphs();  //Load Glyphs
                text1 = new TextField(container, container.getDefaultFont(), 50,50,100,25);
                text2 = new TextField(container, container.getDefaultFont(), 155,50,100,25);
                
                text1.setBackgroundColor(Color.red);
                text1.setTextColor(Color.black);
        }
        @Override
        public void update(GameContainer gc, int delta) throws SlickException {
                System.out.println(text1.getText());
        }
        @Override
        public void render(GameContainer gc, Graphics g) throws SlickException {
                text1.render(gc, g);
                text2.render(gc, g);
        }
        public static void main(String[] args) throws SlickException  {
                Teste g = new Teste();
                AppGameContainer gc = new AppGameContainer(g);
                gc.setDisplayMode(500, 500, false);
                gc.start();
        }
}