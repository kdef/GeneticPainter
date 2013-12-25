package gpainter;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A class to work with both painted images and images from file. 
 *
 * @author Kyle DeFrancia
 */
public class ImagePanel extends JPanel {

    public static final int WIDTH = 400;;
    public static final int HEIGHT = 400;

    public BufferedImage img;

    /**
      * Create a new JPanel with a BufferedImage to draw on.
      */
    public ImagePanel() {
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    /**
      * Create a new JPanel with a BufferedImage loaaded from a file.
      */
    public ImagePanel(String path) {
        BufferedImage in = null;
        try {
            in = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.exit(1);
        }

        // scale the image to our default size
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(in, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

}
