package spacearena;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Ship {
    
    private static final double ROTFACTOR = 0.25;
    private static final double MINROT = 25.0;
    private static final int MAXFIREDELAY = 8;
    
    public static final int CHARGECOST = 48;
    public static final int MAXCHARGE = 240;
    
    public int healthTimer;
    public int secondHealthTimer;
    
    private int fireDelay;
    private int charge;
    
    // collision polygon 
    private static final ArrayList<Point> polyPoints = new ArrayList<>(
        Arrays.asList(  new Point(0, -25), 
                        new Point(13, -7),
                        new Point(13, 12),
                        new Point(-13, 12),
                        new Point(-13, -7),
                        new Point(0, -25))
    );
    
    public Player() {
        super(0, 0, 5, 13, 25, polyPoints, 0, 0.5);
        charge = 0;
    }
    
    @Override
    public void update() {
        double newang = Math.toDegrees(-Math.atan2(Frame.mouseX - Camera.getActualX(this), Frame.mouseY - Camera.getActualY(this))) + 180.0;
        // rotate towards mouse cursor
        // see if it is shorter to rotate clockwise or counter-clockwise
        double pos = newang < ang ? (newang + 360.0 - ang) : (newang - ang);
        double neg = newang > ang ? (newang - 360.0 - ang) : (newang - ang);
        if(Math.abs(pos) < Math.abs(neg)) {
            double cur = Math.max(MINROT, pos * ROTFACTOR);
            rVel = Math.min(cur, pos);
        } else {
            double cur = Math.min(-MINROT, neg * ROTFACTOR);
            rVel = Math.max(cur, neg);
        }
        
        // thrust if user is pressing forward
        thrusting = Frame.upPressed;
        
        fireDelay--;
        charge++;
        
        // cap charge
        if(charge > MAXCHARGE) {
            charge = MAXCHARGE;
        }
        
        // if health less than max, refill health
        if(health < 5) {
            secondHealthTimer++;
            if(secondHealthTimer == 10) {
                healthTimer++;
                secondHealthTimer = 0;
            }
        }
        
        if(healthTimer == 44) {
            secondHealthTimer = 0;
            healthTimer = 0;
            health++;
        }
        
        // check if we can fire a bullet
        if(Frame.firePressed && fireDelay <= 0) {
            Entities.addBullet(new RedBullet(xPos, yPos, xVel+25.0*Math.sin(rang), yVel-25.0*Math.cos(rang), ang, this, 1));
            fireDelay = MAXFIREDELAY;
            
            // fire side cannons if charge is high enough
            if(charge >= CHARGECOST) {
                charge -= CHARGECOST;
                // create yellow bullets with double damage
                Entities.addBullet(new YellowBullet(xPos-12.0*Math.cos(rang), yPos-12.0*Math.sin(rang), xVel+30.0*Math.sin(rang), yVel-30.0*Math.cos(rang), ang, this, 2));
                Entities.addBullet(new YellowBullet(xPos+12.0*Math.cos(rang), yPos+12.0*Math.sin(rang), xVel+30.0*Math.sin(rang), yVel-30.0*Math.cos(rang), ang, this, 2));
            }
        }
       
        super.update();
    }
    
    public int getCharge() {
        return charge;
    }
}
