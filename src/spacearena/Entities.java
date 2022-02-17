package spacearena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

// handles updating, drawing, and deleting of all entities
public class Entities {
    
    public static ArrayList<Ship> shipList;
    public static ArrayList<Bullet> bulletList;
    public static ArrayList<Mine> mineList;
    
    public static void init() {
        shipList = new ArrayList<>();
        bulletList = new ArrayList<>();
        mineList = new ArrayList<>();
    }
    
    public static void addShip(Ship e) {
        shipList.add(e);
    }
    
    public static void addBullet(Bullet b) {
        bulletList.add(b);
    }
    
    public static void addMine(Mine m) {
        mineList.add(m);
    }
    
    public static void update() {
        for(Ship e : shipList) e.update();
        
        // update bullets
        ArrayList<Bullet> removeBullets = new ArrayList<>();
        ArrayList<Ship> removeShips = new ArrayList<>();
        ArrayList<Mine> removeMines = new ArrayList<>();
        
        for(Bullet e : bulletList) {
            e.update();
            if(e.xPos * e.xPos + e.yPos * e.yPos > Border.DR) removeBullets.add(e);
        }
        
        for(Mine m : mineList) {
            m.update();
            // detonate mine if its fuse is up
            if(m.lifetime == 1) m.detonate();
            if(m.lifetime == 0) removeMines.add(m);
        }
        
        // check every ship against every bullet for collisions
        for(Ship s : shipList) {
            if(s.health==0) removeShips.add(s);
            for(Bullet b : bulletList) {
                // don't allow a bullet to hit its owner
                // don't allow a bullet to hit an invincible ship
                if(b.getOwner() == s || s.iframes > 0) continue;
                if(b.lifetime > 1000) removeBullets.add(b);
                // if they are colliding, delete bullet and apply damage
                if(Physics.isColliding(s, b)) {
                    s.setHealth(s.getHealth() - b.getDamage());
                    removeBullets.add(b);
                    // if ship is dead remove it from shiplist
                    if(s.getHealth() <= 0) removeShips.add(s);
                    // give the hit ship invincibility frames
                    s.iframes = 30;
                    // create small explosion effect with particles
                    for(int i = 0; i < 20; i++) {
                        double x = s.xPos + Tools.drand(-5, 5);
                        double y = s.yPos + Tools.drand(-5, 5);
                        double xv = s.xVel + Tools.drand(-2, 2);
                        double yv = s.yVel + Tools.drand(-2, 2);
                        Color c = new Color((226+Tools.irand(-25,25))/255f,(88+Tools.irand(-30,80))/255f,(34+Tools.irand(-20,20))/255f,Tools.frand(0.1f,0.8f));
                        Particles.addParticle(x, y, xv, yv, c, Tools.irand(5, 20), Tools.irand(2, 7));
                    }
                }
            }
        }
        
        // check ship against ship collisions
        for(Ship a : shipList) {
            for(Ship b : shipList) {
                if(a == b || a.iframes > 25 || b.iframes > 25) continue;
                if(Physics.isColliding(a, b)) {
                    // apply damage and iframes if necessary
                    if(a.iframes <= 0) {
                        a.health -= b.chargeDamage;
                        a.iframes = 30;
                    }
                    if(b.iframes <= 0) {
                        b.health -= a.chargeDamage;
                        b.iframes = 30;
                    }
                    
                    // create sparks at ship a
                    for(int i = 0; i < 10; i++) {
                        double x = a.xPos + Tools.drand(-5, 5);
                        double y = a.yPos + Tools.drand(-5, 5);
                        double xv = a.xVel + Tools.drand(-10, 10);
                        double yv = a.yVel + Tools.drand(-10, 10);
                        Color c = new Color((226+Tools.irand(-25,25))/255f,(226+Tools.irand(-25,25))/255f,(34+Tools.irand(-20,20))/255f,Tools.frand(0.1f,0.8f));
                        Particles.addParticle(x, y, xv, yv, c, Tools.irand(5, 15), Tools.irand(2, 5));
                    }
                    
                    // create sparks at ship b
                    for(int i = 0; i < 10; i++) {
                        double x = b.xPos + Tools.drand(-5, 5);
                        double y = b.yPos + Tools.drand(-5, 5);
                        double xv = b.xVel + Tools.drand(-10, 10);
                        double yv = b.yVel + Tools.drand(-10, 10);
                        Color c = new Color((226+Tools.irand(-25,25))/255f,(226+Tools.irand(-25,25))/255f,(34+Tools.irand(-20,20))/255f,Tools.frand(0.1f,0.8f));
                        Particles.addParticle(x, y, xv, yv, c, Tools.irand(5, 15), Tools.irand(2, 5));
                    }
                    
                    if(a.getHealth() <= 0) removeShips.add(a);
                    if(b.getHealth() <= 0) removeShips.add(b);
                    
                    // repel the ships
                    double anga = Math.toRadians(Tools.pointTowards(a.xPos-a.xVel*5.0, a.yPos-a.yVel*5.0, b.xPos-b.xVel*5.0, b.yPos-b.yVel*5.0));
                    double angb = Math.toRadians(Tools.pointTowards(b.xPos-b.xVel*5.0, b.yPos-b.yVel*5.0, a.xPos-a.xVel*5.0, a.yPos-a.yVel*5.0));

                    a.xVel +=-7.0*Math.sin(anga);
                    a.yVel += 7.0*Math.cos(anga);
                    b.xVel +=-7.0*Math.sin(angb);
                    b.yVel += 7.0*Math.cos(angb);
                    
                    // if this is a charging ship, explode it
                    if(a instanceof ChargingEnemyShip) removeShips.add(a);        
                    if(b instanceof ChargingEnemyShip) removeShips.add(b);
                }
            }
        }
        
        // check for ship against mine collisions
        for(Ship s : shipList) {
            for(Mine m : mineList) {
                // minelayer cannot collide with its own mines
                if(m.owner == s) continue;
                
                if(Physics.isColliding(s, m)) {
                    // mine will make some particles
                    for(int i = 0; i < 10; i++) {
                        double x = m.xPos + Tools.drand(-5, 5);
                        double y = m.yPos + Tools.drand(-5, 5);   
                        double xv = m.xVel + Tools.drand(-2, 2);
                        double yv = m.yVel + Tools.drand(-2, 2);   
                        Color c = new Color((255+Tools.irand(-50,0))/255f,(69+Tools.irand(-40,90))/255f,(254+Tools.irand(-30,0))/255f,Tools.frand(0.1f,0.8f));
                        Particles.addParticle(x, y, xv, yv, c, Tools.irand(5, 35), Tools.irand(3, 12));
                    }
                    
                    // repel ship and mine
                    double anga = Math.toRadians(Tools.pointTowards(s.xPos-s.xVel*5.0, s.yPos-s.yVel*5.0, m.xPos-m.xVel*5.0, m.yPos-m.yVel*5.0));
                    double angb = Math.toRadians(Tools.pointTowards(m.xPos-m.xVel*5.0, m.yPos-m.yVel*5.0, s.xPos-s.xVel*5.0, s.yPos-s.yVel*5.0));
                    s.xVel +=-3.0*Math.sin(anga);
                    s.yVel += 3.0*Math.cos(anga);
                    m.xVel +=-3.0*Math.sin(angb);
                    m.yVel += 3.0*Math.cos(angb);
                }
            }
        }
        
        // damage ships in the area
        for(Ship s : shipList) {
            for(Mine m : mineList) {
                if(m.lifetime != 1 || s.iframes > 0) continue;
                
                double dist = Tools.sqDist(s.xPos, s.yPos, m.xPos, m.yPos);
                if(dist < 90000.0) {
                    s.health--;
                    s.iframes = 30;
                    // kill ship
                    if(s.health == 0) removeShips.add(s);
                }
            }
        }
        
        // list of ships that exploded on this tick
        for(Ship s : removeShips) {
            shipList.remove(s);
            // if the player died, call the lose function
            if(s == Main.player) Main.lose();
            // create large explosion effect with particles
            for(int i = 0; i < 60; i++) {
                double x = s.xPos + Tools.drand(-5, 5);
                double y = s.yPos + Tools.drand(-5, 5);   
                double xv = s.xVel + Tools.drand(-6, 6);
                double yv = s.yVel + Tools.drand(-6, 6);
                Color c = new Color((226+Tools.irand(-50,25))/255f,(88+Tools.irand(-40,90))/255f,(34+Tools.irand(-20,20))/255f,Tools.frand(0.1f,0.8f));
                Particles.addParticle(x, y, xv, yv, c, Tools.irand(5, 30), Tools.irand(3, 10));
            }
        }
        
        for(Bullet b : removeBullets) bulletList.remove(b);
        for(Mine m : removeMines) mineList.remove(m);
    }
    
    public static void draw(Graphics2D g) {
        AffineTransform old = g.getTransform();
        // apply scroll
        AffineTransform translated = Camera.getTranslation(old);
        
        // draw bullets
        for(Bullet b : bulletList) {
            g.setTransform(Camera.getRotated(translated, b.getXPos(), b.getYPos(), b.getRang()));
            Sprite s = Sprites.getSprite(b.getSpriteIndex());
            // draw image
            b.draw(g);
            
            if(Main.debug) {
                g.setTransform(translated);
                b.drawPoly(g);
            }
        }
        
        // draw mines
        for(Mine m : mineList) {
            g.setTransform(Camera.getRotated(translated, m.getXPos(), m.getYPos(), m.getRang()));
            Sprite s = Sprites.getSprite(m.getSpriteIndex());
            // draw image
            m.draw(g);
            
            // draw imapct radius if lifetime is low
            if(m.lifetime <= 25) {
                if(m.lifetime <= 5) g.setColor(new Color(255f/255f, 69f/255f, 254f/255f, 0.7f));
                else g.setColor(new Color(255f/255f, 69f/255f, 254f/255f, 0.2f));
                
                g.setTransform(translated);
                g.fillOval((int)m.xPos-150, (int)m.yPos-150, 300, 300);
            }
            
            // draw collision polygokn if in debug mode
            if(Main.debug) {
                g.setTransform(translated);
                m.drawPoly(g);
            }
        }
        
        // draw ships
        for(Ship e : shipList) {

            g.setTransform(Camera.getRotated(translated, e.getXPos(), e.getYPos(), e.getRang()));
            Sprite s = Sprites.getSprite(e.getSpriteIndex());
            // draw image
            e.draw(g);

            // draw collision polygon if in debug mode
            if(Main.debug) {
                g.setTransform(translated);
                e.drawPoly(g);
            }
        }
        
        g.setTransform(old);
    }
}
