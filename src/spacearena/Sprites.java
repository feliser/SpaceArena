package spacearena;

import java.io.IOException;
import java.util.ArrayList;

public class Sprites {
    
    private static ArrayList<Sprite> sprites;
    
    public static void init() {
        sprites = new ArrayList<>();
        
        try {
            sprites.add(new Sprite("redship.png", 26, 48, 13, 28));     // 0 - player
            sprites.add(new Sprite("redbullet.png", 4, 16, 2, 8));      // 1 - player's bullet
            sprites.add(new Sprite("greenship.png", 26, 48, 13, 28));   // 2 - basic enemy
            sprites.add(new Sprite("greenbullet.png", 4, 16, 2, 8));    // 3 - basic enemy's bullet
            sprites.add(new Sprite("yellowbullet.png", 4, 16, 2, 8));   // 4 - player's charyege bullet
            sprites.add(new Sprite("blueship.png", 30, 55, 15, 30));    // 5 - charging enemy
            sprites.add(new Sprite("yellowship.png", 60, 80, 30, 45));  // 6 - advanced enemy
            sprites.add(new Sprite("pinkship.png", 60, 80, 30, 45));    // 7 - minelayer enemy
            sprites.add(new Sprite("minebasic.png", 80, 120, 42, 50));   // 8 - inactive mine
            sprites.add(new Sprite("mineblink.png", 80, 120, 42, 50));   // 9 - active mine
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    // get an image at a certain index
    public static Sprite getSprite(int index) {
        return sprites.get(index);
    }
}
