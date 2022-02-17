package spacearena;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

// class that draws main menu text and players health / charge bars
public class HUD {
    
    private static final int FONTSIZE = 32;
   
    public static void draw(Graphics2D g) {
        // check if in-game or not
        if(Main.menu) {
            // draw text saying "click to start"
            g.setColor(Color.WHITE);
            g.setFont(new Font("Sans", Font.BOLD, FONTSIZE));
            g.drawString("Click To Start", 480, 600);
            
            g.setFont(new Font("Sans", Font.BOLD, FONTSIZE * 2));
            g.drawString("Space Arena", 400, 200);
        } else {
            // draw the charge meter
            int right = Frame.WIDTH - 20;
            int top = Frame.HEIGHT - 60;
            for(int i = 0; i < Player.MAXCHARGE / Player.CHARGECOST; i++) {
                if(Main.player.getCharge() <= i*Player.CHARGECOST) break;
                int width = Math.min(Player.CHARGECOST, Main.player.getCharge()-i*Player.CHARGECOST);
                if(width == Player.CHARGECOST) g.setColor(Color.YELLOW);
                else g.setColor(Color.YELLOW.darker());
                g.fillRect(right-i*Player.CHARGECOST-width+2, top+2, width-4, 16);
            }
            
            // draw the health meter
            int left = 20;
            g.setColor(Color.RED);
            for(int i = 0; i < Main.player.getHealth(); i++) {
                g.fillRect(left+2+i*48, top, 44, 16);
            }
            
            // draw filling health meter
            g.setColor(Color.RED.darker());
            g.fillRect(left+2+Main.player.getHealth()*48, top, Main.player.healthTimer, 16);
        }
    }
}
