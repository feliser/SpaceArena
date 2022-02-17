package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Ship extends Entity {
    
    private static final double BORDERFACTOR = 5.0;
    private static final int FLAMECOUNT = 5;
   
    private final double acceleration;
    private final double border;
    private final int maxVel;
    
    protected double sqMaxVel;
    protected boolean thrusting;
    protected int chargeDamage;
    protected int iframes;
    protected int health;
    
    public Ship(double xPos, double yPos, int health, int maxVel, int borderRadius, ArrayList<Point> polyPoints, int spriteIndex, double acceleration) {
        super(xPos, yPos, polyPoints, spriteIndex);
        this.health = health;
        this.maxVel = maxVel;
        // set squared max velocity
        this.sqMaxVel = maxVel * maxVel;
        this.acceleration = acceleration;
        // set squared border radius
        this.border = (Border.R1-borderRadius) * (Border.R1-borderRadius);
        this.chargeDamage = 1;
    }
    
    @Override
    public void update() {
        // add gray smoke exhaust effects at the back of the ship
        Exhausts.addParticle(xPos-10*Math.sin(Math.toRadians(ang)), yPos+10*Math.cos(Math.toRadians(ang)), 8, 1, 0.5, 0.5, 0.5, 0.25f);
        Exhausts.addParticle(xPos+xVel/2.0-10*Math.sin(Math.toRadians(ang)), yPos+yVel/2.0+10*Math.cos(Math.toRadians(ang)), 9, 1, 0.5, 0.5, 0.5, 0.25f);
        
        // apply thrust
        if(thrusting) {
            // increase x and y velocity
            xVel += acceleration *  Math.sin(rang);
            yVel += acceleration * -Math.cos(rang);
            
            // add orange smoke exhaust effects at the back of the ship
            double newX = Tools.rotXPos(xPos, rang, 10.0);
            double newY = Tools.rotYPos(yPos, rang, 10.0);
            Exhausts.addParticle(newX, newY, 10, 2, 1.0, 0.68, 0.26, 0.8);
            Exhausts.addParticle(newX+xVel/2.0, newY+yVel/2.0, 10, 2, 1.0, 0.7, 0.2, 0.8f);
        
            // spawn flame particles at the back of the ship
            for(int i = 0; i < FLAMECOUNT; i++) {
                // add randomness to position, size, lifetime, and velocity
                Particles.addParticle(newX+Tools.drand(-5,5), newY+Tools.drand(-5,5), xVel*0.5+Tools.frand(-2,2), yVel*0.5+Tools.frand(-2,2), new Color(1f,0.7f,0.2f,0.8f), Tools.irand(3,8), Tools.irand(1,4));
            }
        }     
        
        // cap velocity
        double hyp = xVel * xVel + yVel * yVel;
        if(hyp > sqMaxVel) {
            // scale down velocities according to ratio
            double div = sqMaxVel / hyp;
            xVel *= div;
            yVel *= div;
        }
        
        // repel ship if touching border
        double dist = xPos * xPos + yPos * yPos;
        if(dist > border) {
            // gets angle from origin to ship's position
            double dir = Math.atan2(xPos, yPos);
            xVel -= BORDERFACTOR * Math.sin(dir);
            yVel -= BORDERFACTOR * Math.cos(dir);
        }
        
        if(iframes > 0) iframes--;
        
        // call Entity's update method
        super.update();
    }
    
    @Override
    public void draw(Graphics2D g) {
        if((iframes/2) % 2 == 0) super.draw(g);
    }
    
    public int getHealth() {
        return health;
    }
     
    public void setHealth(int health) {
        this.health = health;
    }
    
    public void setChargeDamage(int chargeDamage) {
        this.chargeDamage = chargeDamage;
    }
}
