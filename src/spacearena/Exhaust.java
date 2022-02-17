package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;

public class Exhaust {
    
    private final double xPos;
    private final double yPos;
    private final int decay;
    private int size;
    
    private Color color;

    public Exhaust(double xPos, double yPos, int size, int decay, Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.decay = decay;
        this.color = color;
    }
    
    public void update() {
        this.size -= decay;
    }
    
    public void draw(Graphics2D g) {
        g.setColor(color);
        // draw outer and inner oval
        g.fillOval((int)xPos-size, (int)yPos-size, size*2, size*2);
        g.fillOval((int)xPos-size+2, (int)yPos-size+2, size*2-4, size*2-4);
        // decrease opacity
        color = new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, Math.max(0f, (color.getAlpha() - 10f) / 255f));
    }
    
    public int getSize() {
        return size;
    }
}
