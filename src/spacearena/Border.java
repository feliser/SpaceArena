package spacearena;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Border {

    public static final double DR = 12250000.0;
    
    public static final int R1 = 2500;
    public static final int R2 = 2510;
    public static final int R3 = 3500;
    
    public static final Color C1 = new Color(1f, 0f, 0f, 1.0f);
    public static final Color C2 = new Color(1f, 0f, 0f, 0.5f);
    public static final Color C3 = new Color(1f, 0f, 0f, 0.2f);
    
    public static void draw(Graphics2D g) {
        AffineTransform old = (AffineTransform) g.getTransform();
        g.setTransform(Camera.getTranslation(old));
        // draw border circle
        g.setColor(C1);
        g.setStroke(new BasicStroke(5));
        g.drawOval(-R1, -R1, R1*2, R1*2);
        
        g.setColor(C2);
        g.setStroke(new BasicStroke(20));
        g.drawOval(-R2, -R2, R2*2, R2*2);
        
        g.setColor(C3);
        g.setStroke(new BasicStroke(1960));
        g.drawOval(-R3, -R3, R3*2, R3*2);
        // reset translation
        g.setTransform(old);
    }
    
}
