package gpainter;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static org.junit.Assert.*;

public class Tester {

    private final BufferedImage testImg;
    private BufferedImage targetImg;

    private final Random rand;

    public Tester() {
        System.out.println("Tester intialized");
       
        testImg = new BufferedImage(ImagePanel.WIDTH, ImagePanel.HEIGHT,
                                     BufferedImage.TYPE_INT_RGB);
        rand = new Random(25);
    }

    ///////////////////////////////////////////////////////////////////////////
    // INDIVIDUAL TESTS
    ///////////////////////////////////////////////////////////////////////////

    @Test
    public void testFitness100() {
        Individual tst = new Individual(rand);
        ImagePanel target = new ImagePanel("images/star.png");

        // copy target image to our test image
        Graphics2D g = testImg.createGraphics();
        g.drawImage(target.img, 0, 0, null);
        g.dispose();

        tst.img = testImg;
        int fitness = tst.judgeFitness(target.img);
        assertEquals(0, fitness);

        // change one pixel to black so it isn't perfect
        testImg.setRGB(0, 0, 0);
        fitness = tst.judgeFitness(target.img);
        assertNotEquals("0 fitness when images do not match", 0, fitness);
    }

    ///////////////////////////////////////////////////////////////////////////
    // POPULATION TESTS
    ///////////////////////////////////////////////////////////////////////////

    @Test
    public void testPopulationInit() {
        Population pop = new Population();

        assertEquals("Population wrong size", Population.POP_SIZE, pop.generation.length);
        for (int i = 0; i < pop.generation.length; i++) {
            assertNotNull("Population not full", pop.generation[i]);
        }
    }

    public void testPopulationMate() {
        Population pop = new Population();
        Individual mom = new Individual(rand);
    }

    public void testPopulationEvolve() {
    }


}
