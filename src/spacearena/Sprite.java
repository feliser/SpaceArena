package spacearena;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
    
    private static final String imgPrefix = "/img/";
    
    private final BufferedImage image;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    // constructor for image's center as offset
    public Sprite(String fileName, int width, int height) throws IOException {
        image = ImageIO.read(Main.class.getResource(imgPrefix + fileName));
        this.width = width;
        this.height = height;
        this.offsetX = width / 2;
        this.offsetY = height / 2;
    }
    
    // constructor for custom offset
    public Sprite(String fileName, int width, int height, int offsetX, int offsetY) throws IOException {
        image = ImageIO.read(Main.class.getResource(imgPrefix + fileName));
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public BufferedImage getImage() {
        return image;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
