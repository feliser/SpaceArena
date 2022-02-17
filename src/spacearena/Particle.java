package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle {
    
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
        
    private Color color;
    private int lifetime;
    private int size;

    public Particle(double xPos, double yPos, double xVel, double yVel, Color color, int lifetime, int size) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.color = color;
        this.lifetime = lifetime;
        this.size = size;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
        lifetime--;
    }
    
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect((int)xPos - size, (int)yPos - size, size * 2, size * 2);
    } 
    
    public int getLifetime() {
        return lifetime;
    }
}
