package spacearena;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Mine extends Entity {
    
    private double border;
    private double sqMaxVel;
    
    public Ship owner;
    public int lifetime;
    
    private static final ArrayList<Point> polyPoints = new ArrayList<>(
        Arrays.asList(  new Point(-10, 10), 
                        new Point(20, 10),
                        new Point(20, -20),
                        new Point(-10, -20),
                        new Point(-10, 10))
    );
    
    public Mine(double xPos, double yPos, double xVel, double yVel, double ang, double rVel, Ship owner, int lifetime) {
        super(xPos, yPos, polyPoints, 8);
        this.xVel = xVel;
        this.yVel = yVel;
        this.rVel = rVel;
        this.owner = owner;
        this.lifetime = lifetime;
        this.ang = ang;
        this.border = (Border.R1-25)*(Border.R1-25);
        this.sqMaxVel = 225.0;
    }

    @Override
    public void update() {
        lifetime--;
        
        // change sprite to blink every once and a while
        if((lifetime/5)%5==0) spriteIndex = 9;
        else spriteIndex = 8;
        
        // repel from border
        double dist = xPos * xPos + yPos * yPos;
        if(dist > border) {
            // gets angle from origin to mine's position
            double dir = Math.atan2(xPos, yPos);
            xVel -= 5.0 * Math.sin(dir);
            yVel -= 5.0 * Math.cos(dir);
        }
        
          // cap velocity
        double hyp = xVel * xVel + yVel * yVel;
        if(hyp > sqMaxVel) {
            // scale down velocities according to ratio
            double div = sqMaxVel / hyp;
            xVel *= div;
            yVel *= div;
        }
        
        super.update();
    }
    
    public void detonate() {
        // create pink explosion effect
        for(int i = 0; i < 160; i++) {
            double x = xPos + Tools.drand(-5, 5);
            double y = yPos + Tools.drand(-5, 5);   
            double xv = xVel + Tools.drand(-10, 10);
            double yv = yVel + Tools.drand(-10, 10);   
            Color c = new Color((255+Tools.irand(-50,0))/255f,(69+Tools.irand(-40,90))/255f,(254+Tools.irand(-30,0))/255f,Tools.frand(0.1f,0.8f));
            Particles.addParticle(x, y, xv, yv, c, Tools.irand(5, 35), Tools.irand(3, 12));
        }
    }
}
