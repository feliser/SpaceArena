package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class YellowBullet extends Bullet {
        
   private static final ArrayList<Point> polyPoints = new ArrayList<>(
        Arrays.asList(  new Point(-2, -8), 
                        new Point(2, -8),
                        new Point(2, 16),
                        new Point(-2, 16),
                        new Point(-2, -8))
    );
    
    public YellowBullet(double xPos, double yPos, double xVel, double yVel, double ang, Ship owner, int damage) {
        super(xPos, yPos, polyPoints, 4, xVel, yVel, ang, owner, damage);
    }
}
