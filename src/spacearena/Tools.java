package spacearena;

// class with static methods that are useful throughout the program
public class Tools {
        
    // random double
    public static double drand(double min, double max) {
        return (Math.random() * (max-min)) + min;
    }
    
    // random float
    public static float frand(float min, float max) {
        return ((float)Math.random() * (max-min)) + min;
    }
    
    // random int
    public static int irand(int min, int max) {
        return (int)(Math.random() * (max-min+1)) + min;
    }
    
    // finds a new point that goes a distance of d out from point (x, y) at angle ang
    // returns x coordinate of this point
    public static double rotXPos(double x, double ang, double d) {
        return x - Math.sin(ang) * d;
    }
    
    // finds a new point that goes a distance of d out from point (x, y) at angle ang
    // returns y coordinate of this point
    public static double rotYPos(double y, double ang, double d) {
        return y + Math.cos(ang) * d;
    }
    
    // returns angle you should face if you are at (cx, cy) looking at (tx, ty)
    public static double pointTowards(double cx, double cy, double tx, double ty) {
        return Math.toDegrees(Math.atan2(tx-cx, -ty+cy));
    }
    
    // returns squared distance from (cx, cy) to (tx, ty)
    public static double sqDist(double cx, double cy, double tx, double ty) {
        return (cx-tx)*(cx-tx)+(cy-ty)*(cy-ty);
    }
    
}
