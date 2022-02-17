package spacearena;

import java.util.ArrayList;

public class Bullet extends Entity {
    
    private Ship owner;
    private int damage;
    
    public int lifetime;
    
    public Bullet(double xPos, double yPos, ArrayList<Point> original, int spriteIndex, double xVel, double yVel, double ang, Ship owner, int damage) {
        super(xPos, yPos, original, spriteIndex);
        this.xVel = xVel;
        this.yVel = yVel;
        this.ang = ang;
        this.owner = owner;
        this.damage = damage;
    }
    
    @Override
    public void update() {
        lifetime++;
        super.update();
    }
    
    public Ship getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }
}
