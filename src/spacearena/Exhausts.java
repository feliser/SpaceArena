package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Exhausts {
 
    private static ArrayList<Exhaust> exhaustList;
    
    public static void init() {
        exhaustList = new ArrayList<>();
    }
    
    public static double rand(double min, double max) {
        return (Math.random()*(max-min))+min;
    }
    
    public static void addParticle(double xPos, double yPos, int size, int decay, double r, double g, double b, double a) {
        Color c = new Color((float)Math.max((r-rand(0, 0.1f)),0), (float)Math.max((g-rand(0, 0.1f)),0), (float)Math.max((b-rand(0, 0.1f)),0), (float)a);
        exhaustList.add(new Exhaust(xPos, yPos, size + (int)rand(-2, 2), decay, c));
    }
    
    public static void update() {
        // update alive particles
        for(Exhaust p : exhaustList) p.update();
        // filter out expired particles
        ArrayList<Exhaust> newList = new ArrayList<>();
        for(Exhaust p : exhaustList) if(p.getSize() >= 1) newList.add(p); 
        exhaustList = newList;
        
       
    }
    
    // draw all particles
    public static void draw(Graphics2D g) {        
        AffineTransform old = g.getTransform();
        // apply scroll
        g.setTransform(Camera.getTranslation(old));
        for(Exhaust p : exhaustList) p.draw(g);
        // reset translation
        g.setTransform(old);
    }
}
