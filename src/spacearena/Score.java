package spacearena;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

// class that updates and draws the timer and best time
public class Score {
    
    private static final int FONTSIZE = 32;
    private static DecimalFormat format;

    public static double bestScore;
    public static double currentScore;
    
    
    // read the best score from file (highscore.txt)
    public static void init() {
        try {
            FileReader fr = new FileReader("highscore.txt");
            Scanner s = new Scanner(fr);
            bestScore = s.nextDouble();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("highscore: " + bestScore);
        // two decimal places
        currentScore = 0;
        format = new DecimalFormat("0.00");
    }
    
    public static void update() {
        // add 16 ms (length of timer)
        currentScore += 0.016;
        // update best time
        bestScore = Math.max(bestScore, currentScore);
    }
    
    // draws the current time and best time
    public static void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Sans", Font.BOLD, FONTSIZE));
        // round to 2 decimal places
        g.drawString(format.format(currentScore), 10, 42);
        g.setColor(Color.YELLOW);
        // round to 2 decimal places
        g.drawString(format.format(bestScore), 1116-Math.max(0,((int)Math.log10(bestScore))*22), 42);
    }
    
    // writes the best score to a file (highscore.txt)
    public static void saveScore() {
        try {
            FileWriter fw = new FileWriter("highscore.txt");
            PrintWriter pw = new PrintWriter(fw); 
            pw.println(bestScore);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
