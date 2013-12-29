package gpainter;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

/**
 * A circle has a size, a color, and a position.
 *
 * @author Kyle DeFrancia
 */
public class Circle {

    public int size;
    public Color color;
    public int x;
    public int y;

    /**
     * Create a new Circle.
     */
    public Circle(int size, Color color, int x, int y) {
        this.size = size;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new Circle randomly.
     */
    public Circle(Random rand) {
        
        size = rand.nextInt(20);       //100 is an arbitrary size limit
        x = rand.nextInt(ImagePanel.WIDTH);
        y = rand.nextInt(ImagePanel.HEIGHT);

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        color = new Color(r, g, b);
    }

    /**
     * Paints this Circle.
     *
     * @param g     the Graphics object to paint with
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

}
