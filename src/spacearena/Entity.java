package spacearena;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

// represents an object with position, rotation, velocity, collision, and an associated image
public class Entity {
    
    // position and rotation
    protected double xPos;
    protected double yPos;
    // angle in degrees
    protected double ang;
    // angle in radians
    protected double rang;

    // velocities
    protected double xVel;
    protected double yVel;
    protected double rVel;
   
    // associated image
    protected int spriteIndex;
    
    // list of points that make up polygon
    private ArrayList<Point> original;
    private ArrayList<Point> updated;
        
    public Entity(double xPos, double yPos, ArrayList<Point> originalPoly, int spriteIndex) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.original = originalPoly;
        this.spriteIndex = spriteIndex;
        updated = new ArrayList<>();
    }
    
    public void draw(Graphics2D g) {
        Sprite s = Sprites.getSprite(spriteIndex);
        g.drawImage(s.getImage(), (int)xPos-s.getOffsetX(), (int)yPos-s.getOffsetY(), s.getWidth(), s.getHeight(), null);
        // System.out.println("Draw player: " + angle + " xPos " + xPos + " yPos " + yPos + " xVel " + xVel + " yVel " + yVel + " rVel " + rVel);
    }
    
    // DEBUG: draw collision polygon
    public void drawPoly(Graphics2D g) { 
        g.setColor(Color.YELLOW);
        for(int i = 1; i < updated.size(); i++) {
            g.setStroke(new BasicStroke(2));
            g.drawLine((int)updated.get(i).x, (int)updated.get(i).y, (int)updated.get(i-1).x, (int)updated.get(i-1).y);
        }
    }
    
    public void update() {
        // apply velocity
        xPos = xPos + xVel;
        yPos = yPos + yVel;
        
        // change angle
        ang = (ang + rVel) % 360.0;
        if(ang < 0.0) ang += 360.0;
        
        // set angle in radians
        rang = Math.toRadians(ang);
        
        // create updated polygon for collisions
        genUpdated();
    }
    
    // rotates and translates original polygon
    private void genUpdated() {
        updated.clear();
        for(int i = 0; i < original.size(); i++) {
            updated.add(Physics.rotateAround(xPos, yPos, original.get(i).x+xPos, original.get(i).y+yPos, rang));
        }
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public double getAng() {
        return ang;
    }

    public void setAng(double ang) {
        this.ang = ang;
    }
    
    public double getRang() {
        return rang;   
    }
    
    public void setRang(double rang) {
        this.rang = rang;
    }

    public double getXVel() {
        return xVel;
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    public double getRVel() {
        return rVel;
    }

    public void setRVel(double rVel) {
        this.rVel = rVel;
    }

    public ArrayList<Point> getOriginalPoly() {
        return original;
    }
    
    public void setOriginalPoly(ArrayList<Point> originalPoly) {
        this.original = originalPoly;
    }
    
    public ArrayList<Point> getUpdatedPoly() {
        return updated;
    }
    
    public void setUpdatedPoly(ArrayList<Point> updatedPoly) {
        this.updated = updatedPoly;
    }
    
    public int getSpriteIndex() {
        return spriteIndex;
    }
    
    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }
}
