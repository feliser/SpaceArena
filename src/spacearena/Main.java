package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Main {
    
    public static boolean debug = false;
    public static boolean menu = true;
    
    private static Frame window;
    private static int startDelay;
    public static final String imgPrefix = "/img/";
    public static Player player;
    
    
    public static void main(String[] args) {        
        Sprites.init();
        Entities.init();
        Particles.init();
        Exhausts.init();
        Stars.init();
        Score.init();
        
        player = new Player();
        Entities.shipList.add(player);
        player.setHealth(0);
        
        Frame.init();
    }
    
    public static void start() {
        // reset
        Score.init();
        Spawning.init();
        Entities.init();
        Particles.init();
        Exhausts.init();
        
        // add player
        player = new Player();
        Entities.shipList.add(player);
    }
    
    public static void lose() {
        menu = true;
        Frame.firePressed = false;
        player.xVel = 0;
        player.yVel = 0;
        startDelay = 50;
        Score.saveScore();
    }
    
    public static void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
        // starts
        Stars.draw(g);
       
        Border.draw(g);
        Exhausts.draw(g);
        Particles.draw(g);
        Entities.draw(g);
        HUD.draw(g);
        Score.draw(g);
    }
    
    public static void update() {
        // check if they are on main menu or not
        if(menu) {
            // if space is pressed, start the game
            startDelay--;
            if(Frame.firePressed && startDelay < 0) {
                start();
                menu = false;
            }
        } else {
            // if in game, update the score and scroll
            Score.update();
            Camera.update();   
        } 
        
        // update regardless for visual effects when player has died
        Spawning.update();
        Entities.update();
        Exhausts.update();
        Particles.update();
    }
}
