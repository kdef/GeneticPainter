package gpainter;

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
    public static final double RETAIN = 0.2;

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
        double chanceToMutate = 0.1;
        if (chanceToMutate > rand.nextDouble()) {
            System.out.println("Mutation: ");
            // randomly choose either size, color, or position to change
            switch (rand.nextInt(3)) {
                case 0: System.out.println("    size changed");
                     for (int i = 0; i < kids.length; i++) {
                            int randomGene = rand.nextInt(Individual.GENE_LENGTH);
                            int randomSize = rand.nextInt(100);
                            kids[i].genes[randomGene].size = randomSize; 
                            System.out.println("    " + i + ": " + kids[i].genes[randomGene].size + " = " + randomSize);
                        }
                        break;
                case 1:
                case 2:
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
        }
    }

}
