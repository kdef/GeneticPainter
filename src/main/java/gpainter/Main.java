package gpainter;

import java.awt.Dimension;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Main entry point of program responsible for the GUI and controlling the
 * genetic algorithm.
 *
 * @author Kyle DeFrancia
 */
public class Main extends JFrame {

    private ImagePanel paintView;
    private ImagePanel bestView;
    private ImagePanel targetView;

    /**
      * Instantiate the frame to display the images and other information.
      */
    public Main() {
        // calculate sizes
        Dimension minSize = new Dimension(ImagePanel.WIDTH * 3, ImagePanel.HEIGHT);
        Dimension panelSize = new Dimension(ImagePanel.WIDTH, ImagePanel.HEIGHT);

        // setup images
        Container topPane = getContentPane();
        
        paintView = new ImagePanel();
        paintView.setMinimumSize(panelSize);
        paintView.setPreferredSize(panelSize);

        bestView = new ImagePanel();
        bestView.setMinimumSize(panelSize);
        bestView.setPreferredSize(panelSize);

        targetView = new ImagePanel("images/shapes.png");
        targetView.setMinimumSize(panelSize);
        targetView.setPreferredSize(panelSize);

        // display images side by side 
        Box imagePane = new Box(BoxLayout.LINE_AXIS);
        imagePane.add(paintView);
        imagePane.add(bestView);
        imagePane.add(targetView);

        topPane.add(imagePane);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Genetic Painter");
        setMinimumSize(minSize);
        pack();
        setVisible(true);
    }
    
    /**
     * Kickoff the Genetic Algorithm by setting up a Population and evolving
     * it until the desired fitness is reached.
     */
    private void initGPainter() {
        Population pop = new Population();

        Individual best;
        int gen = 0;
        do {
            best = pop.generation[0];

            // calculate the fitness of each Individual
            for (Individual candidate : pop.generation) {
                paintView.img = candidate.img;
                repaint();

                if (candidate.judgeFitness(targetView.img) < best.fitness) {
                    best = candidate;
                }
            }

            System.out.println("GEN " + gen +": " + best.fitness);

            // show the best from the generation
            bestView.img = best.img;
            repaint();

            // evolve!
            pop.evolve();
            gen++;
        } while (best.fitness > 1000);
    }
    
    public static void main(String[] args) {        
        Main window = new Main();
        window.initGPainter();        
    }
}
