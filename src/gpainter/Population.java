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
    public static final double RETAIN = 0.1;

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

        // copy half the genes from mom and half from dad
        int splitPoint = Individual.GENE_LENGTH / 2;

        System.arraycopy(mom.genes, 0, kids[0].genes, 0, splitPoint);
        System.arraycopy(dad.genes, splitPoint, kids[0].genes, splitPoint, splitPoint);

        System.arraycopy(dad.genes, 0, kids[1].genes, 0, splitPoint);
        System.arraycopy(mom.genes, splitPoint, kids[1].genes, splitPoint, splitPoint);

        // randomly mutate some genes
        double chanceToMutate = 0.3;
        if (chanceToMutate > rand.nextDouble()) {
            //System.out.println("Mutation: ");
            Individual childToMutate = kids[rand.nextInt(kids.length)];
            int randomGene = rand.nextInt(Individual.GENE_LENGTH);
            // randomly choose either size, color, or position to change
            switch (rand.nextInt(3)) {
                case 0: int randomSize = rand.nextInt(30);
                        childToMutate.genes[randomGene].size = randomSize; 
                        //System.out.println("    size changed");
                        break;
                case 1: float r = rand.nextFloat();
                        float g = rand.nextFloat();
                        float b = rand.nextFloat();
                        Color c = new Color(r, g, b);
                        childToMutate.genes[randomGene].color = c;
                        //System.out.println("    color changed");
                        break;
                case 2: int randomX = rand.nextInt(ImagePanel.WIDTH);
                        int randomY = rand.nextInt(ImagePanel.HEIGHT);
                        childToMutate.genes[randomGene].x = randomX;
                        childToMutate.genes[randomGene].y = randomY;
                        //System.out.println("    position changed");
                default: break;
            }
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
        //keep /= 2;

        //for (int k =0; k < POP_SIZE; k++){
        //    System.out.println("fitness: " + generation[k].fitness);
        //    if (k == keep) System.out.println("^ parents | kids v");
        //}

        //generation[keep-1] = new Individual(rand);
        generation[keep] = new Individual(rand); 

        // fill in the rest of the Population
        for (int i = keep+1; i < (POP_SIZE-1); i+=2) {
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
