package gpainter;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

/**
 * A Population is a set of Individuals representing a generation.  A
 * Population can be evolved to create a new generation based on its
 * fittest Individuals.
 *
 * @author Kyle DeFrancia
 */
public class Population {

    // number of individuals in a Population
    public static final int POP_SIZE = 100;
    // percent of Population to retain between generations
    public static final double RETAIN = 0.2;
    // maximum amount to mutate by (between 10 and 100 would be good)
    public static final int MUTATE_AMOUNT = 10;
    // how often to mutate genes
    public static final double MUTATION_RATE = 0.3;

    public Individual[] generation;
    public Random rand;

    /**
     * Initialize a Population with an array of Individuals.
     */ 
    public Population() {
        generation = new Individual[POP_SIZE];
        rand = new Random(System.currentTimeMillis());

        for (int i = 0; i < POP_SIZE; i++) {
            generation[i] = new Individual(rand);
        }
    }

    /**
     * Helper function to make sure a given set of coordinates is within
     * image bounds.
     *
     * @param x the x coordinate of the point to check
     * @param y the y coordinate of the point to check
     *
     * @return true if the coordinates are inside the image
     */
    private boolean checkBounds(int x, int y) {
        return (x < ImagePanel.WIDTH) && (x > 0) &&
               (y < ImagePanel.HEIGHT) && (y > 0);
    }

    /**
     * Mate two Individuals to produce two new Individuals.  The children
     * have genes from both parents, and there is a chance to introduce a
     * random mutation during the mating process.
     *
     * @param mom an Individual to mate
     * @param dad an Individual to mate
     *
     * @return an Array of Individuals 
     */
    private Individual[] mate(Individual mom, Individual dad) {
        Individual[] kids = new Individual[] {new Individual(rand), new Individual(rand)};

        for (Individual kid : kids) {
            // uniform crossover
            for (int i = 0; i < Individual.GENE_LENGTH; i++) {
                if (rand.nextDouble() < 0.5) {
                    kid.genes[i] = mom.genes[i];
                } else {
                    kid.genes[i] = dad.genes[i];
                }
            }

            // randomly mutate
            if (rand.nextDouble() < MUTATION_RATE) {
                int randomGene = rand.nextInt(Individual.GENE_LENGTH);
                // randomly choose either size, color, or position to change
                switch (rand.nextInt(3)) {
                    case 0: int delta = rand.nextInt(MUTATE_AMOUNT);
                            if (rand.nextDouble() < 0.5) delta *= -1;
                            int newSize = kid.genes[randomGene].size + delta; 

                            // prevent Circles that are too big or too small
                            if (newSize > 30) {
                                newSize -= 20;
                            } else if (newSize <= 0) {
                                newSize = 1;
                            }
                            
                            kid.genes[randomGene].size = newSize;
                            break;

                    case 1: int amnt = rand.nextInt(MUTATE_AMOUNT);
                            if (rand.nextDouble() < 0.5) amnt *= -1;

                            Color orig = kid.genes[randomGene].color;
                            int r = orig.getRed();
                            int g = orig.getGreen();
                            int b = orig.getBlue();

                            // randomly choose one color component to change
                            Color c;
                            int t;
                            switch (rand.nextInt(3)) {
                                case 0:  t = r + amnt;
                                         if (t < 0 || t > 255) t = r - amnt;
                                         c = new Color(t, g, b);
                                         break;
                                case 1:  t = g + amnt;
                                         if (t < 0 || t > 255) t = g - amnt;
                                         c = new Color(r, t, b);
                                         break;
                                case 2:  t = b + amnt;
                                         if (t < 0 || t > 255) t = b - amnt;
                                         c = new Color(r, g, t);
                                         break;
                                default: c = new Color(r, g, b);
                                         break;
                            }
                            kid.genes[randomGene].color = c;
                            break;

                    case 2: int x;
                            int y;
                            int tries = 0;
                            do {
                                int deltaX = rand.nextInt(MUTATE_AMOUNT);
                                int deltaY = rand.nextInt(MUTATE_AMOUNT);
                                // randomly negate 0, 1, or 2 values
                                switch (rand.nextInt(4)) {
                                    case 0: deltaY *= -1;
                                    case 1: deltaX *= -1;
                                            break;
                                    case 2: deltaY *= -1;
                                            break;
                                    case 3:
                                    default: break;
                                }
                                
                                x = kid.genes[randomGene].x + deltaX;
                                y = kid.genes[randomGene].y + deltaY;
                                tries++;
                            } while(!checkBounds(x, y) && (tries < 5));

                            if (tries >= 5) {
                                x = rand.nextInt(ImagePanel.WIDTH);
                                y = rand.nextInt(ImagePanel.HEIGHT);
                            }
                        
                            kid.genes[randomGene].x = x;
                            kid.genes[randomGene].y = y;
                    default: break;
                }
            }
            // apply our changes
            kid.updateImage();
        }

        return kids;
    }

    /**
     * Evolve this Population into the next generation using the fittest
     * Individuals.
     */
    public void evolve() {
        // select the fittest individuals as the parents
        Arrays.sort(generation);
        int keep = (int)(POP_SIZE * RETAIN);
        
        // fill in the rest of the Population
        for (int i = keep; i < (POP_SIZE - 1); i+=2) {
            // mate two random parents and add children
            int mom = rand.nextInt(keep);
            int dad;
            do {
                dad = rand.nextInt(keep);
            } while (dad == mom);

            Individual[] children = mate(generation[mom], generation[dad]);
            generation[i] = children[0];
            generation[i+1] = children[1];
        }
    }

}
