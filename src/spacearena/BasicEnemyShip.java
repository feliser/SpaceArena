package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class BasicEnemyShip extends Ship {
    
    private static final double ROTFACTOR = 0.5;
    private static final double SQFIRERADIUS = 125000;
    private static final double MINROT = 1.0;
    private static final int MAXFIREDELAY = 12;
    
    private final int maxFireDelay = 25;
    private double newang;
    
    private int lookDelay;
    private int fireDelay;
    
    // collision polygon 
    private static final ArrayList<Point> polyPoints = new ArrayList<>(
        Arrays.asList(  new Point(0, -25), 
                        new Point(13, -7),
                        new Point(13, 12),
                        new Point(-13, 12),
                        new Point(-13, -7),
                        new Point(0, -25))
    );
    
    public BasicEnemyShip(double xPos, double yPos) {
        super(xPos, yPos, 4, 10, 25, polyPoints, 2, 1);
    }
    
    @Override
    public void update() {
        
        lookDelay--;
        // update where the ship should be looking
        if(lookDelay <= 0) {
            double f = Tools.drand(-7, 15);
            newang = Tools.pointTowards(xPos, yPos, Main.player.xPos + Main.player.xVel * f, Main.player.yPos + Main.player.yVel * f)+Tools.irand(-5, 5);
            newang %= 360;
            if(newang < 0) newang += 360;
            // set delay for when to update again
            lookDelay = Tools.irand(2, 10);
        }
        
        double pos = newang < ang ? (newang + 360.0 - ang) : (newang - ang);
        double neg = newang > ang ? (newang - 360.0 - ang) : (newang - ang);
        if(Math.abs(pos) < Math.abs(neg)) {
            double cur = Math.max(MINROT, pos * ROTFACTOR);
            rVel = Math.min(cur, pos);
        } else {
            double cur = Math.min(-MINROT, neg * ROTFACTOR);
            rVel = Math.max(cur, neg);
        }
        
        fireDelay--;
        double dist = Tools.sqDist(xPos, yPos, Main.player.xPos, Main.player.yPos);
        
        if(dist < SQFIRERADIUS) {
            thrusting = false;
            if(fireDelay <= 0) {
                fireDelay = MAXFIREDELAY;
                // add small variance to fire angle
                double fireang = Math.toRadians(ang+Tools.irand(-5, 5));
                Entities.addBullet(new GreenBullet(xPos, yPos, xVel+30.0*Math.sin(fireang), yVel-30.0*Math.cos(fireang), Math.toDegrees(fireang), this, 1));
            }
        } else {
            thrusting = true;
        }
        
        super.update();
    }
}
