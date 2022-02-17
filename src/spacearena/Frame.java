package spacearena;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame {
    
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;
    
    // frame components
    private static JFrame frame;
    private static JPanel panel;
    private static Timer timer;
    private static ActionListener tick;
    
    // key press booleans
    public static boolean upPressed;
    public static boolean firePressed;
    
    // mouse position
    public static int mouseX;
    public static int mouseY;
    
    public static void init() {
        frame = new JFrame("Space");
        
        // add to dimensions so inner pannel will have correct size
        // windows: WIDTH + 14, HEIGHT + 37
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        // stop the program when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Main.render((Graphics2D) g);
            }
        };
        
        // make panel read keyboard inputs
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {}
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W) upPressed = true;
                if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) Main.debug = true;
                if(ke.getKeyCode() == KeyEvent.VK_SPACE) firePressed = true;

            }
            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W) upPressed = false;
                if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) Main.debug = false;
                if(ke.getKeyCode() == KeyEvent.VK_SPACE) firePressed = false;
            }  
        });
        panel.setFocusable(true);
        panel.grabFocus();
        
        // make panel read mouse inputs
        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent me) {
                mouseX = me.getX();
                mouseY = me.getY();
            }
            @Override
            public void mouseMoved(MouseEvent me) {
                mouseX = me.getX();
                mouseY = me.getY();
            }
        });
        
        // listen for mouse click
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                firePressed = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                firePressed = false;
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panel.setDoubleBuffered(true);
        tick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // updates the game (player, enemies, etc...)
                Main.update();
                // redraws the game
                panel.repaint();
            }
        };
        
        timer = new Timer(16, tick);
        timer.start();
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
