package gpainter;

import java.awt.Graphics2D;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Tester {

    private BufferedImage testImg;
    private BufferedImage targetImg;

    private Random rand;

    public Tester() {
        System.out.println("Tester intialized");
       
        testImg = new BufferedImage(ImagePanel.WIDTH, ImagePanel.HEIGHT,
                                     BufferedImage.TYPE_INT_RGB);
        rand = new Random(25);
    }

    public void testAll() {
        testIndividual();
        testPopulation();
    }

    ///////////////////////////////////////////////////////////////////////////
    // INDIVIDUAL TESTS
    ///////////////////////////////////////////////////////////////////////////

    public void testIndividual() {
        testFitness100();
    }

    public void testFitness100() {
        Individual tst = new Individual(rand);
        ImagePanel target = new ImagePanel("images/star.png");

        // copy target image to our test image
        Graphics2D g = testImg.createGraphics();
        g.drawImage(target.img, 0, 0, null);
        g.dispose();

        tst.img = testImg;
        int fitness = tst.judgeFitness(target.img);
        assert (fitness == 0) : "fitness is " + fitness + " but should be 0";

        // change one pixel to black so it isn't perfect
        testImg.setRGB(0, 0, 0);
        fitness = tst.judgeFitness(target.img);
        assert (fitness != 0) : "fitness is 0 when the images do not match";

        System.out.println("    testFitness100 success!");
    }

    ///////////////////////////////////////////////////////////////////////////
    // POPULATION TESTS
    ///////////////////////////////////////////////////////////////////////////

    public void testPopulation() {
        testPopulationInit();
        testPopulationMate();
        testPopulationEvolve();
    }

    public void testPopulationInit() {
        Population pop = new Population();

        assert (pop.generation.length == Population.POP_SIZE) : "Population wrong size";
        for (int i = 0; i < pop.generation.length; i++) {
            assert (pop.generation[i] != null) : "Population not full";
        }

        System.out.println("    testPopulationInit success!");
    }

    public void testPopulationMate() {
        Population pop = new Population();
        Individual mom = new Individual(rand);
    }

    public void testPopulationEvolve() {
    }


}
