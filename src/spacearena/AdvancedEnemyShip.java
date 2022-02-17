package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvancedEnemyShip extends Ship {
    
    private static final double ROTFACTOR = 0.2;
    private static final double SQFIRERADIUS = 400000;
    private static final double MINROT = 1;
    private static final int MAXFIREDELAY = 6;
    
    private final int maxFireDelay = 25;
    private double newang;
    
    private int lookDelay;
    private int fireDelay;
    
    // collision polygon 
    private static final ArrayList<Point> polyPoints = new ArrayList<>(
        Arrays.asList(  new Point(0, -35), 
                        new Point(25, -7),
                        new Point(25, 20),
                        new Point(-25, 20),
                        new Point(-25, -7),
                        new Point(0, -35))
    );
    
    public AdvancedEnemyShip(double xPos, double yPos) {
        super(xPos, yPos, 5, 15, 25, polyPoints, 6, 0.7);
    }
    
    @Override
    public void update() {
        
        lookDelay--;
        // update where the ship should be looking
        if(lookDelay <= 0) {
            double f = Tools.drand(-25, 25);
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
                Entities.addBullet(new YellowBullet(xPos, yPos, xVel+20.0*Math.sin(fireang), yVel-20.0*Math.cos(fireang), Math.toDegrees(fireang), this, 1));
                fireang = Math.toRadians(ang+Tools.irand(-5, 5));
                Entities.addBullet(new YellowBullet(xPos-12.0*Math.cos(fireang), yPos-12.0*Math.sin(fireang), xVel+15.0*Math.sin(fireang), yVel-15.0*Math.cos(fireang), Math.toDegrees(fireang), this, 1));
                fireang = Math.toRadians(ang+Tools.irand(-5, 5));
                Entities.addBullet(new YellowBullet(xPos+12.0*Math.cos(fireang), yPos+12.0*Math.sin(fireang), xVel+15.0*Math.sin(fireang), yVel-15.0*Math.cos(fireang), Math.toDegrees(fireang), this, 1));
            }
        } else thrusting = true;
        
        super.update();
        
        if(dist > SQFIRERADIUS / 4) {
            double newX = Tools.rotXPos(xPos, rang, 15.0);
            double newY = Tools.rotYPos(yPos, rang, 15.0);
            Exhausts.addParticle(newX, newY, 18, 1, 0.9f, 0.9f, 0.13f, 0.7f);
            Exhausts.addParticle(newX+xVel/2.0, newY+yVel/2.0, 18, 1, 0.88f, 0.88f, 0.13f, 0.8f);
        } else {
            double newX = Tools.rotXPos(xPos, rang, 15.0);
            double newY = Tools.rotYPos(yPos, rang, 15.0);
            Exhausts.addParticle(newX, newY, 18, 1, 0.5f, 0.5f, 0.5f, 0.7f);
            Exhausts.addParticle(newX+xVel/2.0, newY+yVel/2.0, 18, 1, 0.5f, 0.5f, 0.5f, 0.8f);
        }
    }
}
