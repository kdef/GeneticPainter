package gpainter;

import java.awt.Graphics;
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

    public static final int GENE_LENGTH = 100;
    public Circle[] genes;
    public int fitness;

    public Individual(Random rand) {
        fitness = 0;
        genes = new Circle[GENE_LENGTH];

        // initialize genes as random circles 
        for(int i = 0; i < GENE_LENGTH; i++) {
            genes[i] = new Circle(rand);
        }

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
    public int judgeFitness() {
        return 0;
    }

    @Override
    public int compareTo(Individual other) {
        return this.fitness - other.fitness;
    }

    /**
     * Paints the Image this Individual represents.
     *
     * @param g     the Graphics object to paint with
     */
    public void paint(Graphics g) {
        System.out.println("Size: " + genes.length);
        for(Circle gene : genes) {
            System.out.println("Drawing: " + gene.size + " at (" + gene.x + "," + gene.y + ")");
            gene.paint(g);
        }
    }

}
