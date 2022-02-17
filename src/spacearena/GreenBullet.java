package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class GreenBullet extends Bullet {
        
    private static final ArrayList<Point> polyPoints = new ArrayList<>(
        Arrays.asList(  new Point(-2, -8), 
                        new Point(2, -8),
                        new Point(2, 16),
                        new Point(-2, 16),
                        new Point(-2, -8))
    );
    
    public GreenBullet(double xPos, double yPos, double xVel, double yVel, double ang, Ship owner, int damage) {
        super(xPos, yPos, polyPoints, 3, xVel, yVel, ang, owner, damage);
    }
}
