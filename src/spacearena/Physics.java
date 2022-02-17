package spacearena;

import java.util.ArrayList;

public class Physics {

    private static final double EPS = 1e-7;
    
    // idea from:
    // https://stackoverflow.com/questions/753140/how-do-i-determine-if-two-convex-polygons-intersect
    public static boolean isColliding(Entity ea, Entity eb) {
        // loop through all line segments of first polygon
        // segment formed from aPts[i-1] -> aPts[i]
        ArrayList<Point> aPts = ea.getUpdatedPoly();
        ArrayList<Point> bPts = eb.getUpdatedPoly();
        
        // find bounding box of a's pts
        double aMin, aMax, bMin, bMax;
        aMin = 1e9; aMax = -1e9;
        bMin = 1e9; bMax = -1e9;   
        for(int i = 0; i < aPts.size(); i++) {
            aMin = Math.min(aMin, aPts.get(i).x);
            aMax = Math.max(aMax, aPts.get(i).x);
            bMin = Math.min(bMin, aPts.get(i).y);
            bMax = Math.max(bMax, aPts.get(i).y);
        }
        
        boolean possible = false;
        for(int i = 0; i < bPts.size(); i++) {
            // check if point is in bounding box
            if(bPts.get(i).x >= aMin && bPts.get(i).x <= aMax && bPts.get(i).y >= bMin && bPts.get(i).y <= bMax) {
                possible = true;
                break;
            }
        }
        
        if(!possible) return false;     
        for(int i = 1; i < aPts.size(); i++) {
            // find slope of line segment
            double dy = aPts.get(i).y - aPts.get(i-1).y;
            double dx = aPts.get(i).x - aPts.get(i-1).x;
                 
            aMin = 1e9; aMax = -1e9;
            bMin = 1e9; bMax = -1e9;    
            //System.out.println("a pts " + aPts.get(i).x + " " + aPts.get(i).y + " second: " + aPts.get(i-1).x + " " + aPts.get(i-1).y + " dx " + dx);
            //System.out.println("A: " + aMin + " " + aMax + " B: " + bMin + " " + bMax);

            // special case vertical line
            if(Math.abs(dx) < EPS) {
                // find x-intercepts:
                for(int j = 0; j < aPts.size(); j++) {
                    double b = aPts.get(j).x;
                    aMin = Math.min(aMin, b);
                    aMax = Math.max(aMax, b);
                    //System.out.println("A: " + aMin + " " + aMax + " B: " + bMin + " " + bMax + " " + aPts.get(j).x);
                }
                
                for(int j = 0; j < bPts.size(); j++) {
                    double b = bPts.get(j).x;
                    bMin = Math.min(bMin, b);
                    bMax = Math.max(bMax, b);
                   // System.out.println("A: " + aMin + " " + aMax + " B: " + bMin + " " + bMax + " " + bPts.get(j).x);
                }
            } else {
                double slope = dy / dx;
                // System.out.println("Slope: " + slope);
                // find y-intercepts:        b = y - mx
                for(int j = 0; j < aPts.size(); j++) {
                    double b = aPts.get(j).y - slope * aPts.get(j).x;
                    aMin = Math.min(aMin, b);
                    aMax = Math.max(aMax, b);
                }
                
                for(int j = 0; j < bPts.size(); j++) {
                    double b = bPts.get(j).y - slope * bPts.get(j).x;
                    bMin = Math.min(bMin, b);
                    bMax = Math.max(bMax, b);
                }
            }
            
            // find if intervals are separate
            // System.out.println("Line Segment " + i + " A: " + aMin + " " + aMax + " B: " + bMin + " " + bMax);
            if(aMax < bMin || bMax < aMin) {
                return false;
            }
        }
        
        return true;
    }
    
    // frotate a point around another point, idea from:
    // https://stackoverflow.com/questions/2259476/rotating-a-point-about-another-point-2d
    public static Point rotateAround(double centerX, double centerY, double pointX, double pointY, double ang) {
        double px = pointX - centerX;
        double py = pointY - centerY;
        double x = px * Math.cos(ang) - py * Math.sin(ang);
        double y = px * Math.sin(ang) + py * Math.cos(ang);
        return new Point(x + centerX, y + centerY);
    }
}
