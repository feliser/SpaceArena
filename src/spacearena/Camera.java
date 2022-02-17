package spacearena;

import java.awt.geom.AffineTransform;

public class Camera {
    
    // constants
    public static final int WIDTHOFFSET = Frame.WIDTH / 2;
    public static final int HEIGHTOFFSET = Frame.HEIGHT / 2;
    public static final double SCROLLFACTOR = 0.25;
    public static final double MOUSEFACTORX = 100;
    public static final double MOUSEFACTORY = 100;
    public static final double SCROLLMIN = 1.0;
    
    private static double scrollX;
    private static double scrollY;
    private static double mouseX;
    private static double mouseY;
    
    // updates scroll values based on player and mouse location
    public static void update() {
        // update player scrolling
        double dx = -(Main.player.getXPos()+Main.player.getXVel()*7.0) - scrollX;
        double dy = -(Main.player.getYPos()+Main.player.getYVel()*7.0) - scrollY;
        double moveX = dx * SCROLLFACTOR;
        double moveY = dy * SCROLLFACTOR;
        
        // cap scrolling
        if(dx < 0) scrollX += Math.max(dx, moveX);
        else scrollX += Math.min(dx, moveX);
        if(dy < 0) scrollY += Math.max(dy, moveY);
        else scrollY += Math.min(dy, moveY);
        
        // update mouse scrolling
        dx = MOUSEFACTORX * ((WIDTHOFFSET  - (double)Frame.mouseX) /  WIDTHOFFSET) - mouseX;
        dy = MOUSEFACTORY * ((HEIGHTOFFSET - (double)Frame.mouseY) / HEIGHTOFFSET) - mouseY;
        moveX = dx * SCROLLFACTOR;
        moveY = dy * SCROLLFACTOR;
        
        // slightly shift scroll in direction of the mouse
        if(dx < 0) mouseX += Math.max(dx, moveX);
        else mouseX += Math.min(dx, moveX);       
        if(dy < 0) mouseY += Math.max(dy, moveY);
        else mouseY += Math.min(dy, moveY);
    }
    
    public static AffineTransform getTranslation(AffineTransform base) {
        AffineTransform ret = (AffineTransform) base.clone();
        ret.translate(getTotalX(), getTotalY());
        return ret;
    }
    
    public static AffineTransform getRotated(AffineTransform base, double xPos, double yPos, double ang) {
        AffineTransform ret = (AffineTransform) base.clone();
        ret.rotate(ang, xPos, yPos);
        return ret;
    }

    public static double getTotalX() {
        return scrollX + WIDTHOFFSET + mouseX;
    }
    
    public static double getTotalY() {
        return scrollY + HEIGHTOFFSET + mouseY;
    }
    
    public static double getActualX(Entity e) {
        return getTotalX() + e.getXPos();
    }
    
    public static double getActualY(Entity e) {
        return getTotalY() + e.getYPos();
    }
}
