package gpainter;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

/**
 * A Population is a set of Individuals representing a generation.  A
 * Population can be evolved to create a new generation based on its
 * fittest Individuals.
 *
 * Note: This class assumes that Individual.GENE_LENGTH is an even number 
 *
 * @author Kyle DeFrancia
 */
public class Population {

    public static final int POP_SIZE = 100;
    // percent of Population to retain between generations
    public static final double RETAIN = 0.20;
    // maximum amount to mutate by (between 10 and 100 would be good)
    public static final int MUTATE_AMOUNT = 10;
    // how often to mutate genes
    public static final double MUTATION_RATE = 0.1;

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

    private Float clamp(Float val) {
        if (val < 0) {
            return new Float(0.00001);
        } 
        else if (val > 1) {
            return new Float(0.99999);
        }
        return val;
    }

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

        // single point crossover
        //int splitPoint = Individual.GENE_LENGTH / 2;

        //System.arraycopy(mom.genes, 0, kids[0].genes, 0, splitPoint);
        //System.arraycopy(dad.genes, splitPoint, kids[0].genes, splitPoint, splitPoint);

        //System.arraycopy(dad.genes, 0, kids[1].genes, 0, splitPoint);
        //System.arraycopy(mom.genes, splitPoint, kids[1].genes, splitPoint, splitPoint);

        // uniform crossover
        for (int i = 0; i < Individual.GENE_LENGTH; i++) { 
            if (0.5 > rand.nextDouble()) {
                kids[0].genes[i] = mom.genes[i];
                kids[1].genes[i] = dad.genes[i];
            } else {
                kids[0].genes[i] = dad.genes[i];
                kids[1].genes[i] = mom.genes[i];
            }
        }

        // randomly mutate some genes
        for (Individual childToMutate : kids) {
        //for (int i = 0; i < Individual.GENE_LENGTH; i++) { 
            if (MUTATION_RATE > rand.nextDouble()) {
                //System.out.println("Mutation: ");
                int randomGene = rand.nextInt(Individual.GENE_LENGTH);
                //int randomGene = i;
                // randomly choose either size, color, or position to change
                switch (rand.nextInt(3)) {
                    case 0: int randomAmnt = rand.nextInt(MUTATE_AMOUNT);
                            // sometimes decrease
                            if (rand.nextDouble() < 0.5) {
                                randomAmnt = -1 * randomAmnt;
                                int newSize = childToMutate.genes[randomGene].size + randomAmnt;
                                if (newSize <= 0) randomAmnt = 0;
                            }
                            childToMutate.genes[randomGene].size += randomAmnt; 

                            // keep size in check
                            if (childToMutate.genes[randomGene].size > 30) {
                                childToMutate.genes[randomGene].size -= 20;
                            }

                            //System.out.println("    size changed: " + childToMutate.genes[randomGene].size);
                            break;
                    case 1: int amnt = rand.nextInt(MUTATE_AMOUNT);
                            // sometimes decrease
                            if (rand.nextDouble() < 0.5) {
                                amnt = -1 * amnt;
                            }

                            Color orig = childToMutate.genes[randomGene].color;
                            int r = orig.getRed();
                            int g = orig.getGreen();
                            int b = orig.getBlue();

                            // randomly choose one color component to change
                            //r += amnt;
                            //g += amnt;
                            //b += amnt;
                            //if (r < 0 || r > 255) r -= amnt;
                            //if (g < 0 || g > 255) g -= amnt;
                            //if (b < 0 || b > 255) b -= amnt;
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
                            childToMutate.genes[randomGene].color = c;
                            //System.out.println("    color changed: " + r + " " + g + " " + b);
                            break;
                    case 2: int x;
                            int y;
                            int tries = 0;
                            do {
                                int randomX = rand.nextInt(MUTATE_AMOUNT);
                                int randomY = rand.nextInt(MUTATE_AMOUNT);
                                switch (rand.nextInt(4)) {
                                    case 0: randomY = -1 * randomY;
                                    case 1: randomX = -1 * randomX;
                                            break;
                                    case 2: randomY = -1 * randomY;
                                            break;
                                    case 3:
                                    default: break;
                                }
                                
                                x = childToMutate.genes[randomGene].x += randomX;
                                y = childToMutate.genes[randomGene].y += randomY;
                                tries++;
                            } while(!checkBounds(x, y) && (tries < 5));

                            if (tries >= 5) {
                                x = rand.nextInt(ImagePanel.WIDTH);
                                y = rand.nextInt(ImagePanel.HEIGHT);
                            }
                        
                            //if (0.5 < rand.nextDouble()) {
                                childToMutate.genes[randomGene].x = x;
                            //} else {
                                childToMutate.genes[randomGene].y = y;
                            //}
                            //System.out.println("    position changed: (" + x + ", " + y + ")");
                    default: break;
                }
            }
        //}
        }

        // apply our changes
        kids[0].updateImage();
        kids[1].updateImage();

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
        //keep /= 2;

        //for (int k =0; k < POP_SIZE; k++){
        //    System.out.println("fitness: " + generation[k].fitness);
        //    if (k == keep) System.out.println("^ parents | kids v");
        //}

        //generation[keep-1] = new Individual(rand);
        //generation[keep] = new Individual(rand); 

        // fill in the rest of the Population
        for (int i = keep; i < (POP_SIZE-1); i+=2) {
            // mate two random parents and add children
            int mom = rand.nextInt(keep);
            int dad;
            do {
                dad = rand.nextInt(keep);
            } while (dad == mom);

            Individual[] children = mate(generation[mom], generation[dad]);
            generation[i] = children[0];
            generation[i+1] = children[1];
            //System.out.println("making babies at indxes: " + i + " and " + (i+1));
        }
    }

}
