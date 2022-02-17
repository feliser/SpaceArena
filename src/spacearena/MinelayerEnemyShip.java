package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class MinelayerEnemyShip extends Ship {
    
    private static final double ROTFACTOR = 0.5;
    private static final double SQFIRERADIUS = 125000;
    private static final double MINROT = 1.0;
    private static final int MAXFIREDELAY = 190;
    
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
    
    public MinelayerEnemyShip(double xPos, double yPos) {
        super(xPos, yPos, 10, 10, 25, polyPoints, 7, 0.3);
    }
    
    @Override
    public void update() {
        
        lookDelay--;
        // update where the ship should be looking
        if(lookDelay <= 0) {
            double f = Tools.drand(85, 105);
            newang = Tools.pointTowards(xPos, yPos, Main.player.xPos + Main.player.xVel * f, Main.player.yPos + Main.player.yVel * f)+Tools.irand(-5, 5);
            newang %= 360;
            if(newang < 0) newang += 360;
            // set delay for when to update again
            lookDelay = Tools.irand(1, 3);
            
            if(fireDelay <= 0) {
                fireDelay = MAXFIREDELAY;
                // add small variance to fire angle
                Entities.addMine(new Mine(xPos, yPos, xVel * Tools.drand(-0.3, 1.0) + Tools.drand(-3, 3), yVel * Tools.drand(-0.3, 1.0) + Tools.drand(-3, 3), ang, (Math.abs(xVel) + Math.abs(yVel)) * Tools.drand(0.05, 0.1), this, 200));
            }
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
        thrusting = true;
        
        super.update();
    }
}
