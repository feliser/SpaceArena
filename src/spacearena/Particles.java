package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Particles {
    
    public static ArrayList<Particle> particleList;
    
    public static void init() {
        particleList = new ArrayList<>();
    }

    public static void addParticle(double xPos, double yPos, double xVel, double yVel, Color color, int lifetime, int size) {
        particleList.add(new Particle(xPos, yPos, xVel, yVel, color, lifetime, size));
    }
    
    public static void update() {
        ArrayList<Particle> removeList = new ArrayList<>();
        // update every particle
        for(Particle p : particleList) {
            p.update();
            // delete particle if it has expired
            if(p.getLifetime() < 0) removeList.add(p);
        }
        
        for(Particle p : removeList) particleList.remove(p);
    }
    
    public static void draw(Graphics2D g) {
        AffineTransform old = g.getTransform();
        // get translated
        g.setTransform(Camera.getTranslation(old));
        // draw every particle
        for(Particle p : particleList) {
            p.draw(g);
        }
    
        g.setTransform(old);
    }
}
