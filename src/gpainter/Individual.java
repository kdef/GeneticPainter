package gpainter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * An Individual is an array of circles that represent a single painted image.
 * Each Individual has a fitness rating, where a higher rating means it more
 * accuratley depicts the target image.
 *
 * @author Kyle DeFrancia
 */
public class Individual implements Comparable<Individual> {

    public static final int GENE_LENGTH = 150;

    public BufferedImage img;
    public Circle[] genes;
    public int fitness;

    public Individual(Random rand) {
        fitness = 0;

        // initialize genes as random circles 
        genes = new Circle[GENE_LENGTH];
        for(int i = 0; i < GENE_LENGTH; i++) {
            genes[i] = new Circle(rand);
        }

        // draw the circles onto the image
        img = new BufferedImage(ImagePanel.WIDTH,
                                ImagePanel.HEIGHT,
                                BufferedImage.TYPE_INT_RGB);
        updateImage();
    }

    /**
     * Calculate the fitness rating of this Individual by comparing it to
     * to the target Image.
     * 
     * This number is calculated by comparing the color of each pixel in the
     * painted Image to the corresponding pixel in the target Image.
     *
     * @return an int representing the fitness of this Individual
     */
    public int judgeFitness(BufferedImage target) {
        int fit = 0;

        // compare images pixel by pixel
        for (int i = 0; i < ImagePanel.WIDTH; i++) {
            for (int j = 0; j < ImagePanel.HEIGHT; j++) {
                Color indv = new Color(img.getRGB(i, j));
                Color targ = new Color(target.getRGB(i, j));

                int deltaR = targ.getRed() - indv.getRed();
                int deltaG = targ.getGreen() - indv.getGreen();
                int deltaB = targ.getBlue() - indv.getBlue();

                int pixelFitness = (int)Math.sqrt(deltaR * deltaR +
                                                  deltaG * deltaG +
                                                  deltaB * deltaB);
                // lower fitness is better
                fit += pixelFitness;
            }
        }

        fitness = fit;
        return fit;
    }

    /**
     * Paint the genes onto this Individual's image.
     */
    public void updateImage() {
        Graphics2D g = img.createGraphics();
        for (Circle gene : genes) {
            gene.paint(g);
        }
        g.dispose();
    }

    @Override
    public int compareTo(Individual other) {
        return this.fitness - other.fitness;
    }
}
