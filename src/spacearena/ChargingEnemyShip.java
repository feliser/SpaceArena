package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class ChargingEnemyShip extends Ship {
    
    private static final double ROTFACTOR = 0.5;
    private static final double SQFIRERADIUS = 62500;
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
    
    public ChargingEnemyShip(double xPos, double yPos) {
        super(xPos, yPos, 4, 18, 25, polyPoints, 5, 1);
        this.chargeDamage = 2;
    }
    
    @Override
    public void update() {
        
        lookDelay--;
        thrusting = true;
        // update where the ship should be looking
        double dist = Tools.sqDist(xPos, yPos, Main.player.xPos, Main.player.yPos);

        if(lookDelay <= 0 && dist >= SQFIRERADIUS) {
            double f = Tools.drand(-25, 25);
            newang = Tools.pointTowards(xPos, yPos, Main.player.xPos + Main.player.xVel * f, Main.player.yPos + Main.player.yVel * f)+Tools.irand(-5, 5);
            newang %= 360;
            if(newang < 0) newang += 360;
            // set delay for when to update again
            lookDelay = Tools.irand(1, 5);
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
        
        if(dist < SQFIRERADIUS) {
            sqMaxVel = 625.0;
            lookDelay = 5;
            
            double newX = Tools.rotXPos(xPos, rang, 10.0);
            double newY = Tools.rotYPos(yPos, rang, 10.0);
            Exhausts.addParticle(newX, newY, 16, 2, 0.27f, 0.64f, 0.58f, 0.8f);
            Exhausts.addParticle(newX+xVel/2.0, newY+yVel/2.0, 12, 2, 0.27f, 0.64f, 0.58f, 0.8f);
        } else {
            sqMaxVel = 324.0;
        }
        
        super.update();
        
 
    }
}
