package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Stars {
    
    private static final int STARCOUNT = 1000;
    private static ArrayList<Point> starList;
    
    public static int rand(int min, int max) {
        return (int)(Math.random()*(double)(max-min+1))+min;
    }
    
    public static void init() {
        starList = new ArrayList<>();
        
        for(int i = 1; i <= STARCOUNT; i++) {
            starList.add(new Point(rand(-3500, 3500), rand(-3500, 3500)));
        }
    }
    
    public static void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        for(Point p : starList) {
            int x = (int)p.x + (int)Camera.getTotalX();
            int y = (int)p.y + (int)Camera.getTotalY();
            
            if(x >= 0 && x <= Frame.WIDTH && y >= 0 && y <= Frame.HEIGHT) {
                if(rand(1, 1000) == 1) {
                    g.drawRect(x-3, y, 7, 1);
                    g.drawRect(x, y-3, 1, 7);
                } else {
                    g.drawRect(x, y, 1, 1);
                }
            }
        }
    }
}
